/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.legacy.exception;

import java.util.Map;

/**
 * Provides a strategy interface for reading information from an exception in a consistent way. For example JMS 1.0.2b uses
 * linkedExceptions rather that 'cause' and SQLExceptions hold additional information that can be extracted using this interface.
 */
public interface ExceptionReader {

  String getMessage(Throwable t);

  Throwable getCause(Throwable t);

  Class<?> getExceptionType();

  /**
   * Returns a map of the non-standard information stored on the exception
   * 
   * @param t the exception to extract the information from
   * @return a map of the non-standard information stored on the exception
   */
  Map<String, Object> getInfo(Throwable t);

}
