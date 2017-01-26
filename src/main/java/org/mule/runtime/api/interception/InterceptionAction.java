/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.interception;

/**
 * Allows the implementations of {@link InterceptionHandler#before(String, java.util.Map, InterceptionEvent, InterceptionAction)}
 * to control the result of the interception.
 * 
 * @since 1.0
 */
public interface InterceptionAction {

  /**
   * Indicates that the current interception chain must continue, proceeding with the next {@link InterceptionHandler handlers} in
   * the chain (if any) and the intercepted compoent.
   */
  void proceed();
}
