/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import static java.lang.String.format;
import static java.util.ServiceLoader.load;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class used to create {@link BindingContext.Builder} objects.
 *
 * @since 1.0
 */
public abstract class AbstractBindingContextBuilderFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBindingContextBuilderFactory.class);

  static {
    try {
      final AbstractBindingContextBuilderFactory factory = load(AbstractBindingContextBuilderFactory.class).iterator().next();
      LOGGER.info(format("Loaded BindingContextBuilderFactory implementation '%s' form classloader '%s'",
                         factory.getClass().getName(), factory.getClass().getClassLoader().toString()));

      DEFAULT_FACTORY = factory;
    } catch (Throwable t) {
      LOGGER.error("Error loading BindingContextBuilderFactory implementation.", t);
      throw t;
    }
  }

  private static final AbstractBindingContextBuilderFactory DEFAULT_FACTORY;

  /**
   * The implementation of this abstract class is provided by the Mule Runtime, and loaded during
   * this class initialization.
   * <p>
   * If more than one implementation is found, the classLoading order of those implementations
   * will determine which one is used. Information about this will be logged to aid in the
   * troubleshooting of those cases.
   *
   * @return the implementation of this builder factory provided by the Mule Runtime.
   */
  static final AbstractBindingContextBuilderFactory getDefaultFactory() {
    return DEFAULT_FACTORY;
  }

  /**
   * @return a fresh {@link BindingContext.Builder} object.
   */
  protected abstract BindingContext.Builder create();

  /**
   * @param bindingContext existing {@link BindingContext} to use as a template to create a new {@link BindingContext.Builder} instance.
   * @return a fresh {@link BindingContext.Builder} based on the template {@code bindingContext} provided.
   */
  protected abstract BindingContext.Builder create(BindingContext bindingContext);
}
