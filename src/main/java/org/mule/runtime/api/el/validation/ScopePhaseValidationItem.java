/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el.validation;

import java.util.Map;

/**
 * Represents a Scope phase validation item
 *
 * @since 1.5.0
 */
public final class ScopePhaseValidationItem {

  private final String kind;
  private final String message;
  private final Map<String, String> params;
  private final Location location;

  public ScopePhaseValidationItem(String kind, String message, Map<String, String> params, Location location) {
    this.kind = kind;
    this.message = message;
    this.params = params;
    this.location = location;
  }

  public String getKind() {
    return kind;
  }

  public String getMessage() {
    return message;
  }

  public Map<String, String> getParams() {
    return params;
  }

  public Location getLocation() {
    return location;
  }
}
