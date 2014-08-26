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

public class TransactionCreateSplitLineTest {

    Transaction t;
    private String mMemoValue = "This is an awesome memo";
    private String mShowName = "\"Awesome Show\"";

    @Before
    public void setUp() {
        t = new Transaction();
        t.mAccount = Account.DOOR_SALES;
        t.mClass = QBClass.SHOW;
        t.mName = mShowName;

        TransactionLine tl = new TransactionLine(
                new Date("1/1/2002"),
                Account.DOOR_SALES,
                new Name("name"),
                QBClass.SHOW,
                new Amount(233.00),
                new Memo(mMemoValue));
        t.mTransLine = tl;
    }
    
    String incomeLine = "SPL\t\"9/4/2009\"\t\"Other Income\" \t\"Person\"\t-20.00";
    @Test
    public void testShowIncome() {
        SplitLine line = t.createSplitLine(incomeLine);
        assertEquals("Wrong Date", "\"9/4/2009\"", line.mDate.toString());
        assertEquals("Wrong Account", Account.DOOR_SALES, line.mAccount);
        assertEquals("Wrong Name", mShowName, line.mName.toString());
        assertEquals("Wrong class", QBClass.SHOW, line.mClass);
        assertEquals("Wrong amount", "-20.00", line.mAmount.toString());
        assertEquals("Wrong memo", "\"" + mMemoValue + "\"", line.mMemo.toString());
    }
    
    @Test
    public void testSeasubIncome() {
        t.mAccount = Account.SEA_SUB;
        t.mClass = QBClass.HOUSE;
        t.mName = "Person";
        SplitLine line = t.createSplitLine(incomeLine);
        assertEquals("Wrong Date", "\"9/4/2009\"", line.mDate.toString());
        assertEquals("Wrong Account", Account.SEA_SUB, line.mAccount);
        assertEquals("Wrong Name", "\"Person\"", line.mName.toString());
        assertEquals("Wrong class", QBClass.HOUSE, line.mClass);
        assertEquals("Wrong amount", "-20.00", line.mAmount.toString());
        assertEquals("Wrong memo", "\"" + mMemoValue + "\"", line.mMemo.toString());
    }
    
    @Test
    public void testFundraiserIncome() {
        t.mAccount = Account.DOOR_SALES;
        t.mClass = QBClass.HOUSE;
        t.mName = "Fundraiser";
        SplitLine line = t.createSplitLine(incomeLine);
        assertEquals("Wrong Date", "\"9/4/2009\"", line.mDate.toString());
        assertEquals("Wrong Account", Account.DOOR_SALES, line.mAccount);
        assertEquals("Wrong Name", "\"Fundraiser\"", line.mName.toString());
        assertEquals("Wrong class", QBClass.HOUSE, line.mClass);
        assertEquals("Wrong amount", "-20.00", line.mAmount.toString());
        assertEquals("Wrong memo", "\"" + mMemoValue + "\"", line.mMemo.toString());
    }
    
    String expenseLine = "SPL\t\"9/4/2009\"\t\"Other Expenses\"\tFee\t2.04";
    @Test
    public void testShowExpense() {
        SplitLine line = t.createSplitLine(expenseLine);
        assertEquals("Wrong Date", "\"9/4/2009\"", line.mDate.toString());
        assertEquals("Wrong Account", Account.PAYPAL_EXPENSE, line.mAccount);
        assertEquals("Wrong Name", mShowName, line.mName.toString());
        assertEquals("Wrong class", QBClass.SHOW, line.mClass);
        assertEquals("Wrong amount", "2.04", line.mAmount.toString());
        assertEquals("Wrong memo", "\"" + mMemoValue + "\"", line.mMemo.toString());
    }
    
    @Test
    public void testSeasubExpense() {
        t.mAccount = Account.SEA_SUB;
        t.mClass = QBClass.HOUSE;
        t.mName = "Person";
        SplitLine line = t.createSplitLine(expenseLine);
        assertEquals("Wrong Date", "\"9/4/2009\"", line.mDate.toString());
        assertEquals("Wrong Account", Account.PAYPAL_EXPENSE, line.mAccount);
        assertEquals("Wrong Name", "\"Person\"", line.mName.toString());
        assertEquals("Wrong class", QBClass.HOUSE, line.mClass);
        assertEquals("Wrong amount", "2.04", line.mAmount.toString());
        assertEquals("Wrong memo", "\"" + mMemoValue + "\"", line.mMemo.toString());
    }
    
    @Test
    public void testFundraiserExpense() {
        t.mAccount = Account.DOOR_SALES;
        t.mClass = QBClass.HOUSE;
        t.mName = "Fundraiser";
        SplitLine line = t.createSplitLine(expenseLine);
        assertEquals("Wrong Date", "\"9/4/2009\"", line.mDate.toString());
        assertEquals("Wrong Account", Account.PAYPAL_EXPENSE, line.mAccount);
        assertEquals("Wrong Name", "\"Fundraiser\"", line.mName.toString());
        assertEquals("Wrong class", QBClass.HOUSE, line.mClass);
        assertEquals("Wrong amount", "2.04", line.mAmount.toString());
        assertEquals("Wrong memo", "\"" + mMemoValue + "\"", line.mMemo.toString());
    }

    @Test
    public void testBadNumberArgs() {
        String tooMany = "SPL\t1/1/2009\tOther Income\tFoobar\t2\tThis memosucks";
        SplitLine line = t.createSplitLine(tooMany);
        assertEquals("Wrong Date", "\"1/1/2009\"", line.mDate.toString());
        assertEquals("Wrong Account", Account.DOOR_SALES, line.mAccount);
        assertEquals("Wrong Name", mShowName, line.mName.toString());
        assertEquals("Wrong class", QBClass.SHOW, line.mClass);
        assertEquals("Wrong amount", "2.00", line.mAmount.toString());
        assertEquals("Wrong memo", "\"" + mMemoValue + "\"", line.mMemo.toString());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testTooMany() {
        t.createSplitLine("SPL\foo");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testBadStart() {
        t.createSplitLine("SP L\t1\t2\t3\t4\t5");
    }
    

}
