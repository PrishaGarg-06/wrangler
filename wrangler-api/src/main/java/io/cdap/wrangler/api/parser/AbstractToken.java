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

import com.google.gson.JsonElement;

/**
 * Base class for tokens that implements Token interface.
 */
public abstract class AbstractToken implements Token {
  protected final String rawValue;

  public AbstractToken(String rawValue) {
    this.rawValue = rawValue;
  }

  @Override
  public Object value() {
    return rawValue;
  }

  // Default to null, subclasses must override if needed
  @Override
  public JsonElement toJson() {
    return null;
  }
}
