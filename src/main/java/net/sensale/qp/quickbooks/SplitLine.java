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

/**
 * A single line representing a Split entry.
 *
 */
public class SplitLine extends QBLine {

    public SplitLine(
            Date pDate,
            Account pAccount,
            Name pName,
            QBClass pClass,
            Amount pAmount,
            Memo pMemo) {
        super(pDate, pAccount, pName, pClass, pAmount, pMemo);
    }

    @Override
    protected String getLineType() {
        return "SPL";
    }

}
