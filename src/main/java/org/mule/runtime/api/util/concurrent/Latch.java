/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
