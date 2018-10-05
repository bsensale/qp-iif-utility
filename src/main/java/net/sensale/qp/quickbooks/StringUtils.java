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
 * Sometime useful methods for dealing with Strings.
 *
 */
public class StringUtils {

    static String[] specialChars = {"\u00ef", "\u00bf", "\u00bd", "\u00d4", "\u00f8", "\u03a9", "\ufffd" };
    
    /**
     * Removes the Quotes from a string, also pruning special characters.
     * 
     * @param pValue
     * @return
     */
    static String stripQuotes(String pValue) {
        if(pValue == null) {
            return null;
        }
        String result = pValue;
        if(result.contains("\"")){
            result = result.replaceAll("\"", "");
        }
        return stripSpecialChars(result);
    }

    /**
     * Strips out any character that matches the internal list this class keeps.
     * 
     * @param pValue
     * @return
     */
    static String stripSpecialChars(String pValue) {
        String result = pValue;
        for (String c : specialChars) {
            if(result.contains(c)) {
                result = result.replaceAll(c, "");
            }
        }
        return result;
    }
}