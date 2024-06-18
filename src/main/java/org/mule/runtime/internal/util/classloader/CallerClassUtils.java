/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.util.classloader;

import static java.util.Arrays.asList;

import java.util.List;

public class CallerClassUtils {

  private CallerClassUtils() {
    // Nothing to do
  }

  public static ClassLoader getCallerClassClassLoader() {
    return new CallingClasses().getCallingClasses().stream()
        .map(Class::getClassLoader)
        .filter(classLoader -> classLoader != null && classLoader != CallerClassUtils.class.getClassLoader())
        .findFirst().orElseThrow(() -> new RuntimeException("Class loader not found"));
  }

  private static class CallingClasses extends SecurityManager {

    CallingClasses() {
      // Nothing to do
    }

    public List<Class<?>> getCallingClasses() {
      return asList(getClassContext());
    }

  }

}
