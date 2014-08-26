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

public class TransactionLineTest {

    @Test
    public void testFullLine() {
        String exp = "TRNS\t\"9/30/2009\"\t\"Paypal\"\t\"Cynthia Person\"\tHouse\t38.54\t\"2 Musical adult ticket for Some Random Show on 10/2/2009 - Friday, 8:00PM\"";
        TransactionLine t = new TransactionLine(
                new Date("9/30/2009"),
                Account.PAYPAL,
                new Name("Cynthia Person"),
                QBClass.HOUSE,
                new Amount(38.54),
                new Memo(
                        "\"2 Musical adult ticket for Some Random Show on 10/2/2009 - Friday, 8:00PM\""));
        assertEquals(exp, t.toString());
    }

    @Test
    public void testEmbeddedQuotesInMemo() {
        String exp = "TRNS\t\"9/23/2009\"\t\"Paypal\"\t\"David Person\"\tHouse\t32.71\t\"2 Regular senior/student ticket for Another Show on 1/22/2010 - Friday, 8:00PM\"";
        TransactionLine t = new TransactionLine(
                new Date("\"9/23/2009\""),
                Account.PAYPAL,
                new Name("\"David Person\""),
                QBClass.HOUSE,
                new Amount(32.71),
                new Memo(
                        "\"2 Regular senior/student ticket for Another \"Show\" on 1/22/2010 - Friday, 8:00PM\""));
        assertEquals(exp, t.toString());
    }
}
