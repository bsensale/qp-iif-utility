package net.sensale.qp.quickbooks;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.Assertion;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;


public class ConversionUtilTest {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();
    
    @Test
    public void testNoArgs() throws IOException {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals("Did not print usage",
                        "Usage: ConversionUtil <inputFile> <outputFile>" + LINE_SEPARATOR,
                        log.getLog());
            }
        });
        ConversionUtil.main(new String[] {});
    }
    
    @Test(expected = RuntimeException.class)
    public void testWriteOutputFileExists() throws IOException {
        File file = File.createTempFile("foo", "txt");
        file.deleteOnExit();
        ConversionUtil.writeOutput(file.getAbsolutePath(), null);
    }
    
    @Test(expected = RuntimeException.class)
    public void testGetNextTransactionBadFirstLine() throws IOException {
        StringReader reader = new StringReader("Bad");
        ConversionUtil.getNextTransaction(new BufferedReader(reader));
    }
    
    @Test(expected = RuntimeException.class)
    public void testGetNextTransactionBadSecondLine() throws IOException {
        StringReader reader = new StringReader("TRNS" + LINE_SEPARATOR + "Bad");
        ConversionUtil.getNextTransaction(new BufferedReader(reader));
    }
    
    @Test(expected = RuntimeException.class)
    public void testGetNextTransactionNoEnd() throws IOException {
        StringReader reader = new StringReader("TRNS");
        ConversionUtil.getNextTransaction(new BufferedReader(reader));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testBadTransactionLinet() throws IOException {
        StringReader reader = new StringReader("TRNS" + LINE_SEPARATOR + "SPL" + LINE_SEPARATOR + "ENDTRNS");
        ConversionUtil.getNextTransaction(new BufferedReader(reader));
    }
    
}
