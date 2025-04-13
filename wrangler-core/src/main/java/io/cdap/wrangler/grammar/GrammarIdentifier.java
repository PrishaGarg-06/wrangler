//Copyright 2025 Your Organization Name
 
 // Licensed under the Apache License, Version 2.0 (the "License");
 // you may not use this file except in compliance with the License.
 // You may obtain a copy of the License at
 
   //   http://www.apache.org/licenses/LICENSE-2.0
 
 // Unless required by applicable law or agreed to in writing, software
 // distributed under the License is distributed on an "AS IS" BASIS,
 // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 // See the License for the specific language governing permissions and
 // limitations under the License.
 

package io.cdap.wrangler.grammar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
 /**
 * Parses byte size and time duration strings.
 */
public class GrammarIdentifier {
 
     // Regular expression pattern to match byte sizes (KB, MB, GB, etc.)
     private static final Pattern BYTE_SIZE_PATTERN = Pattern.compile("(\\d+)(KB|MB|GB|TB|B)");
 
     // Regular expression pattern to match time durations (s, m, h)
     private static final Pattern TIME_DURATION_PATTERN = Pattern.compile("(\\d+)(s|m|h)");
 
     // Method to parse byte size (e.g., 10KB, 2MB)
    public static long parseByteSize(String input) {
         Matcher matcher = BYTE_SIZE_PATTERN.matcher(input.trim().toUpperCase());
         if (matcher.matches()) {
             long size = Long.parseLong(matcher.group(1));
             String unit = matcher.group(2);
 
             switch (unit) {
                 case "KB":
                     return size * 1024;
                 case "MB":
                     return size * 1024 * 1024;
                 case "GB":
                     return size * 1024 * 1024 * 1024;
                 case "TB":
                     return size * 1024 * 1024 * 1024 * 1024;
                 case "B":
                 default:
                     return size;
             }
         }
         throw new IllegalArgumentException("Invalid byte size format");
    }
 
     // Method to parse time duration (e.g., 10s, 5m, 2h)
    public static long parseTimeDuration(String input) {
         Matcher matcher = TIME_DURATION_PATTERN.matcher(input.trim().toLowerCase());
         if (matcher.matches()) {
             long duration = Long.parseLong(matcher.group(1));
             String unit = matcher.group(2);
 
             switch (unit) {
                 case "s":
                     return duration * 1000;  // Convert seconds to milliseconds
                 case "m":
                     return duration * 60 * 1000;  // Convert minutes to milliseconds
                 case "h":
                     return duration * 60 * 60 * 1000;  // Convert hours to milliseconds
                 default:
                     throw new IllegalArgumentException("Invalid time duration format");
             }
        }
        throw new IllegalArgumentException("Invalid time duration format");
    }
}

