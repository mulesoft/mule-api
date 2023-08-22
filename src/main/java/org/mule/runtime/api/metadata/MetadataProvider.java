/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.metadata.descriptor.ComponentMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.InputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.OutputMetadataDescriptor;
import org.mule.runtime.api.metadata.resolving.InputTypeResolver;
import org.mule.runtime.api.metadata.resolving.MetadataResult;
import org.mule.runtime.api.metadata.resolving.OutputTypeResolver;

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
   * Resolves the dynamic {@link MetadataType} for the current component output and attrubutes with the given key.
   *
   * @param key {@link MetadataKey} of the type which's structure has to be resolved.
   * @return A {@link MetadataType} of {@link OutputMetadataDescriptor}. Successful {@link MetadataResult} if the Metadata is
   *         successfully retrieved Failure {@link MetadataResult} when the Metadata retrieval fails for any reason
   * @throws MetadataResolvingException if an error occurs while creating the {@link MetadataContext}
   * @since 1.4
   */
  MetadataResult<OutputMetadataDescriptor> getOutputMetadata(MetadataKey key) throws MetadataResolvingException;

}

