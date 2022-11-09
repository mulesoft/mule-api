/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.parameterization;

import static java.lang.String.format;
import static java.util.ServiceLoader.load;

import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractComponentParameterizationBuilderFactory<M extends ParameterizedModel> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractComponentParameterizationBuilderFactory.class);

  static {
    try {
      final AbstractComponentParameterizationBuilderFactory factory =
          load(AbstractComponentParameterizationBuilderFactory.class).iterator().next();
      LOGGER.info(format("Loaded AbstractComponentParameterizationBuilderFactory implementation '%s' from classloader '%s'",
                         factory.getClass().getName(), factory.getClass().getClassLoader().toString()));

      DEFAULT_FACTORY = factory;
    } catch (Throwable t) {
      LOGGER.error("Error loading AbstractComponentParameterizationBuilderFactory implementation.", t);
      throw t;
    }
  }

  private static final AbstractComponentParameterizationBuilderFactory DEFAULT_FACTORY;

  /**
   * The implementation of this abstract class is provided by the Mule Runtime, and loaded during this class initialization.
   * <p>
   * If more than one implementation is found, the classLoading order of those implementations will determine which one is used.
   * Information about this will be logged to aid in the troubleshooting of those cases.
   *
   * @return the implementation of this builder factory provided by the Mule Runtime.
   */
  static final AbstractComponentParameterizationBuilderFactory getDefaultFactory() {
    return DEFAULT_FACTORY;
  }

  /**
   * @return a fresh {@link ComponentParameterization.Builder} object.
   */
  protected abstract <M extends ParameterizedModel> ComponentParameterization.Builder<M> create(M model);

}
