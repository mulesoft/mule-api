/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.message;

import static org.mule.runtime.api.util.classloader.MuleImplementationLoaderUtils.getMuleImplementationsLoader;
import static org.mule.runtime.api.util.classloader.MuleImplementationLoaderUtils.isResolveMuleImplementationLoadersDynamically;

import static java.lang.String.format;
import static java.util.ServiceLoader.load;
import org.mule.runtime.api.message.Message.Builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class used to create {@link Builder} objects.
 *
 * @since 1.0
 */
public abstract class AbstractMuleMessageBuilderFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMuleMessageBuilderFactory.class);

  static {
    if (!isResolveMuleImplementationLoadersDynamically()) {
      DEFAULT_FACTORY = loadFactory(AbstractMuleMessageBuilderFactory.class.getClassLoader());
    } else {
      DEFAULT_FACTORY = null;
    }
  }

  private static AbstractMuleMessageBuilderFactory loadFactory(ClassLoader classLoader) {
    try {
      final AbstractMuleMessageBuilderFactory factory = load(AbstractMuleMessageBuilderFactory.class,
                                                             classLoader).iterator()
                                                                 .next();
      LOGGER.info(format("Loaded MuleMessageBuilderFactory implementation '%s' from classloader '%s'",
                         factory.getClass().getName(), factory.getClass().getClassLoader().toString()));

      return factory;
    } catch (Throwable t) {
      LOGGER.error("Error loading MuleMessageBuilderFactory implementation.", t);
      throw t;
    }
  }

  private static final AbstractMuleMessageBuilderFactory DEFAULT_FACTORY;

  /**
   * The implementation of this abstract class is provided by the Mule Runtime.
   * <p>
   * If more than one implementation is found, the classLoading order of those implementations will determine which one is used.
   * Information about this will be logged to aid in the troubleshooting of those cases.
   *
   * @return the implementation of this builder factory provided by the Mule Runtime.
   */
  static final AbstractMuleMessageBuilderFactory getDefaultFactory() {
    return DEFAULT_FACTORY != null ? DEFAULT_FACTORY : loadFactory(getMuleImplementationsLoader());
  }

  /**
   * @return a fresh {@link Builder} object.
   */
  protected abstract Message.PayloadBuilder create();

  /**
   * @param message existing {@link Message} to use as a template to create a new {@link Builder} instance.
   * @return a fresh {@link Builder} based on the template {@code message} provided.
   */
  protected abstract Message.Builder create(Message message);

}
