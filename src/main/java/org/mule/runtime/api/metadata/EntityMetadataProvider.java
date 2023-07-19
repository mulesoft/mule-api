/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.descriptor.TypeMetadataDescriptor;
import org.mule.runtime.api.metadata.resolving.MetadataResult;

/**
 * This interface allows a Component that processes a {@link Message} to expose its metadata descriptor, containing all the
 * {@link MetadataType} information associated to the Component's input and output elements
 *
 * @since 1.0
 */
@NoImplement
public interface EntityMetadataProvider {

  MetadataResult<MetadataKeysContainer> getEntityKeys() throws MetadataResolvingException;

  MetadataResult<TypeMetadataDescriptor> getEntityMetadata(MetadataKey key) throws MetadataResolvingException;
}

