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
 * Factory class used to create {@link ExpressionModule.Builder} objects.
 *
 * @since 1.0
 */
@NoImplement
public abstract class AbstractExpressionModuleBuilderFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractExpressionModuleBuilderFactory.class);

  private static AbstractExpressionModuleBuilderFactory loadFactory(ClassLoader classLoader) {
    try {
      final AbstractExpressionModuleBuilderFactory factory =
          load(AbstractExpressionModuleBuilderFactory.class, classLoader).iterator().next();
      LOGGER.info(format("Loaded ExpressionModuleBuilderFactory implementation '%s' from classloader '%s'",
                         factory.getClass().getName(), factory.getClass().getClassLoader().toString()));

      return factory;
    } catch (Throwable t) {
      LOGGER.error("Error loading ExpressionModuleBuilderFactory implementation.", t);
      throw t;
    }
  }

  private static final Map<ClassLoader, AbstractExpressionModuleBuilderFactory> factoriesMap = new HashMap<>();

  /**
   * The implementation of this abstract class is provided by the Mule Runtime.
   * <p>
   * If more than one implementation is found, the classLoading order of those implementations will determine which one is used.
   * Information about this will be logged to aid in the troubleshooting of those cases.
   *
   * @return the implementation of this builder factory provided by the Mule Runtime.
   */
  static AbstractExpressionModuleBuilderFactory getDefaultFactory() {
    ClassLoader contextClassLoader = currentThread().getContextClassLoader();
    try {
      return getDefaultFactory(contextClassLoader);
    } catch (Throwable t) {
      ClassLoader classLoader;
      try {
        classLoader = contextClassLoader.loadClass("org.mule.runtime.core.api.MuleContext").getClassLoader();
      } catch (ClassNotFoundException e) {
        throw new MuleRuntimeException(createStaticMessage("Failed obtaining class loader to load ExpressionModuleBuilderFactory implementation"),
                                       e);
      }

      AbstractExpressionModuleBuilderFactory defaultFactory = null;
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

  private static AbstractExpressionModuleBuilderFactory getDefaultFactory(ClassLoader classLoader) {
    return factoriesMap.computeIfAbsent(classLoader, AbstractExpressionModuleBuilderFactory::loadFactory);
  }

  /**
   * @param namespace The namespace of the module
   * @return a fresh {@link BindingContext.Builder} object.
   */
  protected abstract ExpressionModule.Builder create(ModuleNamespace namespace);

}
