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


public class ClassTest {

    @Test
    public void testGetNameHouse() {
        QBClass a = QBClass.HOUSE;
        assertEquals("House", a.toString());
    }
    
    @Test
    public void testGetNameShow() {
        QBClass a = QBClass.SHOW;
        assertEquals("Show", a.toString());
    }
    
    @Test public void testGetNameFundraiser() {
        QBClass a = QBClass.FUNDRAISER;
        assertEquals("Fundraiser",  a.toString());
    }
    
    @Test public void testGetNameExpressCheckout() {
        QBClass a = QBClass.WEB_PAYMENT;
        assertEquals("\"Web Accept Checkout Payment\"",  a.toString());
    }
    
    @Test public void testGetNameWebPayment() {
        QBClass a = QBClass.WEB_PAYMENT;
        assertEquals("\"Web Accept Checkout Payment\"",  a.toString());
    }
    
    @Test public void testGetNameDonation() {
        QBClass a = QBClass.DONATION;
        assertEquals("\"Donation Payment\"",  a.toString());
    }
}
