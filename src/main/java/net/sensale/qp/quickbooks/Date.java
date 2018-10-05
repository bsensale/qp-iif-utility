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

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents the date of a transaction.
 *
 */
public class Date extends QBObject<java.util.Date> {

    SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
    
    public Date(String pDate) {
        if(pDate == null) {
            throw new IllegalArgumentException("Null value passed in for Date");
        }
        try {
            mValue = df.parse(StringUtils.stripQuotes(pDate));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Parse Exception for date", e);
        }
    }
    
    /**
     * @return The date object, formatted as M/d/yyyy
     */
    @Override
    public String toString() {
        return "\"" + df.format(mValue) + "\"";
    }
}
