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

import org.junit.Test;

public class DateTest {

    @Test
    public void testConstructor() {
        Date d = new Date("1/2/2009");
        assertEquals("\"1/2/2009\"", d.toString());
    }
    
    @Test
    public void testLateDates() {
        Date d = new Date("11/22/2011");
        assertEquals("\"11/22/2011\"", d.toString());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testNullArg() {
        new Date(null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testUnparseable() {
        new Date("foo");
    }
}
