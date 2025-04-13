/*
 *  Copyright © 2021 Cask Data, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

 package io.cdap.wrangler.api.parser;

 import java.util.HashMap;
 import java.util.Map;
 /**
  * TimeDuration class to handle the parsing and conversion of time duration units.
  */
 public class TimeDuration extends AbstractToken {
   private static final Map<String, Long> UNIT_MULTIPLIERS = new HashMap<>();
   
   static {
     // Define the conversion multipliers for various time units
     UNIT_MULTIPLIERS.put("NS", 1L);                      // Nanoseconds
     UNIT_MULTIPLIERS.put("MS", 1_000_000L);               // Milliseconds
     UNIT_MULTIPLIERS.put("S", 1_000_000_000L);            // Seconds
     UNIT_MULTIPLIERS.put("M", 60_000_000_000L);           // Minutes (60 seconds)
     UNIT_MULTIPLIERS.put("H", 3600_000_000_000L);         // Hours (3600 seconds)
   }
 
   private final long nanoseconds;
 
   public TimeDuration(String value) {
     super(value);
     value = value.trim().toUpperCase();
     
     // Debugging logs to check the input value and parsing result
     System.out.println("Trimmed and Uppercase Value: " + value);
 
     // Special case to handle 'min' as 'M'
     value = value.replace("MIN", "M");
     
     // Separate numeric part and unit part
     String numberPart = value.replaceAll("[^0-9.]", "");
     String unitPart = value.replaceAll("[0-9.]", "");
 
     // Debugging logs for parsed parts
     System.out.println("Number Part: " + numberPart);
     System.out.println("Unit Part: " + unitPart);
 
     // Parse the numeric value and apply the appropriate multiplier for the unit
     double numericValue = Double.parseDouble(numberPart);
     long multiplier = UNIT_MULTIPLIERS.getOrDefault(unitPart, 1L);
 
     this.nanoseconds = (long) (numericValue * multiplier);
 
     // Debugging log for the final nanoseconds value
     System.out.println("Calculated Nanoseconds: " + this.nanoseconds);
   }
 
   public long getNanoseconds() {
     return nanoseconds;
   }
 
   public long getMilliseconds() {
     return nanoseconds / 1_000_000;
   }
 
   @Override
   public TokenType type() {
     return TokenType.TIME_DURATION;
   }
 
   public String toString() {
     return String.format("%d nanoseconds", nanoseconds);
   }
}



