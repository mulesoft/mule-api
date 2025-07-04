/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.lock;

import java.util.concurrent.locks.Lock;

/**
 * Provides abstraction in the creation Mule locks.
 *
 * {@link LockFactory} uses instances of this interface to create locks.
 *
 * Lock implementation can be changed by replacing the LockProvider in the mule registry.
 *
 * @since 1.10
 */
public interface LockProvider {

  /**
   * Returns an instance of a {@link Lock}.
   *
   * @param lockId id that identifies the {@link Lock} instance
   * @return a {@link Lock} instance related to the lockId
   */
  Lock createLock(String lockId);

}
