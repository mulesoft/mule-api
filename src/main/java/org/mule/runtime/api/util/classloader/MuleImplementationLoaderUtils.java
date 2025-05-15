/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.classloader;

import static org.mule.runtime.api.util.MuleSystemProperties.RESOLVE_MULE_IMPLEMENTATIONS_LOADER_DYNAMICALLY;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import static org.mule.runtime.api.util.Preconditions.checkState;

import static java.lang.Boolean.getBoolean;

import org.mule.runtime.internal.util.classloader.CallerClassUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Utility to determine the {@link ClassLoader} from where API's implementations can be loaded through SPI and their resources can
 * be located.
 *
 * @since 1.8
 */
public class MuleImplementationLoaderUtils {

  private static final boolean DYNAMIC_RESOLUTION = getBoolean(RESOLVE_MULE_IMPLEMENTATIONS_LOADER_DYNAMICALLY);

  private static final String MULE_RUNTIME_CORE_API_CLASS = "org.mule.runtime.core.api.MuleContext";

  private static final ConcurrentMap<ClassLoader, ClassLoader> muleImplementationsLoaders = new ConcurrentHashMap<>();
  private static ClassLoader muleImplementationsLoader;
  private static boolean forceLookup = false;

  private MuleImplementationLoaderUtils() {
    // Nothing to do
  }

  /**
   * @return whether the {@link ClassLoader} for API's implementations and their resources should be determined dynamically.
   */
  public static boolean isResolveMuleImplementationLoadersDynamically() {
    return DYNAMIC_RESOLUTION;
  }

  /**
   * @return the {@link ClassLoader} from where to obtain API's implementations and their resources.
   */
  public static ClassLoader getMuleImplementationsLoader() {
    if (!isResolveMuleImplementationLoadersDynamically()) {
      return muleImplementationsLoader != null ? muleImplementationsLoader : MuleImplementationLoaderUtils.class.getClassLoader();
    }

    if (forceLookup) {
      ClassLoader nonMuleApiClassLoader = CallerClassUtils.getCallerClassClassLoader(MULE_RUNTIME_CORE_API_CLASS);

      return muleImplementationsLoaders.computeIfAbsent(nonMuleApiClassLoader, key -> {
        try {
          return nonMuleApiClassLoader.loadClass(MULE_RUNTIME_CORE_API_CLASS).getClassLoader();
        } catch (ClassNotFoundException e) {
          throw new RuntimeException(e);
        }
      });
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
