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

  /**
   * Returns the first {@link ClassLoader} of a caller class that has access to class with the given name.
   *
   * @param accessibleClassName the name of the class that the class loader of the caller class should be able to access.
   * @return a {@link ClassLoader} with access to {@code accessibleClassName}.
   */
  public static ClassLoader getCallerClassClassLoader(String accessibleClassName) {
    return new CallingClasses().getCallingClasses().stream()
        .map(Class::getClassLoader)
        .filter(classLoader -> classLoader != null && classLoader != CallerClassUtils.class.getClassLoader())
        .filter(classLoader -> {
          try {
            classLoader.loadClass(accessibleClassName).getClassLoader();
            return true;
          } catch (ClassNotFoundException e) {
            return false;
          }
        })
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
