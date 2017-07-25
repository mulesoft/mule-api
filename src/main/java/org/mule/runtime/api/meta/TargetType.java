/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta;

import org.mule.runtime.api.message.Message;

/**
 * Enumerates the different types of output that can be stored in a variable used as target.
 *
 * @since 1.0
 */
public enum TargetType {
  /**
   * Only stores the {@link Message#getPayload()}. This is the default value.
   */
  PAYLOAD,

  /**
   * The whole {@link Message} is stored. This is useful for situations in which you need access to other things
   * of the message which are not the payload (i.e. the message attributes).
   */
  MESSAGE
}
