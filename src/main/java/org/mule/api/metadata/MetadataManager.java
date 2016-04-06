/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.metadata;

import org.mule.api.metadata.descriptor.OperationMetadataDescriptor;
import org.mule.api.metadata.resolving.MetadataContentResolver;
import org.mule.api.metadata.resolving.MetadataKeysResolver;
import org.mule.api.metadata.resolving.MetadataOutputResolver;
import org.mule.api.metadata.resolving.MetadataResult;
import org.mule.metadata.api.model.MetadataType;

import java.util.List;

/**
 * Provides access to the Metadata of any {@link MetadataAware} Component in the application,
 * using it's {@link ComponentId}
 *
 * @since 1.0
 */
public interface MetadataManager
{

    /**
     * Returns the list of types that can be described, by the {@link MetadataKeysResolver}
     * associated to the {@link MetadataAware} Component identified by the {@link ComponentId}.
     *
     * @param componentId the id of the {@link MetadataAware} component to query for its available keys
     * @return Successful {@link MetadataResult} if the keys are successfully resolved
     * Failure {@link MetadataResult} if there is an error while resolving the keys
     */
    MetadataResult<List<MetadataKey>> getMetadataKeys(ComponentId componentId);

    /**
     * Resolves the {@link OperationMetadataDescriptor} for the {@link MetadataAware} Component
     * identified by the {@link ComponentId} using only the static types of its parameters, attributes and output.
     *
     * @param componentId the id of the {@link MetadataAware} component to query for its available keys
     * @return An {@link OperationMetadataDescriptor} with the static Metadata representation
     * of the Component.
     * Successful {@link MetadataResult} if the Metadata is successfully retrieved
     * Failure {@link MetadataResult} when the Metadata retrieval of any element fails for any reason
     * @throws MetadataResolvingException
     */
    MetadataResult<OperationMetadataDescriptor> getMetadata(ComponentId componentId);

    /**
     * Resolves the {@link OperationMetadataDescriptor} for the {@link MetadataAware} Component
     * identified by the {@link ComponentId} using both static and dynamic resolving of
     * the parameters, attributes and output types.
     * <p>
     * If the component has a {@link MetadataContentResolver} or {@link MetadataOutputResolver} associated
     * that can be used to resolve the dynamic {@link MetadataType} for the Content or Output,
     * then the {@link OperationMetadataDescriptor} will contain those dynamic types instead
     * of the static types declaration.
     * <p>
     * When neither Content nor Output have dynamic types, then invoking this method is the
     * same as invoking {@link this#getMetadata}
     *
     * @param componentId the id of the {@link MetadataAware} component to query for its available keys
     * @param key         {@link MetadataKey} of the type which's structure has to be resolved,
     *                    used both for input and ouput types
     *                    Successful {@link MetadataResult} if the Metadata is successfully retrieved
     *                    Failure {@link MetadataResult} when the Metadata retrieval of any element fails for any reason
     * @throws MetadataResolvingException
     */
    MetadataResult<OperationMetadataDescriptor> getMetadata(ComponentId componentId, MetadataKey key);

    /**
     * Removes the {@link MetadataCache} with the specified id.
     *
     * @param id the id of the cache to be removed
     */
    void disposeCache(String id);

}
