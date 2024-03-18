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
    if (DEFAULT_FACTORY == null) {
      try {
        final AbstractExpressionModuleBuilderFactory factory =
            load(AbstractExpressionModuleBuilderFactory.class, classLoader).iterator().next();
        LOGGER.info(format("Loaded ExpressionModuleBuilderFactory implementation '%s' from classloader '%s'",
                           factory.getClass().getName(), factory.getClass().getClassLoader().toString()));

        DEFAULT_FACTORY = factory;
      } catch (Throwable t) {
        LOGGER.error("Error loading ExpressionModuleBuilderFactory implementation.", t);
        throw t;
      }
    }

    return DEFAULT_FACTORY;
  }

  private static AbstractExpressionModuleBuilderFactory DEFAULT_FACTORY;

  /**
   * The implementation of this abstract class is provided by the Mule Runtime.
   * <p>
   * If more than one implementation is found, the classLoading order of those implementations will determine which one is used.
   * Information about this will be logged to aid in the troubleshooting of those cases.
   *
   * @return the implementation of this builder factory provided by the Mule Runtime.
   */
  static AbstractExpressionModuleBuilderFactory getDefaultFactory() {
    try {
      return getDefaultFactory(currentThread().getContextClassLoader());
    } catch (Throwable t) {
      ClassLoader classLoader;
      try {
        classLoader = currentThread().getContextClassLoader().loadClass("org.mule.runtime.core.api.MuleContext").getClassLoader();
      } catch (ClassNotFoundException e) {
        throw new MuleRuntimeException(createStaticMessage("Failed obtaining class loader to load ExpressionModuleBuilderFactory implementation"),
                                       e);
      }

      return getDefaultFactory(classLoader);
    }
  }

  /**
   * The implementation of this abstract class is provided by the Mule Runtime.
   * <p>
   * If more than one implementation is found, the classLoading order of those implementations will determine which one is used.
   * Information about this will be logged to aid in the troubleshooting of those cases.
   * <p>
   * <b>NOTE</b>: this method is for internal use only.
   *
   * @param classLoader the class loader where the implementation will be looked up.
   * @return the implementation of this builder factory provided by the Mule Runtime.
   */
  static AbstractExpressionModuleBuilderFactory getDefaultFactory(ClassLoader classLoader) {
    return loadFactory(classLoader);
  }

  /**
   * @param namespace The namespace of the module
   * @return a fresh {@link BindingContext.Builder} object.
   */
  protected abstract ExpressionModule.Builder create(ModuleNamespace namespace);

}
