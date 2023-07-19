/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.exception;

import static java.util.Collections.emptyMap;

import org.mule.runtime.api.legacy.exception.ExceptionReader;

import java.util.Map;

/**
 * This is the default exception reader used if there is no specific one registered for the current exception.
 */
public final class DefaultExceptionReader implements ExceptionReader {

  @Override
  public String getMessage(Throwable t) {
    return t.getMessage();
  }

  @Override
  public Throwable getCause(Throwable t) {
    return t.getCause();
  }

  @Override
  public Class<?> getExceptionType() {
    return Throwable.class;
  }

  /**
   * Returns a map of the non-standard information stored on the exception
   *
   * @return a map of the non-standard information stored on the exception
   */
  @Override
  public Map<String, Object> getInfo(Throwable t) {
    return (t instanceof MuleException ? ((MuleException) t).getInfo() : emptyMap());
  }
}
