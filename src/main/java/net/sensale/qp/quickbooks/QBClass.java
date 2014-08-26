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
 * Represents the classes used in Quickbooks.
 *
 */
public enum QBClass {
    HOUSE, SHOW, WEB_PAYMENT, FUNDRAISER ;

    /**
     * @return A user friendly version of the Class.
     */
    @Override
    public String toString() {
        String result = name();
        if(result.equals(WEB_PAYMENT.name())) {
            return "\"Web Accept Payment Received\"";
        }
        return result.substring(0, 1) + result.substring(1, result.length()).toLowerCase();
    }
}
