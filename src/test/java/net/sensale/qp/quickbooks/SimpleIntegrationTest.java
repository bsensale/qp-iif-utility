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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SimpleIntegrationTest {

    String tmpDir = System.getProperty("java.io.tmpdir");

    private String filePrefix;

    public SimpleIntegrationTest(String pFilePrefix) {
        super();
        filePrefix = pFilePrefix;
    }

    @Parameters
    public static List<String[]> files() {
        List<String[]> results = new ArrayList<String[]>();
        results.add(new String[] { "/show" });
        results.add(new String[] { "/fundraiser" });
        results.add(new String[] { "/donation" });
        results.add(new String[] { "/seasub" });
        results.add(new String[] { "/paymentComplete" });
        results.add(new String[] { "/mixed" });
        results.add(new String[] { "/newReport" });
        return results;
    }

    @Test
    public void testIntegration() throws IOException {
        String input = getFileNameForResource(filePrefix + ".iif");
        String output = prepOutputFile(filePrefix + "output.iif");
        String[] args = { input, output };
        ConversionUtil.main(args);
        File result = new File(output);
        assertTrue("File didn't get created", result.exists());
        File expected = new File(getFileNameForResource(filePrefix + "Expected.iif"));
        assertFilesMatch(expected, result);
    }

    private String getFileNameForResource(String pResource) throws UnsupportedEncodingException {
        String input = getClass().getResource(pResource).getFile();
        input = URLDecoder.decode(input, "UTF-8");
        return input;
    }

    private String prepOutputFile(String pFileName) {
        String output = tmpDir + File.separator + pFileName;
        File result = new File(output);
        result.deleteOnExit();
        return output;
    }

    void assertFilesMatch(File pExpected, File pActual) throws IOException {
        assertTrue("Expected file didn't exist", pExpected.exists());
        assertTrue("Actual file didn't exist", pActual.exists());
        BufferedReader expIn = null;
        BufferedReader actIn = null;
        try {
            expIn = new BufferedReader(new FileReader(pExpected));
            actIn = new BufferedReader(new FileReader(pActual));
            String line;
            while ((line = expIn.readLine()) != null) {
                String actualLine = actIn.readLine();
                assertEquals("Line didn't match: ", line, actualLine);
            }
            assertEquals("Generated file still had more content", null, actIn.readLine());
        } finally {
            try {
                if (expIn != null) {
                    expIn.close();
                }
            } finally {
                if (actIn != null) {
                    actIn.close();
                }
            }
        }
    }
}
