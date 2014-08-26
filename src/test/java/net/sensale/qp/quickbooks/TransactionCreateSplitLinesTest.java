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
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TransactionCreateSplitLinesTest {

    Transaction t;
    private String mMemoValue = "This is an awesome memo";
    private String mShowName = "\"Awesome Show\"";

    String incomeLine1 = "SPL\t\"9/22/2009\"\t\"Other Income\"\t\"Person\"\t-80.00\r\n";
    String expenseLine1 = "SPL\t\"9/23/2009\"\t\"Other Expenses\"\tFee\t2.62";
    String incomeLine2 = "SPL\t\"9/21/2009\"\t\"Other Income\"\t\"Person\"\t-10.00";

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

    @Test
    public void testOne() {
        String[] input = { incomeLine1 };
        t.createSplitLines(input);
        List<SplitLine> splitLines = t.mSplitLines;
        assertNotNull("No split lines created", splitLines);
        assertEquals("Wrong size on split lines list", 1, splitLines.size());
        verifyIncomeLine1(splitLines.get(0));
    }
    
    @Test
    public void testMixed() {
        String[] input = { incomeLine1, expenseLine1 };
        t.createSplitLines(input);
        List<SplitLine> splitLines = t.mSplitLines;
        assertNotNull("No split lines created", splitLines);
        assertEquals("Wrong size on split lines list", 2, splitLines.size());
        verifyIncomeLine1(splitLines.get(0));
        verifyExpenseLine1(splitLines.get(1));
    }
    
    @Test
    public void testAll() {
        String[] input = { incomeLine2, expenseLine1, incomeLine1 };
        t.createSplitLines(input);
        List<SplitLine> splitLines = t.mSplitLines;
        assertNotNull("No split lines created", splitLines);
        assertEquals("Wrong size on split lines list", 3, splitLines.size());
        verifyIncomeLine2(splitLines.get(0));
        verifyExpenseLine1(splitLines.get(1));
        verifyIncomeLine1(splitLines.get(2));
    }

    private void verifyIncomeLine1(SplitLine line) {
        assertEquals("Wrong Date", "\"9/22/2009\"", line.mDate.toString());
        assertEquals("Wrong Account", Account.DOOR_SALES, line.mAccount);
        assertEquals("Wrong Name", mShowName, line.mName.toString());
        assertEquals("Wrong class", QBClass.SHOW, line.mClass);
        assertEquals("Wrong amount", "-80.00", line.mAmount.toString());
        assertEquals("Wrong memo", "\"" + mMemoValue + "\"", line.mMemo.toString());
    }
    
    private void verifyIncomeLine2(SplitLine line) {
        assertEquals("Wrong Date", "\"9/21/2009\"", line.mDate.toString());
        assertEquals("Wrong Account", Account.DOOR_SALES, line.mAccount);
        assertEquals("Wrong Name", mShowName, line.mName.toString());
        assertEquals("Wrong class", QBClass.SHOW, line.mClass);
        assertEquals("Wrong amount", "-10.00", line.mAmount.toString());
        assertEquals("Wrong memo", "\"" + mMemoValue + "\"", line.mMemo.toString());
    }
    
    private void verifyExpenseLine1(SplitLine line) {
        assertEquals("Wrong Date", "\"9/23/2009\"", line.mDate.toString());
        assertEquals("Wrong Account", Account.PAYPAL_EXPENSE, line.mAccount);
        assertEquals("Wrong Name", mShowName, line.mName.toString());
        assertEquals("Wrong class", QBClass.SHOW, line.mClass);
        assertEquals("Wrong amount", "2.62", line.mAmount.toString());
        assertEquals("Wrong memo", "\"" + mMemoValue + "\"", line.mMemo.toString());
    }
}
