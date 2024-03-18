/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static java.util.ServiceLoader.load;

import org.mule.api.annotation.NoExtend;
import org.mule.runtime.api.exception.MuleRuntimeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class used to create {@link DataTypeBuilder} objects.
 *
 * @since 1.0
 */
@NoExtend
public abstract class AbstractDataTypeBuilderFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDataTypeBuilderFactory.class);

  private static AbstractDataTypeBuilderFactory loadFactory(ClassLoader classLoader) {
    if (DEFAULT_FACTORY == null) {
      try {
        final AbstractDataTypeBuilderFactory factory =
            load(AbstractDataTypeBuilderFactory.class, classLoader).iterator().next();
        LOGGER.info(format("Loaded MuleMessageBuilderFactory implementation '%s' from classloader '%s'",
                           factory.getClass().getName(), factory.getClass().getClassLoader().toString()));

        DEFAULT_FACTORY = factory;
      } catch (Throwable t) {
        LOGGER.error("Error loading MuleMessageBuilderFactory implementation.", t);
        throw t;
      }
    }

    return DEFAULT_FACTORY;
  }

  private static AbstractDataTypeBuilderFactory DEFAULT_FACTORY;

  /**
   * The implementation of this abstract class is provided by the Mule Runtime.
   * <p>
   * If more than one implementation is found, the classLoading order of those implementations will determine which one is used.
   * Information about this will be logged to aid in the troubleshooting of those cases.
   * 
   * @return the implementation of this builder factory provided by the Mule Runtime.
   */
  static final AbstractDataTypeBuilderFactory getDefaultFactory() {
    try {
      return getDefaultFactory(currentThread().getContextClassLoader());
    } catch (Throwable t) {
      ClassLoader classLoader;
      try {
        classLoader = currentThread().getContextClassLoader().loadClass("org.mule.runtime.core.api.MuleContext").getClassLoader();
      } catch (ClassNotFoundException e) {
        throw new MuleRuntimeException(createStaticMessage(format("Failed obtaining class loader to load %s",
                                                                  AbstractDataTypeBuilderFactory.class.getName())),
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
  static AbstractDataTypeBuilderFactory getDefaultFactory(ClassLoader classLoader) {
    return loadFactory(classLoader);
  }

  /**
   * @return a fresh {@link DataTypeBuilder} object.
   */
  protected abstract DataTypeBuilder create();

  /**
   * @param dataType existing {@link DataType} to use as a template to create a new {@link DataTypeBuilder} instance.
   * @return a fresh {@link DataTypeBuilder} based on the template {@code dataType} provided.
   */
  protected abstract DataTypeBuilder create(DataType dataType);

}
