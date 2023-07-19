/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
