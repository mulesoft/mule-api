/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.lang.String.format;
import static java.util.ServiceLoader.load;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class used to create {@link DataTypeBuilder} objects.
 *
 * @since 1.0
 */
public abstract class DataTypeBuilderFactory
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataTypeBuilderFactory.class);

    private static Supplier<DataTypeBuilderFactory> INSTANCE = () ->
    {
        try
        {
            final DataTypeBuilderFactory factory = load(DataTypeBuilderFactory.class).iterator().next();
            LOGGER.info(format("Loaded DataTypeBuilderFactory impementation '%s' form classloader '%s'",
                    factory.getClass().getName(), factory.getClass().getClassLoader().toString()));

            INSTANCE = () -> factory;
            return factory;
        }
        catch (Exception e)
        {
            LOGGER.error("Error loading DataTypeBuilderFactory implementation.", e);
            throw e;
        }
    };

    static DataTypeBuilderFactory getInstance()
    {
        return INSTANCE.get();
    }

    /**
     * @return a fresh {@link DataTypeBuilder} object.
     */
    protected abstract <T> DataTypeBuilder<T> builder();
}
