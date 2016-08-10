/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.execution;

/**
 * A callback to notify about exceptions of a type {@code E}.
 * <p>
 * Implementations are to be reusable and thread-safe
 *
 * @param <E> the type of the exceptions to be notified
 * @param <T> the type of the value that is produced as a result of handling the exception
 * @since 1.0
 */
public interface ExceptionCallback<T, E extends Throwable> {

  /**
   * Notifies that {@code exception} has occurred
   *
   * @param exception a {@link Exception}
   * @return a nullable value that results from handling the {@code exception}
   */
  T onException(E exception);
}
