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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the Memo section for an IIF entry.
 *
 */
public class Memo extends QBObject<String> {
    
    public Memo(String pValue) {
        mValue = StringUtils.stripQuotes(pValue);
    }
    
    /**
     * @return The Donation amount for the entry if there is one in the Memo, otherwise null
     */
    public Double getDonationValue() {
        if(mValue == null) {
            return null;
        }
        if(mValue.contains("onation")) {
            return parseDonationValue();
        }
        else return null;
    }
    
    /**
     * Extracts the Donation amount from the String.  This works for the following two patterns:
     * 
     *   Foo bar $X.XX donation
     *   Foo bar Donation $X.XX
     *   
     * @return The extracted donation value, or null if the amount is $0.
     */
    Double parseDonationValue() {
    	Pattern p;
    	if(mValue.contains("donation"))
    			p = Pattern.compile(".*\\$(\\d{1,}?\\.\\d{2}) donation\\)");
    	else p = Pattern.compile(".*Donation \\$(\\d{1,}?\\.\\d{2})");
        Matcher m = p.matcher(mValue);
        if(!m.matches()) {
            throw new RuntimeException("Failed to match value to the pattern: " + mValue);
        }
        Double result = new Double(m.group(1))*-1;
        if(result == 0D) {
        	return null;
        }
        return result;
    }

}
