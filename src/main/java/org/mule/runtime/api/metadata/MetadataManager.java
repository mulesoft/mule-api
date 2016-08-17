/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.descriptor.ComponentMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.TypeMetadataDescriptor;
import org.mule.runtime.api.metadata.resolving.MetadataContentResolver;
import org.mule.runtime.api.metadata.resolving.MetadataKeysResolver;
import org.mule.runtime.api.metadata.resolving.MetadataOutputResolver;
import org.mule.runtime.api.metadata.resolving.MetadataResult;

/**
 * Provides access to the Metadata of any {@link MetadataProvider} and {@link EntityMetadataProvider} components in
 * the application and the {@link MetadataKey} of the {@link MetadataKeyProvider} components, using it's {@link ComponentId}
 * It also provides access to the {@link MetadataKey} associated to a configuration through the operations
 * and sources that belong to that configuration.
 *
 * @since 1.0
 */
public interface MetadataManager {

  /**
   * Returns the list of types that can be described by the {@link MetadataKeysResolver} associated to the
   * {@link MetadataKeyProvider} Component identified by the {@link ComponentId}.
   *
   * @param componentId the id of the {@link MetadataKeyProvider} component to query for its available keys
   * @return Successful {@link MetadataResult} if the keys are successfully resolved Failure {@link MetadataResult} if there is an
   * error while resolving the keys
   */
  MetadataResult<MetadataKeysContainer> getMetadataKeys(ComponentId componentId);

  /**
   * Resolves the {@link ComponentMetadataDescriptor} for the {@link MetadataProvider} Component identified by the
   * {@link ComponentId} using only the static types of its parameters, attributes and output.
   *
   * @param componentId the id of the {@link MetadataProvider} component to query for its available keys
   * @return An {@link ComponentMetadataDescriptor} with the static Metadata representation of the Component. Successful
   * {@link MetadataResult} if the Metadata is successfully retrieved Failure {@link MetadataResult} when the Metadata
   * retrieval of any element fails for any reason
   */
  MetadataResult<ComponentMetadataDescriptor> getMetadata(ComponentId componentId);

  /**
   * Resolves the {@link ComponentMetadataDescriptor} for the {@link MetadataProvider} Component identified by the
   * {@link ComponentId} using both static and dynamic resolving of the parameters, attributes and output types.
   * <p>
   * If the component has a {@link MetadataContentResolver} or {@link MetadataOutputResolver} associated that can be used to
   * resolve the dynamic {@link MetadataType} for the Content or Output, then the {@link ComponentMetadataDescriptor} will contain
   * those dynamic types instead of the static types declaration.
   * <p>
   * When neither Content nor Output have dynamic types, then invoking this method is the same as invoking
   * {@link this#getMetadata}
   *
   * @param componentId the id of the {@link MetadataProvider} component to query for its available keys
   * @param key         {@link MetadataKey} of the type which's structure has to be resolved, used both for input and output types
   * @return a {@link MetadataResult} of {@link ComponentMetadataDescriptor} type with Successful {@link MetadataResult} if the
   * Metadata is successfully retrieved and a Failed {@link MetadataResult} when the Metadata retrieval of any element
   * fails for any reason
   */
  MetadataResult<ComponentMetadataDescriptor> getMetadata(ComponentId componentId, MetadataKey key);

  /**
   * Removes the {@link MetadataCache} with the specified id.
   *
   * @param id the id of the cache to be removed
   */
  void disposeCache(String id);

  /**
   * Returns the list of entity types that can be queried in an {@link EntityMetadataProvider} component
   * identified by the {@link ComponentId}.
   *
   * @param componentId the id of the {@link EntityMetadataProvider} component to query for its available keys
   * @return Successful {@link MetadataResult} if the keys are successfully resolved Failure {@link MetadataResult} if there is an
   * error while resolving the entity keys
   */
  MetadataResult<MetadataKeysContainer> getEntityKeys(ComponentId componentId);

  /**
   * Resolves an entity {@link TypeMetadataDescriptor} for the {@link EntityMetadataProvider} component identified by the
   * {@link ComponentId}.
   *
   * @param componentId the id of the {@link EntityMetadataProvider} component to query for its available keys
   * @param key         {@link MetadataKey}  representing an entity of the type which's structure has to be resolved
   * @return a {@link MetadataResult} of {@link TypeMetadataDescriptor} type with a successful {@link MetadataResult} if the
   * metadata is successfully retrieved and a failed {@link MetadataResult} when the metadata retrieval of the entity
   * had a problem.
   */
  MetadataResult<TypeMetadataDescriptor> getEntityMetadata(ComponentId componentId, MetadataKey key);
}
