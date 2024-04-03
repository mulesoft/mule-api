/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.Experimental;
import org.mule.api.annotation.NoImplement;
import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.message.api.MessageMetadataType;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.metadata.descriptor.ComponentMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.InputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.OutputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.RouterInputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.ScopeInputMetadataDescriptor;
import org.mule.runtime.api.metadata.resolving.InputTypeResolver;
import org.mule.runtime.api.metadata.resolving.MetadataResult;
import org.mule.runtime.api.metadata.resolving.OutputTypeResolver;

import java.util.function.Supplier;

/**
 * This interface allows a Component that processes a {@link Message} to expose its metadata descriptor, containing all the
 * {@link MetadataType} information associated to the Component's input and output elements
 *
 * @since 1.0
 */
@NoImplement
public interface MetadataProvider<T extends ComponentModel> {

  /**
   * Resolves the {@link ComponentMetadataDescriptor} for the current component using only the static types of the Component's
   * parameters, attributes and output.
   *
   * @return An {@link ComponentMetadataDescriptor} with the Static Metadata representation of the Component. Successful
   *         {@link MetadataResult} if the Metadata is successfully retrieved Failure {@link MetadataResult} when the Metadata
   *         retrieval of any element fails for any reason
   * @throws MetadataResolvingException if an error occurs while creating the {@link MetadataContext}
   */
  MetadataResult<ComponentMetadataDescriptor<T>> getMetadata() throws MetadataResolvingException;

  /**
   * Resolves the {@link ComponentMetadataDescriptor} for the current component using both static and dynamic resolving of the
   * Component's parameters, attributes and output.
   * <p>
   * If the component has a {@link InputTypeResolver} or {@link OutputTypeResolver} associated that can be used to resolve the
   * Dynamic {@link MetadataType} for the Content or Output, then the {@link ComponentMetadataDescriptor} will contain those
   * Dynamic types instead of the static type declaration.
   * <p>
   * When neither Content nor Output have Dynamic types, then invoking this method is the same as invoking
   * {@link MetadataProvider#getMetadata}
   *
   * @param key {@link MetadataKey} of the type which's structure has to be resolved, used both for input and output types
   * @return a {@link MetadataResult} of {@link ComponentMetadataDescriptor} type with Successful {@link MetadataResult} if the
   *         Metadata is successfully retrieved and a Failed {@link MetadataResult} when the Metadata retrieval of any element
   *         fails for any reason
   * @throws MetadataResolvingException if an error occurs while creating the {@link MetadataContext}
   */
  MetadataResult<ComponentMetadataDescriptor<T>> getMetadata(MetadataKey key) throws MetadataResolvingException;

  /**
   * Resolves the dynamic {@link MetadataType} for the current component parameters with the given key.
   *
   * @param key {@link MetadataKey} of the type which's structure has to be resolved.
   * @return A {@link MetadataType} of {@link InputMetadataDescriptor}. Successful {@link MetadataResult} if the Metadata is
   *         successfully retrieved Failure {@link MetadataResult} when the Metadata retrieval fails for any reason
   * @throws MetadataResolvingException if an error occurs while creating the {@link MetadataContext}
   * @since 1.4
   */
  MetadataResult<InputMetadataDescriptor> getInputMetadata(MetadataKey key) throws MetadataResolvingException;

  /**
   * Resolves the input metadata for the scope at the given {@code location}. It not only resolves input parameter types,
   * but also the payload and attribute types of the message that will enter the inner chain.
   * <p>
   * Tip: Don't confuse the scope's input message (the one that enters the scope operation itself) with the inner chain
   * input message (the one that enters the scope's chain).
   *
   * <b>NOTE:</b> Experimental feature. Backwards compatibility is not guaranteed.
   *
   * @param key                   {@link MetadataKey} for resolving dynamic types
   * @param scopeInputMessageType a typed {@link MessageMetadataType} supplier describing the message that originally entered the scope
   * @return a {@link MetadataResult} wrapping a {@link ScopeInputMetadataDescriptor}
   * @since 1.7.0
   */
  @Experimental
  MetadataResult<ScopeInputMetadataDescriptor> getScopeInputMetadata(MetadataKey key,
                                                                     Supplier<MessageMetadataType> scopeInputMessageType)
      throws MetadataResolvingException;

