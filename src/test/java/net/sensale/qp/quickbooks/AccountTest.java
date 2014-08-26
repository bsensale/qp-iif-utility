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


public class AccountTest {

    @Test
    public void testDonations() {
        assertEquals("\"Donations\"", Account.DONATIONS.toString());
    }
    
    @Test
    public void testDoorSales() {
        assertEquals("\"Door Sales\"", Account.DOOR_SALES.toString());
    }
    
    @Test
    public void testOtherIncome() {
        assertEquals("\"Other Income\"", Account.OTHER_INCOME.toString());
    }
    
    @Test
    public void testPaypal() {
        Account a = Account.PAYPAL;
        assertEquals("\"Paypal\"", a.toString());
    }
    
    @Test
    public void testPaypalExpense() {
        assertEquals("\"Paypal Expense\"", Account.PAYPAL_EXPENSE.toString());
    }
    
    @Test
    public void testSeaSub() {
        assertEquals("\"Season Subscription\"", Account.SEA_SUB.toString());
    }
    
}
