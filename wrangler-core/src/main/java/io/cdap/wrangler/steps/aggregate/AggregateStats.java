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
package io.cdap.wrangler.steps.aggregate;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.ExecutorContext;
import io.cdap.wrangler.api.Arguments;
import io.cdap.wrangler.api.parser.UsageDefinition;
import io.cdap.wrangler.api.parser.TokenType;
import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.TimeDuration;

import java.util.List;
import java.util.ArrayList;

/**
 * This class performs aggregation operations on data, such as computing total and average values
 * for specified columns. It supports aggregating byte sizes and time durations.
 */
public class AggregateStats implements Directive {
    private String byteCol;
    private String timeCol;
    private String outputByteCol;
    private String outputTimeCol;

    @Override
    public UsageDefinition define() {
        return UsageDefinition.builder("aggregate-stats")
                .addArgument("arg1", "byte column")
                .addArgument("arg2", "time column")
                .addArgument("arg3", "output byte column")
                .addArgument("arg4", "output time column")
                .build();
    }

    @Override
    public void initialize(Arguments arguments) {
        byteCol = arguments.value("arg1");
        timeCol = arguments.value("arg2");
        outputByteCol = arguments.value("arg3");
        outputTimeCol = arguments.value("arg4");
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext context) {
        long totalBytes = 0;
        long totalNanos = 0;

        for (Row row : rows) {
            Object sizeValue = row.getValue(byteCol);
            Object timeValue = row.getValue(timeCol);

            if (sizeValue == null || timeValue == null) {
                continue;  // Skip rows with null values
            }

            if (sizeValue instanceof String) {
                try {
                    ByteSize byteSize = new ByteSize((String) sizeValue);
                    totalBytes += byteSize.getBytes();
                } catch (Exception e) {
                    System.err.println("Error parsing byte size: " + sizeValue);
                }
            }

            if (timeValue instanceof String) {
                try {
                    TimeDuration timeDuration = new TimeDuration((String) timeValue);
                    totalNanos += timeDuration.getNanoseconds();
                } catch (Exception e) {
                    System.err.println("Error parsing time duration: " + timeValue);
                }
            }
        }

        List<Row> result = new ArrayList<>();
        Row output = new Row();
        
        double totalSizeMb = totalBytes / (1024.0 * 1024.0); // Convert bytes to MB
        double totalTimeSec = totalNanos / 1_000_000_000.0;   // Convert nanoseconds to seconds
        
        output.add(outputByteCol, totalSizeMb);
        output.add(outputTimeCol, totalTimeSec);
        
        result.add(output);
        return result;
    }

    @Override
    public void destroy() {
        // Nothing to clean up
    }
}

