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

import org.junit.Before;
import org.junit.Test;


public class TransactionParseClassandDetectDonationTest {

    Transaction t;
    @Before
    public void setUp() {
        t = new Transaction();
    }
    
    @Test
    public void testValidClass() {
        QBClass a = t.parseClassAndDetectDonation("Web Accept Payment Received");
        assertEquals(QBClass.WEB_PAYMENT, a);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testNull() {
    	t.parseClassAndDetectDonation(null);
    }
    
    @Test
    public void testDonation() {
        QBClass a = t.parseClassAndDetectDonation("Donation Received");
        assertEquals(QBClass.DONATION, a);
    }
    
    @Test(expected=PaymentCompletedClassException.class)
    public void testPaymentException() {
        t.parseClassAndDetectDonation("Payment Completed");
    }
    
    @Test(expected=UnsupportedClassException.class)
    public void testUnsupportedClassException() {
        t.parseClassAndDetectDonation("foo");
    }
}
