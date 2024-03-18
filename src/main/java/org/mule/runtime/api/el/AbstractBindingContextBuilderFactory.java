/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static java.util.ServiceLoader.load;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.exception.MuleRuntimeException;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class used to create {@link BindingContext.Builder} objects.
 *
 * @since 1.0
 */
@NoImplement
public abstract class AbstractBindingContextBuilderFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBindingContextBuilderFactory.class);

  private static AbstractBindingContextBuilderFactory loadFactory(ClassLoader classLoader) {
    try {
      final AbstractBindingContextBuilderFactory factory =
          load(AbstractBindingContextBuilderFactory.class, classLoader).iterator().next();
      LOGGER.info(format("Loaded BindingContextBuilderFactory implementation '%s' from classloader '%s'",
                         factory.getClass().getName(), factory.getClass().getClassLoader().toString()));

      return factory;
    } catch (Throwable t) {
      LOGGER.error("Error loading BindingContextBuilderFactory implementation.", t);
      throw t;
    }
  }

  private static final Map<ClassLoader, AbstractBindingContextBuilderFactory> factoriesMap = new HashMap<>();

  /**
   * The implementation of this abstract class is provided by the Mule Runtime.
   * <p>
   * If more than one implementation is found, the classLoading order of those implementations will determine which one is used.
   * Information about this will be logged to aid in the troubleshooting of those cases.
   *
   * @return the implementation of this builder factory provided by the Mule Runtime.
   */
  static AbstractBindingContextBuilderFactory getDefaultFactory() {
    ClassLoader contextClassLoader = currentThread().getContextClassLoader();
    try {
      return getDefaultFactory(contextClassLoader);
    } catch (Throwable t) {
      ClassLoader classLoader;
      try {
        classLoader = contextClassLoader.loadClass("org.mule.runtime.core.api.MuleContext").getClassLoader();
      } catch (ClassNotFoundException e) {
        throw new MuleRuntimeException(createStaticMessage("Failed obtaining class loader to load BindingContextBuilderFactory implementation"),
                                       e);
      }

      AbstractBindingContextBuilderFactory defaultFactory = null;
      try {
        defaultFactory = getDefaultFactory(classLoader);
      } finally {
        // Next time this thread's context class loader is used to retrieve the factory, we return it instead of computing it
        // again
        if (defaultFactory != null) {
          factoriesMap.put(contextClassLoader, defaultFactory);
        }
      }

      return defaultFactory;
    }
  }

  private static AbstractBindingContextBuilderFactory getDefaultFactory(ClassLoader classLoader) {
    return factoriesMap.computeIfAbsent(classLoader, AbstractBindingContextBuilderFactory::loadFactory);
  }

  /**
   * @return a fresh {@link BindingContext.Builder} object.
   */
  protected abstract BindingContext.Builder create();

  /**
   * @param bindingContext existing {@link BindingContext} to use as a template to create a new {@link BindingContext.Builder}
   *                       instance.
   * @return a fresh {@link BindingContext.Builder} based on the template {@code bindingContext} provided.
   */
  protected abstract BindingContext.Builder create(BindingContext bindingContext);
}
