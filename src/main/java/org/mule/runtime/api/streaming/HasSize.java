/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.streaming;

import org.mule.api.annotation.NoImplement;

/**
 * A generic contract for an object which can be described with a size of some sort.
 * <p>
 * This interface doesn't assign any specific meaning to the word {@code size}, although most common scenarios will most likely
 * include container objects such as {@link Cursor cursors}.
 *
 * @since 1.0
 */
@NoImplement
public interface HasSize {

  /**
   * Returns {@code this} object's size.
   * <p>
   * In some scenarios, it might not be possible/convenient to actually retrieve this value or it might not be available at this
   * point. {@code -1} is returned in such a case.
   */
  int getSize();
}
