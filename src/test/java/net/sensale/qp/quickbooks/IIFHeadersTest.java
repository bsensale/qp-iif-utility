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

import static org.junit.Assert.*;

import org.junit.Test;

public class IIFHeadersTest {
	
	IIFHeaders headers = new IIFHeaders();

    @Test
    public void testIsEndTransaction() {
        assertTrue(headers.isEndTransactionHeader("!ENDTRNS"));
        assertFalse(headers.isEndTransactionHeader("foo"));
        assertFalse(headers.isEndTransactionHeader(""));
        assertFalse(headers.isEndTransactionHeader(null));
    }

    @Test
    public void testIsStartTransaction() {
        assertTrue(headers.isTransactionHeader("!TRNS\tDATE\tACCNT\tNAME\tCLASS\tAMOUNT\tMEMO"));
        assertFalse(headers.isTransactionHeader("!TRNS\tACCNT\tDATE\tNAME\tCLASS\tAMOUNT\tMEMO"));
        assertFalse(headers.isTransactionHeader(null));
        assertFalse(headers.isTransactionHeader(""));
        assertFalse(headers.isTransactionHeader("foo"));
    }

    @Test
    public void testIsSplit() {
        assertTrue(headers.isSplitInputHeader("!SPL\tDATE\tACCNT\tNAME\tAMOUNT\tMEMO"));
        assertFalse(headers.isSplitInputHeader("!SPL\tACCNT\tDATE\tNAME\tAMOUNT\tMEMO"));
        assertFalse(headers.isSplitInputHeader(null));
        assertFalse(headers.isSplitInputHeader(""));
        assertFalse(headers.isSplitInputHeader("foo"));
    }

    @Test
    public void getTransactionHeader() {
        assertEquals(
                "!TRNS\tDATE\tACCNT\tNAME\tCLASS\tAMOUNT\tMEMO\t",
                headers.getTransactionHeader());
    }

    @Test
    public void getSplitHeader() {
        assertEquals(
                "!SPL\tDATE\tACCNT\tNAME\tCLASS\tAMOUNT\tMEMO\t",
                headers.getSplitHeader());
    }

    @Test
    public void getEndTransactionHeader() {
        assertEquals("!ENDTRNS", headers.getEndTransactionHeader());
    }
}
