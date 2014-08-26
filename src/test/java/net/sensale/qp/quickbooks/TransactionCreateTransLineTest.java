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

import org.junit.Before;
import org.junit.Test;

public class TransactionCreateTransLineTest {

    Transaction t;
    @Before
    public void setUp() {
        t = new Transaction();
    }

    @Test
    public void testSeaSubWithDonation() {
        String input = "TRNS\t\"9/25/2009\"\t\"Paypal\"\t\"Robert Man\"\t\"Web Accept Payment Received\"\t242.45\t\"2 Regular Season Subscription for 5 Shows @ $75.00 = $150.00 (+ $100.00 donation)\"";
        t.createTransLine(input);
        TransactionLine tl = t.mTransLine;
        assertEquals("Wrong date", "\"9/25/2009\"", tl.mDate.toString());
        assertEquals("Wrong account", Account.PAYPAL, tl.mAccount);
        assertEquals("Wrong name", "\"Robert Man\"", tl.mName.toString());
        assertEquals("Wrong class", QBClass.WEB_PAYMENT, tl.mClass);
        assertEquals("Wrong amount", "242.45", tl.mAmount.toString());
        assertEquals("Wrong Memo", "\"2 Regular Season Subscription for 5 Shows @ $75.00 = $150.00 (+ $100.00 donation)\"", tl.mMemo.toString());
        assertEquals("Transaction had wrong account", Account.SEA_SUB, t.mAccount);
        assertEquals("Transaction had wrong class", QBClass.HOUSE, t.mClass);
        assertEquals("Transaction had wrong name", "\"Robert Man\"", t.mName);
        assertNull("Split lines should be null", t.mSplitLines);
        assertEquals("Wrong donation amount", new Double(-100), tl.mMemo.getDonationValue());
    }
    
    @Test
    public void testSeaSubWithDifferentDonationFormat() {
    	String input = "TRNS\t\"8/10/2014\"\t\"Paypal\"\t\"Ed Person\"\t\"Express Checkout Payment Received\"\t164.98\t\"#5 - 2 Regular Season Subscription for 4 Shows with transaction fee @ $72.00 each = $144.00  + Donation $25.00\"";
    	t.createTransLine(input);
        TransactionLine tl = t.mTransLine;
        assertEquals("Wrong date", "\"8/10/2014\"", tl.mDate.toString());
        assertEquals("Wrong account", Account.PAYPAL, tl.mAccount);
        assertEquals("Wrong name", "\"Ed Person\"", tl.mName.toString());
        assertEquals("Wrong class", QBClass.WEB_PAYMENT, tl.mClass);
        assertEquals("Wrong amount", "164.98", tl.mAmount.toString());
        assertEquals("Wrong Memo", "\"#5 - 2 Regular Season Subscription for 4 Shows with transaction fee @ $72.00 each = $144.00  + Donation $25.00\"", tl.mMemo.toString());
        assertEquals("Transaction had wrong account", Account.SEA_SUB, t.mAccount);
        assertEquals("Transaction had wrong class", QBClass.HOUSE, t.mClass);
        assertEquals("Transaction had wrong name", "\"Ed Person\"", t.mName);
        assertNull("Split lines should be null", t.mSplitLines);
        assertEquals("Wrong donation amount", new Double(-25), tl.mMemo.getDonationValue());
    }
    
    @Test
    public void testFunRaiser() {
        String input = "TRNS\t\"9/28/2009\"\t\"Paypal\"\t\"someone\"\t\"Web Accept Payment Received\"\t57.96\t\"2 Improv Workshop and Performance for A NIGHT OF IMPROV - a Fundraiser on 10/10/2009 - Saturday, 8:00PM\"\t";
        t.createTransLine(input);
        TransactionLine tl = t.mTransLine;
        assertEquals("Wrong date", "\"9/28/2009\"", tl.mDate.toString());
        assertEquals("Wrong account", Account.PAYPAL, tl.mAccount);
        assertEquals("Wrong name", "\"someone\"", tl.mName.toString());
        assertEquals("Wrong class", QBClass.WEB_PAYMENT, tl.mClass);
        assertEquals("Wrong amount", "57.96", tl.mAmount.toString());
        assertEquals("Wrong Memo", "\"2 Improv Workshop and Performance for A NIGHT OF IMPROV - a Fundraiser on 10/10/2009 - Saturday, 8:00PM\"", tl.mMemo.toString());
        assertEquals("Transaction had wrong account", Account.DOOR_SALES, t.mAccount);
        assertEquals("Transaction had wrong class", QBClass.FUNDRAISER, t.mClass);
        assertEquals("Transaction had wrong name", "Fundraiser", t.mName);
        assertNull("Split lines should be null", t.mSplitLines);
    }
    
    @Test
    public void testEmbeddedQuotes() {
        String input = "TRNS\t\"9/23/2009\"\t\"Paypal\"\t\"A Person\"\t\"Web Accept Payment Received\"\t32.71\t\"2 Regular senior/student ticket for This \"is\" a show on 1/22/2010 - Friday, 8:00PM\"\t";
        t.createTransLine(input);
        TransactionLine tl = t.mTransLine;
        assertEquals("Wrong date", "\"9/23/2009\"", tl.mDate.toString());
        assertEquals("Wrong account", Account.PAYPAL, tl.mAccount);
        assertEquals("Wrong name", "\"A Person\"", tl.mName.toString());
        assertEquals("Wrong class", QBClass.WEB_PAYMENT, tl.mClass);
        assertEquals("Wrong amount", "32.71", tl.mAmount.toString());
        assertEquals("Wrong Memo", "\"2 Regular senior/student ticket for This is a show on 1/22/2010 - Friday, 8:00PM\"", tl.mMemo.toString());
        assertEquals("Transaction had wrong account", Account.DOOR_SALES, t.mAccount);
        assertEquals("Transaction had wrong class", QBClass.SHOW, t.mClass);
        assertEquals("Transaction had wrong name", "This is a show", t.mName);
        assertNull("Split lines should be null", t.mSplitLines);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNotEnoughColumns() {
        t.createTransLine("TRNS");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidTextAtStart() {
        t.createTransLine("foo\tbar\tfoo\tbar\tfoo\tbar\tfoo");
    }
}
