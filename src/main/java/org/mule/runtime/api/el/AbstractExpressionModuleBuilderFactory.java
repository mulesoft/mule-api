/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import static java.lang.String.format;
import static java.util.ServiceLoader.load;

import org.mule.api.annotation.NoImplement;

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

  static {
    try {
      final AbstractExpressionModuleBuilderFactory factory = load(AbstractExpressionModuleBuilderFactory.class).iterator().next();
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug(format("Loaded ExpressionModuleBuilderFactory implementation '%s' from classloader '%s'",
                            factory.getClass().getName(), factory.getClass().getClassLoader().toString()));
      }

      DEFAULT_FACTORY = factory;
    } catch (Throwable t) {
      LOGGER.error("Error loading ExpressionModuleBuilderFactory implementation.", t);
      throw t;
    }
  }

  private static final AbstractExpressionModuleBuilderFactory DEFAULT_FACTORY;

  /**
   * The implementation of this abstract class is provided by the Mule Runtime, and loaded during this class initialization.
   * <p>
   * If more than one implementation is found, the classLoading order of those implementations will determine which one is used.
   * Information about this will be logged to aid in the troubleshooting of those cases.
   *
   * @return the implementation of this builder factory provided by the Mule Runtime.
   */
  static AbstractExpressionModuleBuilderFactory getDefaultFactory() {
    return DEFAULT_FACTORY;
  }

  /**
   * @param namespace The namespace of the module
   * @return a fresh {@link BindingContext.Builder} object.
   */
  protected abstract ExpressionModule.Builder create(ModuleNamespace namespace);

}
