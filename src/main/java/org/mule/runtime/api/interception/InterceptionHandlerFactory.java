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
   * An {@link InterceptionHandler} will be fetched from this method for each interception.
   * 
   * @return a fresh {@link InterceptionHandler}.
   */
  InterceptionHandler create();
}
