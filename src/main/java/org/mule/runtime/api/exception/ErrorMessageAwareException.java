/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import org.mule.runtime.api.message.Message;

/**
 * Exception that holds a {@link Message} regarding the error that occurred. When this exception is analysed to create an
 * {@link org.mule.runtime.api.message.Error}, it will feature the message as it's error message and the exception will be the
 * specified one.
 *
 * @since 1.0
 */
public interface ErrorMessageAwareException {

  /**
   * Retrieves the error {@link Message} to be used in the error creation.
   *
   * @return the custom {@link Message} regarding the error
   */
  Message getErrorMessage();

}
