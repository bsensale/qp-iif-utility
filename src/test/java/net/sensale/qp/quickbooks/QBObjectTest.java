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
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class QBObjectTest {

    QBObject<String> obj;
    
    @Before
    public void setUp() throws Exception {
        obj = new QBObject<String>();
    }

    @Test
    public void testStripQuotesNoQuotes() {
        String result = StringUtils.stripQuotes("abc");
        assertEquals("abc", result);
    }

    @Test
    public void testStripQuotesSingleQuote() {
        String result = StringUtils.stripQuotes("\"abc");
        assertEquals("abc", result);
    }
    
    @Test
    public void testStripQuotesMultipleQuotes() {
        String result = StringUtils.stripQuotes("\"\"abc");
        assertEquals("abc", result);
    }
    
    @Test
    public void testStripQuotesComplex() {
        String result = StringUtils.stripQuotes("\"This is a\" complex \"\"example\".");
        assertEquals("This is a complex example.", result);
    }
    
    @Test
    public void testStripQuotesNull() {
        assertNull(StringUtils.stripQuotes(null));
    }
    
    @Test
    public void testToString() {
        obj.mValue = "foo";
        assertEquals("\"foo\"", obj.toString());
    }
    
    @Test
    public void testToStringNull() {
        assertEquals("", obj.toString());
    }
    
    @Test
    public void testToStringEmpty() {
        obj.mValue = "";
        assertEquals("", obj.toString());
    }
}
