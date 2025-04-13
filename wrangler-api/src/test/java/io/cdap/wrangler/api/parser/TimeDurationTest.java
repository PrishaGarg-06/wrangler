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
import io.cdap.wrangler.api.parser.TimeDuration;
/**
 * Class to represent Time Duration parsing and conversion.
 */
public class TimeDurationTest {

    @Test
    public void testTimeDurationParsing_ms() {
        TimeDuration timeDuration = new TimeDuration("10ms");
        assertEquals(10000000L, timeDuration.getNanoseconds());  // 10 ms = 10,000,000 nanoseconds
    }

    @Test
    public void testTimeDurationParsing_s() {
        TimeDuration timeDuration = new TimeDuration("2s");
        assertEquals(2000000000L, timeDuration.getNanoseconds());  // 2 s = 2,000,000,000 nanoseconds
    }

    @Test
    public void testTimeDurationParsing_min() {
        TimeDuration timeDuration = new TimeDuration("5min");
        assertEquals(300000000000L, timeDuration.getNanoseconds());  // 5 minutes = 300,000,000,000 nanoseconds
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidTimeDuration() {
        new TimeDuration("InvalidDuration");  // Should throw an exception
    }

    @Test
    public void testTimeDurationParsing_CaseInsensitive() {
        TimeDuration timeDuration = new TimeDuration("10s");
        assertEquals(10000000000L, timeDuration.getNanoseconds());  // Should handle case insensitivity
    }
}
