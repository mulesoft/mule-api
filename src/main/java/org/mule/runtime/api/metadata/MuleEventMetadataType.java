/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.api.model.ObjectType;
import org.mule.runtime.api.metadata.builder.MuleEventMetadataTypeBuilder;

import java.util.Optional;

/**
 * Models a Mule Event MetadataType
 */
public interface MuleEventMetadataType extends ObjectType {

  /**
   * Creates a builder for the MuleEventMetadataType
   *
   * @return The builder
   */
  static MuleEventMetadataTypeBuilder builder() {
    return new MuleEventMetadataTypeBuilder();
  }

  /**
   * The message metadata type
   *
   * @return The message type
   */
  MessageMetadataType getMessageType();

  /**
   * An object with all the variables types.
   *
   * @return The types
   */
  ObjectType getVariables();

  /**
   * Returns the metadata type of a given variable
   *
   * @param varName The variable name
   * @return The metadata type if present.
   */
  Optional<MetadataType> getVariableType(String varName);

}
