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

 import org.junit.Test;
 import static org.junit.Assert.assertEquals;
 import static org.junit.Assert.assertTrue;
 // Add other specific imports as needed 
import io.cdap.wrangler.api.parser.ByteSize;
/**
 * Class to represent Byte Size parsing and conversion.
 */
public class ByteSizeTest {

    @Test
    public void testByteSizeParsing_KB() {
        ByteSize byteSize = new ByteSize("10KB");
        assertEquals(10240, byteSize.getBytes());  // 10 KB = 10240 bytes
    }

    @Test
    public void testByteSizeParsing_MB() {
        ByteSize byteSize = new ByteSize("1MB");
        assertEquals(1048576, byteSize.getBytes());  // 1 MB = 1048576 bytes
    }

    @Test
    public void testByteSizeParsing_GB() {
        ByteSize byteSize = new ByteSize("2GB");
        assertEquals(2147483648L, byteSize.getBytes());  // 2 GB = 2147483648 bytes
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidByteSize() {
        new ByteSize("InvalidSize");  // Should throw an exception
    }
    
    @Test
    public void testByteSizeParsing_CaseInsensitive() {
        ByteSize byteSize = new ByteSize("10kb");
        assertEquals(10240, byteSize.getBytes());  // Should handle case insensitivity
    }
}
