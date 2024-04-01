/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;
import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.message.api.MessageMetadataType;
import org.mule.runtime.api.component.location.Location;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.source.SourceModel;
import org.mule.runtime.api.metadata.descriptor.ComponentMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.InputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.OutputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.RouterInputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.ScopeInputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.TypeMetadataDescriptor;
import org.mule.runtime.api.metadata.resolving.InputTypeResolver;
import org.mule.runtime.api.metadata.resolving.MetadataResult;
import org.mule.runtime.api.metadata.resolving.OutputTypeResolver;
import org.mule.runtime.api.metadata.resolving.TypeKeysResolver;

/**
 * Provides access to the Metadata of any {@link MetadataProvider} and {@link EntityMetadataProvider} components in the
 * application and the {@link MetadataKey} of the {@link MetadataKeyProvider} components, using it's {@link Location} It also
 * provides access to the {@link MetadataKey} associated to a configuration through the operations and sources that belong to that
 * configuration.
 *
 * @since 1.0
 */
@NoImplement
public interface MetadataService {

  /**
   * Key under which the {@link MetadataService} can be found in the {@link org.mule.runtime.api.artifact.Registry}
   */
  String METADATA_SERVICE_KEY = "_muleMetadataService";

  /**
   * Key under which the non lazy {@link MetadataService} can be found in the {@link org.mule.runtime.api.artifact.Registry}
   */
  String NON_LAZY_METADATA_SERVICE_KEY = "_muleNonLazyMetadataService";

  /**
   * Returns the list of types that can be described by the {@link TypeKeysResolver} associated to the {@link MetadataKeyProvider}
   * Component identified by the {@link Location}.
   *
   * @param location the location of the {@link MetadataKeyProvider} component to query for its available keys
   * @return Successful {@link MetadataResult} if the keys are successfully resolved Failure {@link MetadataResult} if there is an
   *         error while resolving the keys
   */
  MetadataResult<MetadataKeysContainer> getMetadataKeys(Location location);

  /**
   * Returns the a {@link MetadataKeysContainer} with the {@link MetadataKey}s provided per {@link TypeKeysResolver} associated to
   * this Component.
   *
   * @param partialKey {@link MetadataKey} to be considered for resolve the next key levels.
   * @return Successful {@link MetadataResult} if the keys are successfully resolved Failure {@link MetadataResult} if there is an
   *         error while retrieving the keys
   * @since 1.4
   */
  MetadataResult<MetadataKeysContainer> getMetadataKeys(Location location, MetadataKey partialKey);

  /**
   * Resolves the {@link MetadataType} for the input parameters of the component at the given {@code location}
   *
   * @param location the {@link Location} of the component which input types are being resolved
   * @param key      {@link MetadataKey} for resolving dynamic types
   * @return A {@link MetadataType} of {@link InputMetadataDescriptor}. Successful {@link MetadataResult} if the Metadata is
   *         successfully retrieved Failure {@link MetadataResult} when the Metadata retrieval fails for any reason
   * @since 1.4
   */
  MetadataResult<InputMetadataDescriptor> getInputMetadata(Location location, MetadataKey key);

  MetadataResult<ScopeInputMetadataDescriptor> getScopeInputMetadata(Location location, MetadataKey key,
                                                                     MessageMetadataType scopeInputMessageType);

  MetadataResult<RouterInputMetadataDescriptor> getRouterInputMetadata(Location location, MetadataKey key,
                                                                       MessageMetadataType routerInputMessageType);

  /**
   * Resolves the dynamic {@link MetadataType} for the current component output with the given key.
   *
   * @param key {@link MetadataKey} of the type which's structure has to be resolved.
   * @return A {@link MetadataType} of {@link OutputMetadataDescriptor}. Successful {@link MetadataResult} if the Metadata is
   *         successfully retrieved Failure {@link MetadataResult} when the Metadata retrieval fails for any reason
   * @since 1.4
   */
  MetadataResult<OutputMetadataDescriptor> getOutputMetadata(Location location, MetadataKey key);

  /**
   * Resolves an entity {@link OutputMetadataDescriptor} for the {@link EntityMetadataProvider} component identified by the
   * {@link Location}. This is to be used only on Scope Operations.
   *
   * @param location the location of the {@link EntityMetadataProvider} component to query for its available keys
   * @param key      {@link MetadataKey} representing an entity of the type which's structure has to be resolved
   * @param ctx      the context for the propagation within a scope.
   * @return a {@link MetadataResult} of {@link OutputMetadataDescriptor} type with a successful {@link MetadataResult} if the
   *         metadata is successfully retrieved and a failed {@link MetadataResult} when the metadata retrieval of the entity had
   *         a problem.
   */
  MetadataResult<OutputMetadataDescriptor> getScopeOutputMetadata(Location location,
                                                                  MetadataKey key,
                                                                  ScopeOutputMetadataContext ctx);

  /**
   * Resolves an entity {@link OutputMetadataDescriptor} for the {@link EntityMetadataProvider} component identified by the
   * {@link Location}. This is to be used only on Router Operations.
   *
   * @param location the location of the {@link EntityMetadataProvider} component to query for its available keys
   * @param key      {@link MetadataKey} representing an entity of the type which's structure has to be resolved
   * @param ctx      the context for the propagation within a router.
   * @return a {@link MetadataResult} of {@link OutputMetadataDescriptor} type with a successful {@link MetadataResult} if the
   *         metadata is successfully retrieved and a failed {@link MetadataResult} when the metadata retrieval of the entity had
   *         a problem.
   */
  MetadataResult<OutputMetadataDescriptor> getRouterOutputMetadata(Location location, MetadataKey key,
                                                                   RouterOutputMetadataContext ctx);


