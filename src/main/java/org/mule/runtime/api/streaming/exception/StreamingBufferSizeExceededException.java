/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.streaming.exception;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;
import org.mule.runtime.api.exception.MuleRuntimeException;

/**
 * Signals that a buffer used for streaming has reached its maximum possible size and thus the streaming operation cannot
 * continue.
 *
 * @since 1.0
 */
public final class StreamingBufferSizeExceededException extends MuleRuntimeException {

  /**
   * Creates a new instance
   * 
   * @param maxBufferSize the maximum size in bytes of the exceeded buffer
   */
  public StreamingBufferSizeExceededException(int maxBufferSize) {
    super(createStaticMessage("Buffer has exceeded its maximum size of " + maxBufferSize));
  }
}
