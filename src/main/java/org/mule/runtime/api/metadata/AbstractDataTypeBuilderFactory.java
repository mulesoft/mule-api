/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.lang.String.format;
import static java.util.ServiceLoader.load;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class used to create {@link DataTypeBuilder} objects.
 *
 * @since 1.0
 */
public abstract class AbstractDataTypeBuilderFactory
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDataTypeBuilderFactory.class);

    static
    {
        try
        {
            final AbstractDataTypeBuilderFactory factory = load(AbstractDataTypeBuilderFactory.class).iterator().next();
            LOGGER.info(format("Loaded AbstractDataTypeBuilderFactory impementation '%s' form classloader '%s'",
                    factory.getClass().getName(), factory.getClass().getClassLoader().toString()));

            DEFAULT_FACTORY = factory;
        }
        catch (Exception e)
        {
            LOGGER.error("Error loading AbstractDataTypeBuilderFactory implementation.", e);
            throw e;
        }
    }
    
    private static final AbstractDataTypeBuilderFactory DEFAULT_FACTORY;

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
    static final AbstractDataTypeBuilderFactory getDefaultFactory()
    {
        return DEFAULT_FACTORY;
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
