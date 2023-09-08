/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Defines a simple latch
 *
 * @since 1.0
 */
public class Latch extends CountDownLatch {

  /**
   * Creates a new instance
   */
  public Latch() {
    super(1);
  }

  /**
   * Releases all waiting threads
   */
  public void release() {
    countDown();
  }

}
