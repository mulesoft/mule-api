/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.values;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.i18n.I18nMessage;

/**
 * {@link Exception} to indicate than an error occurred resolving {@link Value values}
 *
 * @since 1.0
 */
public class ValueResolvingException extends MuleException {

  public final static String UNKNOWN = "UNKNOWN";
  public final static String INVALID_PARAMETER = "INVALID_PARAMETER";
  public final static String CONNECTION_FAILURE = "CONNECTION_FAILURE";

  private String failureCode;

  public ValueResolvingException(String message, String failureCode) {
    super(createStaticMessage(message));
    this.failureCode = failureCode;
  }

  public ValueResolvingException(String message, String failureCode, Throwable cause) {
    super(createStaticMessage(message), cause);
    this.failureCode = failureCode;
  }

  public ValueResolvingException(I18nMessage message, String failureCode) {
    super(message);
    this.failureCode = failureCode;
  }

  public ValueResolvingException(I18nMessage message, String failureCode, Throwable cause) {
    super(message, cause);
    this.failureCode = failureCode;
  }

  public String getFailureCode() {
    return failureCode;
  }
}
