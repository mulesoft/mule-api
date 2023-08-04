/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.operation;

import java.util.concurrent.locks.Lock;

/**
 * In order for Mule to determine the best way to execute different components, it needs to know the type of work the components
 * will be performing.
 *
 * @since 1.0
 */
public enum ExecutionType {

  /**
   * CPU intensive processing such as calculation or transformation.
   */
  CPU_INTENSIVE,

  /**
   * Processing which neither blocks nor is CPU intensive such as message passing, filtering, routing or non-blocking IO..
   */
  CPU_LITE,

  /**
   * Blocking processing that use {@link Thread#sleep(long)}, {@link Lock#lock()}, blocking IO operations or any other technique
   * that blocks the current thread during processing.
   */
  BLOCKING

}
