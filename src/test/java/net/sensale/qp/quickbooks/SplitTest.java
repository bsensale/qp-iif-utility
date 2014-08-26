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

import org.junit.Test;

public class SplitTest {

    @Test
    public void testFullConstructor() {
        Date date = new Date("1/1/01");
        Name name = new Name("name");
        Amount amount = new Amount("322");
        Memo memo = new Memo("Memo");
        SplitLine s = new SplitLine(date, Account.DOOR_SALES, name, QBClass.SHOW, amount, memo);
        assertEquals("Date", date, s.mDate);
        assertEquals("Account", Account.DOOR_SALES, s.mAccount);
        assertEquals("Name", name, s.mName);
        assertEquals("Class", QBClass.SHOW, s.mClass);
        assertEquals("Amount", amount, s.mAmount);
        assertEquals("Memo", memo, s.mMemo);
    }

    @Test
    public void testFullString() {
        String exp = "SPL\t\"9/2/2009\"\t\"Other Income\"\t\"Richard S Person\"\tShow\t-150.00\t\"memo\"";
        SplitLine s = new SplitLine(new Date("9/2/2009"), Account.OTHER_INCOME, new Name(
                "Richard S Person"), QBClass.SHOW, new Amount(-150D), new Memo("memo"));
        assertEquals(exp, s.toString());
    }
    
    @Test
    public void testNoMemo() {
        String exp = "SPL\t\"9/2/2009\"\t\"Other Income\"\t\"Richard S Person\"\tShow\t-150.00\t";
        SplitLine s = new SplitLine(new Date("9/2/2009"), Account.OTHER_INCOME, new Name(
                "Richard S Person"), QBClass.SHOW, new Amount(-150D), new Memo(null));
        assertEquals(exp, s.toString());
    }
}
