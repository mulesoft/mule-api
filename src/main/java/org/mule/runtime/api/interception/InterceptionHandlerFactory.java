/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.interception;

/**
 * Factory for concrete {@link InterceptionHandler} objects.
 * <p>
 * When initialized, all fields in the implementation with @{@link javax.inject.Inject} will be set with an object obtained from
 * the registry by type.
 * 
 * @since 1.0
 */
public interface InterceptionHandlerFactory {

  /**
   * A new {@link InterceptionHandler} will be created with this method for each interception. That means, if 2 components are
   * being intercepted at the same time, there will be 2 different instances of {@link InterceptionHandler}, one for each
   * interception.
   * 
   * @return a fresh {@link InterceptionHandler}.
   */
  InterceptionHandler create();
}