  /**
   * Resolves the {@link ComponentMetadataDescriptor} for the {@link MetadataProvider} Component identified by the
   * {@link Location} using only the static types of its parameters, attributes and output.
   *
   * @param location the location of the {@link MetadataProvider} component to type dynamically
   * @return An {@link ComponentMetadataDescriptor} with the static Metadata representation of the Component. Successful
   *         {@link MetadataResult} if the Metadata is successfully retrieved Failure {@link MetadataResult} when the Metadata
   *         retrieval of any element fails for any reason
   */
  MetadataResult<ComponentMetadataDescriptor<OperationModel>> getOperationMetadata(Location location);

  /**
   * Resolves the {@link ComponentMetadataDescriptor} for the {@link MetadataProvider} Component identified by the
   * {@link Location} using both static and dynamic resolving of the parameters, attributes and output types.
   * <p>
   * If the component has a {@link InputTypeResolver} or {@link OutputTypeResolver} associated that can be used to resolve the
   * dynamic {@link MetadataType} for the Content or Output, then the {@link ComponentMetadataDescriptor} will contain those
   * dynamic types instead of the static types declaration.
   * <p>
   * When neither Content nor Output have dynamic types, then invoking this method is the same as invoking
   * {@link this#getOperationMetadata}
   *
   * @param location the location of the {@link MetadataProvider} component to type dynamically
   * @param key      {@link MetadataKey} of the type which's structure has to be resolved, used both for input and output types
   * @return a {@link MetadataResult} of {@link ComponentMetadataDescriptor} type with Successful {@link MetadataResult} if the
   *         Metadata is successfully retrieved and a Failed {@link MetadataResult} when the Metadata retrieval of any element
   *         fails for any reason
   */
  MetadataResult<ComponentMetadataDescriptor<OperationModel>> getOperationMetadata(Location location, MetadataKey key);

  /**
   * Resolves the {@link ComponentMetadataDescriptor} for the {@link MetadataProvider} Component identified by the
   * {@link Location} using only the static types of its parameters, attributes and output.
   *
   * @param location the location of the {@link MetadataProvider} component to type dynamically
   * @return An {@link ComponentMetadataDescriptor} with the static Metadata representation of the Component. Successful
   *         {@link MetadataResult} if the Metadata is successfully retrieved Failure {@link MetadataResult} when the Metadata
   *         retrieval of any element fails for any reason
   */
  MetadataResult<ComponentMetadataDescriptor<SourceModel>> getSourceMetadata(Location location);

  /**
   * Resolves the {@link ComponentMetadataDescriptor} for the {@link MetadataProvider} Component identified by the
   * {@link Location} using both static and dynamic resolving of the parameters, attributes and output types.
   * <p>
   * If the component has a {@link InputTypeResolver} or {@link OutputTypeResolver} associated that can be used to resolve the
   * dynamic {@link MetadataType} for the Content or Output, then the {@link ComponentMetadataDescriptor} will contain those
   * dynamic types instead of the static types declaration.
   * <p>
   * When neither Content nor Output have dynamic types, then invoking this method is the same as invoking
   * {@link this#getOperationMetadata}
   *
   * @param location the location of the {@link MetadataProvider} component to type dynamically
   * @param key      {@link MetadataKey} of the type which's structure has to be resolved, used both for input and output types
   * @return a {@link MetadataResult} of {@link ComponentMetadataDescriptor} type with Successful {@link MetadataResult} if the
   *         Metadata is successfully retrieved and a Failed {@link MetadataResult} when the Metadata retrieval of any element
   *         fails for any reason
   */
  MetadataResult<ComponentMetadataDescriptor<SourceModel>> getSourceMetadata(Location location, MetadataKey key);


  /**
   * Removes the {@link MetadataCache} with the specified id.
   *
   * @param id the id of the cache to be removed
   */
  void disposeCache(String id);

  /**
   * Returns the list of entity types that can be queried in an {@link EntityMetadataProvider} component identified by the
   * {@link Location}.
   *
   * @param location the location of the {@link EntityMetadataProvider} component to query for its available keys
   * @return Successful {@link MetadataResult} if the keys are successfully resolved Failure {@link MetadataResult} if there is an
   *         error while resolving the entity keys
   */
  MetadataResult<MetadataKeysContainer> getEntityKeys(Location location);

  /**
   * Resolves an entity {@link TypeMetadataDescriptor} for the {@link EntityMetadataProvider} component identified by the
   * {@link Location}.
   *
   * @param location the location of the {@link EntityMetadataProvider} component to query for its available keys
   * @param key      {@link MetadataKey} representing an entity of the type which's structure has to be resolved
   * @return a {@link MetadataResult} of {@link TypeMetadataDescriptor} type with a successful {@link MetadataResult} if the
   *         metadata is successfully retrieved and a failed {@link MetadataResult} when the metadata retrieval of the entity had
   *         a problem.
   */
  MetadataResult<TypeMetadataDescriptor> getEntityMetadata(Location location, MetadataKey key);
}