  /**
   * Resolves the input metadata for the router at the given {@code location}. It not only resolves input parameter types,
   * but also the payload and attribute types of the message that will enter each route.
   * <p>
   * Tip: Don't confuse the scope's input message (the one that enters the scope operation itself) with the routes
   * input message (the one that enters the scope's chain).
   *
   * <b>NOTE:</b> Experimental feature. Backwards compatibility is not guaranteed.
   *
   * @param key                    {@link MetadataKey} for resolving dynamic types
   * @param routerInputMessageType a typed {@link MessageMetadataType} supplier describing the message that originally entered the router
   * @return a {@link MetadataResult} wrapping a {@link ScopeInputMetadataDescriptor}
   * @since 1.7.0
   */
  @Experimental
  MetadataResult<RouterInputMetadataDescriptor> getRouterInputMetadata(MetadataKey key,
                                                                       Supplier<MessageMetadataType> routerInputMessageType)
      throws MetadataResolvingException;

  /**
   * Resolves the dynamic {@link MetadataType} for the current component output and attrubutes with the given key.
   *
   * @param key {@link MetadataKey} of the type which's structure has to be resolved.
   * @return A {@link MetadataType} of {@link OutputMetadataDescriptor}. Successful {@link MetadataResult} if the Metadata is
   *         successfully retrieved Failure {@link MetadataResult} when the Metadata retrieval fails for any reason
   * @throws MetadataResolvingException if an error occurs while creating the {@link MetadataContext}
   * @since 1.4
   */
  MetadataResult<OutputMetadataDescriptor> getOutputMetadata(MetadataKey key) throws MetadataResolvingException;

  /**
   * Resolves the dynamic {@link MetadataType} for the current scope component output and attributes with the given key.
   *
   * <b>NOTE:</b> Experimental feature. Backwards compatibility is not guaranteed.
   *
   * @param key                        {@link MetadataKey} of the type which's structure has to be resolved.
   * @param scopeOutputMetadataContext the context of the propagation of the inner chain of the scope.
   * @return A {@link MetadataType} of {@link OutputMetadataDescriptor}. Successful {@link MetadataResult} if the Metadata is
   * successfully retrieved Failure {@link MetadataResult} when the Metadata retrieval fails for any reason
   * @throws MetadataResolvingException if an error occurs while creating the {@link MetadataContext}
   * @since 1.7
   */
  @Experimental
  MetadataResult<OutputMetadataDescriptor> getScopeOutputMetadata(MetadataKey key,
                                                                  ScopeOutputMetadataContext scopeOutputMetadataContext)
      throws MetadataResolvingException;

  /**
   * Resolves the dynamic {@link MetadataType} for the current scope component output and attributes with the given key.
   *
   * <b>NOTE:</b> Experimental feature. Backwards compatibility is not guaranteed.
   *
   * @param key                         {@link MetadataKey} of the type which's structure has to be resolved.
   * @param routerOutputMetadataContext the context of the propagation of the router's routes' inner chains.
   * @return A {@link MetadataType} of {@link OutputMetadataDescriptor}. Successful {@link MetadataResult} if the Metadata is
   * successfully retrieved Failure {@link MetadataResult} when the Metadata retrieval fails for any reason
   * @throws MetadataResolvingException if an error occurs while creating the {@link MetadataContext}
   * @since 1.7
   */
  @Experimental
  MetadataResult<OutputMetadataDescriptor> getRouterOutputMetadata(MetadataKey key,
                                                                   RouterOutputMetadataContext routerOutputMetadataContext)
      throws MetadataResolvingException;

}

