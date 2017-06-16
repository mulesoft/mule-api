/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import org.mule.runtime.api.legacy.exception.ExceptionReader;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the default exception reader used if there is no specific one registered for the current exception.
 */
final class DefaultExceptionReader implements ExceptionReader {

  private Map<String, Object> info = new HashMap<>();

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
   * Returns a map of the non-stanard information stored on the exception
   * 
   * @return a map of the non-stanard information stored on the exception
   */
  @Override
  public Map<String, Object> getInfo(Throwable t) {
    return info;
  }
}
