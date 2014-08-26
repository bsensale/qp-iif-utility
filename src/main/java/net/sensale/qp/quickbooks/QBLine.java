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
 * Represents a single line in an IIF file, keeping track of each of the sections.
 *
 */
public abstract class QBLine {

    protected Date mDate;

    protected abstract String getLineType();

    protected Account mAccount;
    protected Name mName;
    protected QBClass mClass;
    protected Amount mAmount;
    protected Memo mMemo;

    public QBLine(Date pDate, Account pAccount, Name pName, QBClass pClass, Amount pAmount, Memo pMemo) {
        mDate = pDate;
        mAccount = pAccount;
        mName = pName;
        mClass = pClass;
        mAmount = pAmount;
        mMemo = pMemo;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(getLineType());
        result.append('\t');
        result.append(mDate.toString());
        result.append('\t');
        result.append(mAccount.toString());
        result.append('\t');
        result.append(mName.toString());
        result.append('\t');
        result.append(mClass.toString());
        result.append('\t');
        result.append(mAmount.toString());
        result.append('\t');
        result.append(mMemo.toString());
        return result.toString();
    }

}