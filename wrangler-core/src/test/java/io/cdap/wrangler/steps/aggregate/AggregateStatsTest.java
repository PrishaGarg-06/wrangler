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
import io.cdap.wrangler.api.DirectiveLoadException;
import io.cdap.wrangler.api.DirectiveParseException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.RecipeException;
import io.cdap.wrangler.TestingRig;
import java.util.Arrays;
import java.util.List;

public class AggregateStatsTest {

    @Test
    public void testAggregateStatsTotal() throws RecipeException, DirectiveParseException, DirectiveLoadException {
        // Setup input data with rows having byte size and time duration
        List<Row> rows = Arrays.asList(
            new Row().add("data_transfer_size", "10KB").add("response_time", "100ms"),
            new Row().add("data_transfer_size", "20KB").add("response_time", "200ms"),
            new Row().add("data_transfer_size", "30KB").add("response_time", "300ms")
        );

        // Define the recipe for aggregation
        String[] recipe = new String[] { 
            "aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec"
        };

        // Execute the aggregation using the TestingRig
        List<Row> results = TestingRig.execute(recipe, rows);

        // Validate the output
        assertEquals(1, results.size());

        // Calculate expected values based on the input data
        double expectedTotalSizeMb = (10 + 20 + 30) / 1024.0; // KB to MB
        double expectedTotalTimeSec = (100 + 200 + 300) / 1000.0; // ms to sec

        assertEquals(expectedTotalSizeMb, ((Number) results.get(0).getValue("total_size_mb")).doubleValue(), 0.001);
        assertEquals(expectedTotalTimeSec, ((Number) results.get(0).getValue("total_time_sec")).doubleValue(), 0.001);
    }

    @Test
    public void testAggregateStatsAverage() throws RecipeException, DirectiveParseException, DirectiveLoadException {
        // Setup input data with rows having byte size and time duration
        List<Row> rows = Arrays.asList(
            new Row().add("data_transfer_size", "10KB").add("response_time", "100ms"),
            new Row().add("data_transfer_size", "20KB").add("response_time", "200ms")
        );

        // Define the recipe for average aggregation
        String[] recipe = new String[] { 
            "aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec"
        };

        // Execute the aggregation using the TestingRig
        List<Row> results = TestingRig.execute(recipe, rows);

        // Validate the output
        assertEquals(1, results.size());

        // Calculate expected values based on the input data
        double expectedAvgSizeMb = (10 + 20) / 2.0 / 1024.0; // KB to MB, average
        double expectedAvgTimeSec = (100 + 200) / 2.0 / 1000.0; // ms to sec, average

        assertEquals(expectedAvgSizeMb, ((Number) results.get(0).getValue("total_size_mb")).doubleValue(), 0.001);
        assertEquals(expectedAvgTimeSec, ((Number) results.get(0).getValue("total_time_sec")).doubleValue(), 0.001);
    }

    @Test
    public void testAggregateStatsEdgeCase() throws RecipeException, DirectiveParseException, DirectiveLoadException {
        // Empty row data
        List<Row> rows = Arrays.asList();

        // Define the recipe for aggregation
        String[] recipe = new String[] { 
            "aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec"
        };

        // Execute the aggregation using the TestingRig
        List<Row> results = TestingRig.execute(recipe, rows);

        // Validate that no rows were returned
        assertEquals(0, results.size());
    }
}

