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


public class TransactionParseMemoTest {

    Transaction t;
    
    @Before
    public void setUp() {
        t = new Transaction();
    }
    
    @Test
    public void testSeasubMatch() {
        String memo = "2 Regular Season Subscription for 5 Shows @ $75.00 = $150.00 (+ $100.00 donation)";
        t.parseMemo(memo, "foo");
        assertEquals("Accounts didn't match", Account.SEA_SUB, t.mAccount);
        assertEquals("Names didn't match", "foo", t.mName);
        assertEquals("Wrong class", QBClass.HOUSE, t.mClass);
    }
    
    @Test
    public void testNoSeasubMatch() {
        String memo = "2 Musical adult ticket for This is a show on 10/3/2009 - Saturday, 8:00PM";
        t.parseMemo(memo, "foo");
        assertEquals("Accounts didn't match", Account.DOOR_SALES, t.mAccount);
        assertEquals("Names didn't match", "This is a show", t.mName);
        assertEquals("Wrong class", QBClass.SHOW, t.mClass);
    }
    
    @Test
    public void testEmbeddedQuotes() {
        String memo = "2 Regular senior/student ticket for This show \"has\" quotes on 1/22/2010 - Friday, 8:00PM";
        t.parseMemo(memo, "foo");
        assertEquals("Accounts didn't match", Account.DOOR_SALES, t.mAccount);
        assertEquals("Names didn't match", "This show has quotes", t.mName);
        assertEquals("Wrong class", QBClass.SHOW, t.mClass);
    }
    
    @Test
    public void testFundraiser() {
        String memo = "2 Fundraiser for A NIGHT OF IMPROV - a FUNraiser on 10/10/2009 - Saturday, 8:00PM";
        t.parseMemo(memo, "foo");
        assertEquals("Wrong account", Account.DOOR_SALES, t.mAccount);
        assertEquals("Wrong name", "Fundraiser", t.mName);
        assertEquals("Wrong class", QBClass.FUNDRAISER, t.mClass);
    }
    
    @Test
    public void testDonation() {
        String memo = "Quannapowitt Players";
        t.mClass = QBClass.DONATION;
        t.parseMemo(memo, "foo");
        assertEquals("Wrong account", Account.DONATIONS, t.mAccount);
        assertEquals("Wrong name", "Fundraiser", t.mName);
        assertEquals("Wrong class", QBClass.FUNDRAISER, t.mClass);
    }
    
    @Test
    public void testShortNameWithElipses() {
    	String memo = "#2 - Tickets for Short... on 10/19/2014 for 1 Senior/Student";
    	t.parseMemo(memo, "foo");
    	assertEquals("Wrong account", Account.DOOR_SALES, t.mAccount);
    	assertEquals("Wrong name", "Short", t.mName);
    	assertEquals("Wrong class", QBClass.SHOW, t.mClass);
    }
    
    @Test(expected = RuntimeException.class)
    public void testBadMemo() {
        String memo = "Bad";
        t.parseMemo(memo, "foo");
    }
}
