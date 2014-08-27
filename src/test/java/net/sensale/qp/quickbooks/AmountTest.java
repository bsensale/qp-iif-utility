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



public class AmountTest {

    @Test
    public void testStringConstructor() {
        Amount a = new Amount("444");
        assertEquals(new Double(444.00), a.mValue);
    }
    
    @Test
    public void testDoubleConstructor() {
        Amount a = new Amount(3.54);
        assertEquals("3.54", a.toString());
    }
    
    @Test(expected=NumberFormatException.class)
    public void testUnparseableDouble() {
        new Amount("foo");
    }
    
    @Test
    public void testToStringPositiveValue() {
        Amount a = new Amount("101");
        assertEquals("101.00", a.toString());
    }
    
    @Test
    public void testToStringNegativeValue() {
        Amount a = new Amount("-344.54");
        assertEquals("-344.54", a.toString());
    }
    
}
