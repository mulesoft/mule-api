/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.internal.util;

/**
 * No-op implementation of {@code JpmsUtils} to use when running on JVM 8.
 *
 * @since 1.5
 */
public class JpmsUtils {

  public static boolean isMemberOfNamedModule(Class<?> caller) {
    // Nothing to do
    return false;
  }

}
