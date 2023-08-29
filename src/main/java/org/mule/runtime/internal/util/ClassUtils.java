/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.util;

import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class ClassUtils {

  /**
   * Load a given resource.
   * <p/>
   * This method will try to load the resource using the following methods (in order):
   * <ul>
   * <li>From {@link Thread#getContextClassLoader() Thread.currentThread().getContextClassLoader()}
   * <li>From {@link Class#getClassLoader() ClassUtils.class.getClassLoader()}
   * <li>From the {@link Class#getClassLoader() callingClass.getClassLoader() }
   * </ul>
   *
   * @param resourceName The name of the resource to load
   * @param callingClass The Class object of the calling object
   * @return A URL pointing to the resource to load or null if the resource is not found
   */
  public static URL getResource(final String resourceName, final Class<?> callingClass) {
    URL url = AccessController.doPrivileged((PrivilegedAction<URL>) () -> {
      final ClassLoader cl = Thread.currentThread().getContextClassLoader();
      return cl != null ? cl.getResource(resourceName) : null;
    });

    if (url == null) {
      url = AccessController
          .doPrivileged((PrivilegedAction<URL>) () -> ClassUtils.class.getClassLoader().getResource(resourceName));
    }

    if (url == null) {
      url = AccessController.doPrivileged((PrivilegedAction<URL>) () -> callingClass.getClassLoader().getResource(resourceName));
    }

    return url;
  }

}
