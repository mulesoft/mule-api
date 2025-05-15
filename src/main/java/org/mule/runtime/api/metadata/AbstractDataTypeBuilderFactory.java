/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static org.mule.runtime.api.util.classloader.MuleImplementationLoaderUtils.getMuleImplementationsLoader;
import static org.mule.runtime.api.util.classloader.MuleImplementationLoaderUtils.isResolveMuleImplementationLoadersDynamically;

import static java.lang.String.format;
import static java.util.ServiceLoader.load;

import static com.github.benmanes.caffeine.cache.Caffeine.newBuilder;

import org.mule.api.annotation.NoExtend;

import com.github.benmanes.caffeine.cache.LoadingCache;

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

  private static final LoadingCache<ClassLoader, AbstractDataTypeBuilderFactory> DATA_TYPE_BUILDER_FACTORIES_CACHE =
      newBuilder()
          .weakKeys()
          .build(classLoader -> {
            final AbstractDataTypeBuilderFactory factory =
                load(AbstractDataTypeBuilderFactory.class, getMuleImplementationsLoader()).iterator().next();
            LOGGER.info(format("Loaded AbstractDataTypeBuilderFactory implementation '%s' from classloader '%s'",
                               factory.getClass().getName(), factory.getClass().getClassLoader().toString()));

            return factory;
          });

  private static AbstractDataTypeBuilderFactory loadFactory() {
    try {
      return DATA_TYPE_BUILDER_FACTORIES_CACHE.get(getMuleImplementationsLoader());
    } catch (Throwable t) {
      LOGGER.error("Error loading AbstractDataTypeBuilderFactory implementation.", t);
      throw t;
    }
  }

  private static final AbstractDataTypeBuilderFactory DEFAULT_FACTORY = loadFactory();

  /**
   * The implementation of this abstract class is provided by the Mule Runtime.
   * <p>
   * If more than one implementation is found, the classLoading order of those implementations will determine which one is used.
   * Information about this will be logged to aid in the troubleshooting of those cases.
   *
   * @return the implementation of this builder factory provided by the Mule Runtime.
   */
  static final AbstractDataTypeBuilderFactory getDefaultFactory() {
    if (isResolveMuleImplementationLoadersDynamically()) {
      return loadFactory();
    } else {
      return DEFAULT_FACTORY;
    }
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
