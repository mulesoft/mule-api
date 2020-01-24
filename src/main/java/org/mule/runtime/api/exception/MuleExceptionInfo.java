/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import static java.lang.System.lineSeparator;

import org.mule.api.annotation.NoInstantiate;
import org.mule.runtime.api.util.collection.SmallMap;

import java.io.Serializable;
import java.util.Map;

/**
 * Contains information relative to a {@link MuleException} to help in troubleshooting
 *
 * @since 1.3
 */
@NoInstantiate
public final class MuleExceptionInfo implements Serializable {

  private static final long serialVersionUID = -953920524424559726L;

  public static final String INFO_ERROR_TYPE_KEY = "Error type";
  public static final String INFO_LOCATION_KEY = "Element";
  public static final String INFO_SOURCE_DSL_KEY = "Element DSL";
  public static final String FLOW_STACK_INFO_KEY = "FlowStack";
  public static final String MISSING_DEFAULT_VALUE = "(None)";

  public static final String INFO_ERROR_TYPE_KEY_MSG = INFO_ERROR_TYPE_KEY + getColonMatchingPad(INFO_ERROR_TYPE_KEY) + ": ";
  public static final String INFO_LOCATION_KEY_MSG = INFO_LOCATION_KEY + getColonMatchingPad(INFO_LOCATION_KEY) + ": ";
  public static final String INFO_SOURCE_DSL_KEY_MSG = INFO_SOURCE_DSL_KEY + getColonMatchingPad(INFO_SOURCE_DSL_KEY) + ": ";
  public static final String FLOW_STACK_INFO_KEY_MSG = FLOW_STACK_INFO_KEY + getColonMatchingPad(FLOW_STACK_INFO_KEY) + ": ";

  private boolean alreadyLogged = false;

  private String errorType;
  private String location;
  private String dslSource;
  private Object flowStack;
  private final Map<String, Object> additionalEntries = new SmallMap<>();

  void addToSummaryMessage(StringBuilder buf) {
    buf
        .append(INFO_LOCATION_KEY_MSG)
        .append(location != null ? location : MISSING_DEFAULT_VALUE)
        .append(lineSeparator())
        .append(INFO_SOURCE_DSL_KEY_MSG)
        .append(dslSource != null ? dslSource : MISSING_DEFAULT_VALUE)
        .append(lineSeparator())
        .append(INFO_ERROR_TYPE_KEY_MSG)
        .append(errorType != null ? errorType : MISSING_DEFAULT_VALUE)
        .append(lineSeparator())
        .append(FLOW_STACK_INFO_KEY_MSG)
        .append(flowStack != null ? flowStack : MISSING_DEFAULT_VALUE)
        .append(lineSeparator());
  }

  public boolean isAlreadyLogged() {
    return alreadyLogged;
  }

  public void setAlreadyLogged(boolean alreadyLogged) {
    this.alreadyLogged = alreadyLogged;
  }

  public String getErrorType() {
    return errorType;
  }

  public void setErrorType(String errorType) {
    this.errorType = errorType;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDslSource() {
    return dslSource;
  }

  public void setDslSource(String dslSource) {
    this.dslSource = dslSource;
  }

  public Object getFlowStack() {
    return flowStack;
  }

  public void setFlowStack(Object flowStack) {
    this.flowStack = flowStack;
  }

  public void putAdditionalEntry(String name, Object info) {
    additionalEntries.put(name, info);
  }

  Map<String, Object> asMap() {
    Map<String, Object> result = new SmallMap<>();

    result.putAll(additionalEntries);

    if (errorType != null) {
      result.put(INFO_ERROR_TYPE_KEY, errorType);
    }
    if (location != null) {
      result.put(INFO_LOCATION_KEY, location);
    }
    if (dslSource != null) {
      result.put(INFO_SOURCE_DSL_KEY, dslSource);
    }
    if (flowStack != null) {
      result.put(FLOW_STACK_INFO_KEY, flowStack);
    }

    return result;
  }

  private static String repeat(char c, int len) {
    String str = String.valueOf(c);
    if (str == null) {
      return null;
    } else if (len <= 0) {
      return "";
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < len; i++) {
        stringBuilder.append(str);
      }
      return stringBuilder.toString();
    }
  }

  private static String getColonMatchingPad(String key) {
    int padSize = 22 - key.length();
    if (padSize > 0) {
      return repeat(' ', padSize);
    }
    return "";
  }

}
