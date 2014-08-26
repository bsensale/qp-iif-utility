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
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class MemoConstructorTest {

    @Test
    public void testConstructor() {
        Memo memo = new Memo("bar");
        assertEquals("bar", memo.mValue);
    }

    @Test
    public void testNullArgument() {
        Memo memo = new Memo(null);
        assertNull(memo.mValue);
    }

    @Test
    public void testEmptyArgument() {
        Memo memo = new Memo("");
        assertEquals("", memo.mValue);
    }

}
