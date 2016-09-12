/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.resolving.MetadataResult;

/**
 * Represents the view of all the Metadata associated to an Component's
 * {@link Message} output
 *
 * @since 1.0
 */
public interface OutputMetadataDescriptor {

  /**
   * @return a {@link MetadataResult} with the {@link TypeMetadataDescriptor} of the Component's
   * output {@link Message#getPayload}
   */
  MetadataResult<TypeMetadataDescriptor> getPayloadMetadata();

  /**
   * @return a {@link MetadataResult} with the {@link TypeMetadataDescriptor} of the Component's
   * output {@link Message#getAttributes}
   */
  MetadataResult<TypeMetadataDescriptor> getAttributesMetadata();

}
