package net.sensale.qp.quickbooks;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void testStripQuotes() {
        String input = "\"Foo\"";
        assertEquals("Foo", StringUtils.stripQuotes(input));
    }

    @Test
    public void testStripSpecialChars() {
        String[] chars = { "ï", "¿", "½" };
        for (String s : chars) {
            String input = "Hi" + s + " There";
            assertEquals("Hi There", StringUtils.stripSpecialChars(input));
        }
    }

    @Test
    public void testStripBoth() {
        String input = "This is\" a reïally long \"¿\"weird string with many weird items.\"";
        assertEquals(
                "This is a really long weird string with many weird items.",
                StringUtils.stripQuotes(input));
    }
}
