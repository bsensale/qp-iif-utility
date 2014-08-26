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

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Wraps the various header columns used by IIF files.
 *
 */
public class IIFHeaders {

	/**
	 * The first line in a typical IIF download from Paypal
	 */
    String[] TRANSACTION_HEADER_COLUMNS = { "!TRNS", "DATE", "ACCNT", "NAME", "CLASS",
            "AMOUNT", "MEMO" };

    /**
     * The second line in a typical IIF download from Paypal
     */
    String[] SPLIT_INPUT_HEADER_COLUMNS = { "!SPL", "DATE", "ACCNT", "NAME", "AMOUNT",
            "MEMO" };

    /**
     * Class is important to the split lines, so we inject it into the header.
     */
    String[] SPLIT_OUTPUT_HEADER_COLUMNS = { "!SPL", "DATE", "ACCNT", "NAME", "CLASS",
            "AMOUNT", "MEMO" };

    /**
     * The third line in a typical IIF download from Paypal
     */
    String END_TRNS = "!ENDTRNS";

    /**
     * Tests that a line is a transaction header
     * 
     * @param pLine A transaction header line from an IIF file.
     * @return true if the line matches the expected value for a transaction header
     */
    public boolean isTransactionHeader(String pLine) {
        return testLine(TRANSACTION_HEADER_COLUMNS, pLine);
    }

    /**
     * Tests that a line is a split header
     * 
     * @param pLine A split header line from an IIF file.
     * @return true if the line matches the expected value for a split header
     */
    public boolean isSplitInputHeader(String pLine) {
        return testLine(SPLIT_INPUT_HEADER_COLUMNS, pLine);
    }

    /**
     * Splits a single line by tabs, then tests that the array matches the expected values.
     * 
     * @param pExpected An array of strings that represent the headers
     * @param pLine A String, which can be split by tabs to get a list of headers
     * @return true if the line splits into the expected values.
     */
    private boolean testLine(String[] pExpected, String pLine) {
        if (pLine == null) {
            return false;
        }
        String[] columns = pLine.split("\t");
        return Arrays.equals(pExpected, columns);
    }

    /**
     * Tests that a line is the end transaction
     * 
     * @param pLine
     * @return true if pLine is the end transaction header
     */
    public boolean isEndTransactionHeader(String pLine) {
        return END_TRNS.equals(pLine);
    }

    /**
     * @return A String representing a transaction header
     */
    String getTransactionHeader() {
        return createHeaderLine(TRANSACTION_HEADER_COLUMNS);
    }

    /**
     * @return A String representing a split header as used by our Quickbooks
     */
    String getSplitHeader() {
        return createHeaderLine(SPLIT_OUTPUT_HEADER_COLUMNS);
    }

    /**
     * @param pColumns The columns to turn into a String.
     * @return A String that represents a header line in an IIF file. 
     */
    private String createHeaderLine(String[] pColumns) {
        StringBuilder sb = new StringBuilder();
        for (String col : pColumns) {
            sb.append(col);
            sb.append("\t");
        }
        return sb.toString();
    }

    /**
     * @return A String the represents the end transaction header line.
     */
    String getEndTransactionHeader() {
        return END_TRNS;
    }

	/**
	 * Writes the standard IIF headers to the given {@link BufferedWriter}
	 * 
	 * @param out A {@link BufferedWriter} to send the headers to.
	 * @throws IOException
	 */
	public void writeHeaders(BufferedWriter out) throws IOException {
		out.write(getTransactionHeader());
        out.newLine();
        out.write(getSplitHeader());
        out.newLine();
        out.write(getEndTransactionHeader());
        out.newLine();
	}

}
