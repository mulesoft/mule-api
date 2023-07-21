/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.lock;

import java.util.concurrent.locks.Lock;

/**
 * Factory for creating {@link Lock} instances.
 * <p>
 * All mule components that require synchronization for access shared data must be synchronized using locks from the
 * {@link LockFactory} implementation.
 * 
 * @since 1.0
 */
public interface LockFactory {

  /**
   * Provides a lock to create thread safe Mule components.
   * <p>
   * Always returns the same lock for a certain {@code lockId}
   *
   * @param lockId ID of the lock. Non null.
   * @return a {@link Lock} instance associated to the {@code lockId}
   */
  Lock createLock(String lockId);

}
