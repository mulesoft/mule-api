/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.i18n.I18nMessage;
import org.mule.runtime.api.metadata.resolving.FailureCode;

/**
 * Exception that represents an error related to Metadata resolving.
 * A {@link FailureCode} is required in order to describe the error reason.
 *
 * @since 1.0
 */
public final class MetadataResolvingException extends MuleException {

  private final FailureCode failure;

  public MetadataResolvingException(String message, FailureCode failure) {
    super(createStaticMessage(message));
    this.failure = failure;
  }

  public MetadataResolvingException(String message, FailureCode failure, Throwable cause) {
    super(createStaticMessage(message), cause);
    this.failure = failure;
  }

  public MetadataResolvingException(I18nMessage message, FailureCode failure) {
    super(message);
    this.failure = failure;
  }

  public MetadataResolvingException(I18nMessage message, FailureCode failure, Throwable cause) {
    super(message, cause);
    this.failure = failure;
  }

  public FailureCode getFailure() {
    return failure;
  }
}
