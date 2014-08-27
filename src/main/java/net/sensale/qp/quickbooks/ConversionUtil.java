/**
 * Copyright 2014 Brian Sensale
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sensale.qp.quickbooks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConversionUtil {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            printUsage();
            System.exit(1);
        }
        List<Transaction> transactions = readInput(args[0]);
        writeOutput(args[1], transactions);
    }

    static void writeOutput(String pFileName, List<Transaction> transactions) throws IOException {
        File outputFile = new File(pFileName);
        if(outputFile.exists()) {
            throw new RuntimeException("Can't write to existing file: " + outputFile.getCanonicalPath());
        }
        outputFile.createNewFile();
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(outputFile));
            new IIFHeaders().writeHeaders(out);
            writeTransactions(out, transactions);
        }
        finally {
            if(out != null) {
                out.flush();
                out.close();
                out = null;
            }
        }
    }

    private static void writeTransactions(BufferedWriter pOut, List<Transaction> transactions) throws IOException {
        for(Transaction t : transactions) {
            pOut.write(t.toString());
            pOut.newLine();
        }
    }

    static List<Transaction> readInput(String pFileName) throws IOException {
        List<Transaction> results = new ArrayList<Transaction>();
        File inputFile = new File(pFileName);
        if (!inputFile.exists() || !inputFile.canRead()) {
            throw new RuntimeException("Can't find or read input file: " + pFileName);
        }
        BufferedReader in = null;
        try {
        	IIFHeaders headers = new IIFHeaders();
            in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
            // The first three lines MUST be headers.
            if (!headers.isTransactionHeader(in.readLine())) {
                throw new RuntimeException("File didn't have a Transaction header as first line.");
            }
            if (!headers.isSplitInputHeader(in.readLine())) {
                throw new RuntimeException("File didn't have a Split Header as the second line.");
            }
            if (!headers.isEndTransactionHeader(in.readLine())) {
                throw new RuntimeException("File didn't have a Transaction end header as third line.");
            }
            Transaction t;
            while ((t = getNextTransaction(in)) != null) {
                results.add(t);
            }
        }
        finally {
            if(in != null) {
                in.close();
                in = null;
            }
        }
        return results;
    }

    static Transaction getNextTransaction(BufferedReader pIn) throws IOException {
        String startLine = pIn.readLine();
        if(startLine == null) {
            return null;
        }
        if(!ConversionUtil.isTransactionStart(startLine)) {
            throw new RuntimeException("Next line was not a Transaction start: " + startLine);
        }
        String currLine;
        List<String> splitLines = new ArrayList<String>();
        while((currLine = pIn.readLine())!=null) {
            if(ConversionUtil.isTransactionEnd(currLine)) {
                break;
            }
            if(!ConversionUtil.isSplit(currLine)) {
                throw new RuntimeException("Line in a Transaction was not a split: " + currLine);
            }
            splitLines.add(currLine);
        }
        if(currLine == null) {
            throw new RuntimeException("Reached end of file without reaching end transaction");
        }
        try {
            return new Transaction(startLine, splitLines.toArray(new String[] {}));
        }
        catch(PaymentCompletedClassException e) {
            return getNextTransaction(pIn);
        }
        catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("IAE processing:" + startLine, e);
        }
    }

    static void printUsage() {
        System.out.println("Usage: ConversionUtil <inputFile> <outputFile>");
    }

	static boolean isTransactionStart(String pValue) {
	    return pValue.startsWith("TRNS");
	}

	static boolean isTransactionEnd(String pValue) {
	    return pValue.startsWith("ENDTRNS");
	}

	static boolean isSplit(String pValue) {
	    return pValue.startsWith("SPL");
	}

}
