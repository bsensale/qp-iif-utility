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

import org.junit.Test;


public class MemoDonationValueTest {

    @Test
    public void testGetDonationValue() {
        String input = "2 Regular Season Subscription for 5 Shows @ $75.00 = $150.00 (+ $100.00 donation)";
        Memo memo = new Memo(input);
        assertEquals(new Double(-100.00), memo.getDonationValue());
    }
    
    @Test
    public void testGetNewDonationValueSeasub() {
        String input = "#5 - 2 Regular Season Subscription for 4 Shows with transaction fee @ $72.00 each = $144.00 + Donation $25.00";
        Memo memo = new Memo(input);
        assertEquals(new Double(-25.00), memo.getDonationValue());
    }
    
    @Test
    public void testGetNewDonationValueFundraiser() {
        String input = "#135 - Tickets for Suburban H... on 12/5/2014 for 2 Fundraiser+ $25";
        Memo memo = new Memo(input);
        assertEquals(new Double(-25.00), memo.getDonationValue());
    }
    
    @Test
    public void testGetNewDonationValueShow() {
        String input = "#120 - Tickets for November... on 10/24/2014 for 2 Adult+ $100";
        Memo memo = new Memo(input);
        assertEquals(new Double(-100.00), memo.getDonationValue());
    }
    
    @Test
    public void testNullString() {
        Memo memo = new Memo(null);
        assertNull(memo.getDonationValue());
    }
    
    @Test
    public void testEmptyString() {
        Memo memo = new Memo("");
        assertNull(memo.getDonationValue());
    }
    
    @Test
    public void testNoDonation() {
        String input = "2 Musical senior/student ticket for This is a show on 9/26/2009 - Saturday, 8:00PM";
        Memo memo = new Memo(input);
        assertNull(memo.getDonationValue());
    }
    
    @Test(expected=RuntimeException.class)
    public void testWrongDonation() {
        String input = "This has a donation.";
        Memo memo = new Memo(input);
        memo.getDonationValue();
    }
    
    @Test
    public void testOddDonation() {
        String input = "$10034.45 donation)";
        Memo memo = new Memo(input);
        assertEquals(new Double(-10034.45), memo.getDonationValue());
    }
    
    @Test
    public void test0Donation() {
    	String input = "Donation $0.00";
    	Memo memo = new Memo(input);
    	assertNull("$0 in donations should return null!", memo.getDonationValue());
    }
}
