/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import static java.lang.String.valueOf;
import static java.lang.System.lineSeparator;

import org.mule.api.annotation.NoInstantiate;
import org.mule.runtime.api.message.ErrorType;
import org.mule.runtime.api.util.collection.SmallMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Contains information relative to a {@link MuleException} to help in troubleshooting
 *
 * @since 1.3
 */
@NoInstantiate
public final class MuleExceptionInfo implements Serializable {

  private static final long serialVersionUID = -6844204561753057827L;

  public static final String INFO_ERROR_TYPE_KEY = "Error type";
  public static final String INFO_CAUSED_BY_KEY = "Caused by";
  public static final String INFO_LOCATION_KEY = "Element";
  public static final String INFO_SOURCE_DSL_KEY = "Element DSL";
  public static final String FLOW_STACK_INFO_KEY = "FlowStack";
  public static final String MISSING_DEFAULT_VALUE = "(None)";


  public static final String INFO_ERROR_TYPE_KEY_MSG = INFO_ERROR_TYPE_KEY + getColonMatchingPad(INFO_ERROR_TYPE_KEY) + ": ";
  public static final String INFO_CAUSED_BY_KEY_MSG = INFO_CAUSED_BY_KEY + getColonMatchingPad(INFO_CAUSED_BY_KEY) + ": ";
  public static final String INFO_LOCATION_KEY_MSG = INFO_LOCATION_KEY + getColonMatchingPad(INFO_LOCATION_KEY) + ": ";
  public static final String INFO_SOURCE_DSL_KEY_MSG = INFO_SOURCE_DSL_KEY + getColonMatchingPad(INFO_SOURCE_DSL_KEY) + ": ";
  public static final String FLOW_STACK_INFO_KEY_MSG = FLOW_STACK_INFO_KEY + getColonMatchingPad(FLOW_STACK_INFO_KEY) + ": ";

  private boolean alreadyLogged = false;

  private ErrorType errorType;
  private List<MuleException> suppressedCauses = new ArrayList<>(4);
  private String location;
  private String dslSource;
  private Serializable flowStack;
  private final SmallMap<String, Object> additionalEntries = new SmallMap<>();

  public void addToSummaryMessage(StringBuilder buf) {
    buf
        .append(INFO_LOCATION_KEY_MSG)
        .append(location != null ? location : MISSING_DEFAULT_VALUE)
        .append(lineSeparator())
        .append(INFO_SOURCE_DSL_KEY_MSG)
        .append(dslSource != null ? dslSource : MISSING_DEFAULT_VALUE)
        .append(lineSeparator())
        .append(INFO_ERROR_TYPE_KEY_MSG)
        .append(errorType != null ? errorType.toString() : MISSING_DEFAULT_VALUE);
    if (!suppressedCauses.isEmpty()) {
      writeSuppressedCauses(buf);
    }
    buf
        .append(lineSeparator())
        .append(FLOW_STACK_INFO_KEY_MSG)
        .append(flowStack != null ? flowStack : MISSING_DEFAULT_VALUE)
        .append(lineSeparator());
  }

  private void writeSuppressedCauses(StringBuilder buffer) {
    final String PADDING = repeat(' ', INFO_CAUSED_BY_KEY_MSG.length());
    buffer
        .append(lineSeparator())
        .append(INFO_CAUSED_BY_KEY_MSG);
    Iterator<MuleException> causes = suppressedCauses.iterator();
    writeCause(buffer, causes.next());
    while (causes.hasNext()) {
      buffer.append(lineSeparator());
      buffer.append(PADDING);
      writeCause(buffer, causes.next());
    }
  }

  private void writeCause(StringBuilder buffer, MuleException causedByException) {
    ErrorType causedByErrorType = causedByException.getExceptionInfo().getErrorType();
    if (causedByErrorType != null) {
      buffer.append(causedByErrorType).append(": ");
    }
    buffer.append(causedByException.getMessage());
  }

  public boolean isAlreadyLogged() {
    return alreadyLogged;
  }

  public void setAlreadyLogged(boolean alreadyLogged) {
    this.alreadyLogged = alreadyLogged;
  }

  public ErrorType getErrorType() {
    return errorType;
  }

  public void setErrorType(ErrorType errorType) {
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

  public Serializable getFlowStack() {
    return flowStack;
  }

  public void setFlowStack(Serializable flowStack) {
    this.flowStack = flowStack;
  }

  public List<MuleException> getSuppressedCauses() {
    return (suppressedCauses);
  }

  public void setSuppressedCauses(List<MuleException> suppressedCauses) {
    this.suppressedCauses = suppressedCauses;
  }

  public void addSuppressedCause(MuleException cause) {
    this.suppressedCauses.add(cause);
  }

  public void putAdditionalEntry(String name, Object info) {
    additionalEntries.put(name, info);
  }

  Map<String, Object> getAdditionalEntries() {
    Map<String, Object> result = new SmallMap<>();
    result.putAll(additionalEntries);
    return result;
  }

  Map<String, Object> asMap() {
    Map<String, Object> result = new SmallMap<>();

    result.putAll(additionalEntries);

    if (errorType != null) {
      result.put(INFO_ERROR_TYPE_KEY, errorType);
    }
    if (!suppressedCauses.isEmpty()) {
      result.put(INFO_CAUSED_BY_KEY, suppressedCauses);
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
    String str = valueOf(c);
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
