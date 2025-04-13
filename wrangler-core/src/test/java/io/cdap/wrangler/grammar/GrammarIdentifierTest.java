/*
 * Copyright 2025 Your Organization Name
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
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import io.cdap.wrangler.grammar.GrammarIdentifier;
 
 /**
  * Test class for GrammarIdentifier.
  */
public class GrammarIdentifierTest {
 
     /**
      * Test for parsing byte size strings.
      */
     @Test
     public void testParseByteSize() {
         // Test byte size parsing
         assertEquals(10240L, GrammarIdentifier.parseByteSize("10KB"));
         assertEquals(2147483648L, GrammarIdentifier.parseByteSize("2GB"));
         assertEquals(104857600L, GrammarIdentifier.parseByteSize("100MB"));
         assertEquals(1073741824L, GrammarIdentifier.parseByteSize("1GB"));
     }
 
     /**
      * Test for parsing time duration strings.
      */
     @Test
     public void testParseTimeDuration() {
         // Test time duration parsing
         assertEquals(10000L, GrammarIdentifier.parseTimeDuration("10s"));   // 10 seconds in milliseconds
         assertEquals(300000L, GrammarIdentifier.parseTimeDuration("5m"));    // 5 minutes in milliseconds
         assertEquals(7200000L, GrammarIdentifier.parseTimeDuration("2h"));   // 2 hours in milliseconds
         assertEquals(60000L, GrammarIdentifier.parseTimeDuration("1m"));     // 1 minute in milliseconds
    }
}

