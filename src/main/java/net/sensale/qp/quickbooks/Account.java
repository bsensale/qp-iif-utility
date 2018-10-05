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
 * Represents the accounts QP uses in Quicken
 */
public enum Account {
    PAYPAL("Paypal"),
    DOOR_SALES("Door Sales"),
    SEA_SUB("Season Subscription"),
    PAYPAL_EXPENSE("Paypal Expense"),
    DONATIONS("Donations"),
    OTHER_INCOME("Other Income"),
    CHECKING ("Reading Checking");
    
    String mDisplayName;
    Account(String pValue) {
        mDisplayName = pValue;
    }
    
    @Override
    public String toString() {
        return "\"" + mDisplayName + "\"";
    }
}
