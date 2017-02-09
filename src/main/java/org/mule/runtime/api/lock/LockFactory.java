/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.lock;

import java.util.concurrent.locks.Lock;

/**
 * Factory for creating Lock instances.
 * <p>
 * All mule components that require synchronization for access shared data must be synchronized using locks from the LockFactory
 * provided by MuleContext
 * 
 * @since 1.0
 */
public interface LockFactory {

  /**
   * Provides a lock to create thread safe Mule components.
   * <p>
   * Always returns the same lock for a certain {@code lockId}
   *
   * @param lockId Id of the lock
   * @return a {@link Lock} instance associated to the {@code lockId}
   */
  Lock createLock(String lockId);

}
