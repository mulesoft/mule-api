/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.classloader;

import static org.mule.runtime.api.util.Preconditions.checkArgument;
import static org.mule.runtime.api.util.Preconditions.checkState;

import org.mule.runtime.internal.util.classloader.CallerClassUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility to determine the right {@link ClassLoader} from where API's implementations can be loaded through SPI and their
 * resources can be located.
 *
 * @since 1.8
 */
public class MuleImplementationLoaderUtils {

  private static final Map<ClassLoader, ClassLoader> muleImplementationsLoaders = new HashMap();
  private static ClassLoader muleImplementationsLoader;
  private static boolean forceLookup = false;

  private MuleImplementationLoaderUtils() {
    // Nothing to do
  }

  public static ClassLoader getMuleImplementationsLoader() {
    if (muleImplementationsLoader == null && !forceLookup) {
      return MuleImplementationLoaderUtils.class.getClassLoader();
    }

    if (forceLookup) {
      ClassLoader nonMuleApiClassLoader = CallerClassUtils.getCallerClassClassLoader();

      ClassLoader muleImplLoader = muleImplementationsLoaders.get(nonMuleApiClassLoader);
      if (muleImplLoader == null) {
        try {
          muleImplLoader = nonMuleApiClassLoader.loadClass("org.mule.runtime.core.api.MuleContext").getClassLoader();
          muleImplementationsLoaders.put(nonMuleApiClassLoader, muleImplLoader);
        } catch (ClassNotFoundException e) {
          throw new RuntimeException(e);
        }
      }

      return muleImplLoader;
    } else {
      return muleImplementationsLoader;
    }
  }

  /**
   * The given {@link ClassLoader} will be used as the implementations class loader.
   * 
   * @param classLoader {@link ClassLoader} to look for implementations.
   * @throws IllegalStateException    if the implementations class loader has been already set or the dynamic lookup was
   *                                  configured.
   * @throws IllegalArgumentException if the given {@link ClassLoader} is {@code null}.
   */
  public static void setMuleImplementationsLoader(ClassLoader classLoader) {
    checkState(muleImplementationsLoader == null, "'muleImplementationsLoader' has been already set");
    checkState(!forceLookup, "Implementation loaders will be dynamically looked up");
    checkArgument(classLoader != null, "'classLoader' can't be null");

    muleImplementationsLoader = classLoader;
  }

  /**
   * The implementations class loader will be determined dynamically.
   *
   * @throws IllegalStateException if the implementations class loader has been already set.
   */
  public static void forceLookup() {
    checkState(muleImplementationsLoader == null, "'muleImplementationsLoader' has been set");

    forceLookup = true;
  }

}
