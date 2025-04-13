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
 * ByteSize class to handle the parsing and conversion of byte size units.
 */
 public class ByteSize extends AbstractToken {
   private static final Map<String, Long> UNIT_MULTIPLIERS = new HashMap<>();
   static {
     UNIT_MULTIPLIERS.put("B", 1L);
     UNIT_MULTIPLIERS.put("KB", 1024L);
     UNIT_MULTIPLIERS.put("MB", 1024L * 1024);
     UNIT_MULTIPLIERS.put("GB", 1024L * 1024 * 1024);
   }
 
   private final long bytes;
 
   public ByteSize(String value) {
     super(value);
     value = value.trim().toUpperCase();
 
     String numberPart = value.replaceAll("[^0-9.]", "");
     String unitPart = value.replaceAll("[0-9.]", "");
 
     double numericValue = Double.parseDouble(numberPart);
     long multiplier = UNIT_MULTIPLIERS.getOrDefault(unitPart, 1L);
 
     this.bytes = (long) (numericValue * multiplier);
   }
 
   public long getBytes() {
     return bytes;
   }
 
   @Override
   public TokenType type() {
     return TokenType.BYTE_SIZE;
   }
 
   public String toString() {
     return String.format("%d bytes", bytes);
   }
}

