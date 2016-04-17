/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.resolving;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.metadata.api.model.MetadataType;

/**
 * Handles the dynamic {@link MetadataType} resolving for the Content parameter of an associated component.
 *
 * @since 1.0
 */
public interface MetadataContentResolver
{

    /**
     * Given a {@link MetadataKey} of a type, resolves their {@link MetadataType} which
     * represents the type structure.
     * This {@link MetadataType} will be considered as the main input of an Operation
     * for their parameter marked as Content.
     *
     * @param context MetaDataContext of the MetaData resolution
     * @param key     {@link MetadataKey} of the type which's structure has to be resolved
     * @return the {@link MetadataType} of the Content parameter
     * @throws MetadataResolvingException if an error occurs during the {@link MetadataType} building. See
     *                                    {@link FailureCode} for possible {@link MetadataResolvingException} reasons
     * @throws ConnectionException        if an error occurs when using the connection provided  by the {@link MetadataContext}
     */
    MetadataType getContentMetadata(MetadataContext context, MetadataKey key) throws MetadataResolvingException, ConnectionException;

}
