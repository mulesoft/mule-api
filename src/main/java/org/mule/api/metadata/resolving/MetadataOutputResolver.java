/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.metadata.resolving;

import org.mule.api.connection.ConnectionException;
import org.mule.api.metadata.MetadataContext;
import org.mule.api.metadata.MetadataKey;
import org.mule.api.metadata.MetadataResolvingException;
import org.mule.metadata.api.model.MetadataType;

/**
 * Handles the dynamic {@link MetadataType} resolving for the output of an associated component.
 *
 * @since 1.0
 */
public interface MetadataOutputResolver
{

    /**
     * Given a {@link MetadataKey} of a type, resolves their {@link MetadataType}, which
     * represents the type structure.
     * This {@link MetadataType} will be considered as the output or result of the Operation
     *
     * @param context MetaDataContext of the MetaData resolution
     * @param key     {@link MetadataKey} of the type which's structure has to be resolved
     * @return {@link MetadataType} from the given {@param key}
     * @throws MetadataResolvingException if an error occurs during the {@link MetadataType} building. See
     *                                    {@link FailureCode} for possible {@link MetadataResolvingException} reasons
     * @throws ConnectionException        if an error occurs when using the connection provided  by the {@link MetadataContext}
     */
    MetadataType getOutputMetadata(MetadataContext context, MetadataKey key) throws MetadataResolvingException, ConnectionException;

}
