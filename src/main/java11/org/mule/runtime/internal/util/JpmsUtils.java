/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.util;

/**
 * Utilities to deal with the Java Module system.
 *
 * @since 1.5
 */
public class JpmsUtils {

  /**
   * Determines whether the caller class is a member of a named module.
   * 
   * @param caller class to determine whether it's a member of a named module.
   * @return {@code true} if the class belongs to a named module, {@code false} otherwise.
   */
  public static boolean isMemberOfNamedModule(Class<?> caller) {
    return caller.getModule().isNamed();
  }

}
