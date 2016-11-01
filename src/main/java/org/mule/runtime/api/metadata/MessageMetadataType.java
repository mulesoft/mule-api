/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.api.model.ObjectType;


/**
 * Models a Message Type with its payload and attributes.
 *
 * @see org.mule.runtime.api.message.Message
 * @see MuleEventMetadataType
 * @see org.mule.runtime.api.metadata.builder.MessageMetadataTypeBuilder
 */
public interface MessageMetadataType extends ObjectType {

  /**
   * The payload metadata types
   *
   * @return The payload type
   */
  MetadataType getPayloadType();

  /**
   * The attributes metadata type
   *
   * @return The attribute type
   */
  MetadataType getAttributesType();
}
