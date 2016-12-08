/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.builder;


import org.mule.metadata.api.annotation.TypeAnnotation;
import org.mule.metadata.api.builder.*;
import org.mule.metadata.api.model.MetadataFormat;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.DefaultMuleEventMetadataType;
import org.mule.runtime.api.metadata.MessageMetadataType;
import org.mule.runtime.api.metadata.MuleEventMetadataType;

/**
 * Builder for the {@link MuleEventMetadataType}
 */
public class MuleEventMetadataTypeBuilder extends AbstractBuilder<MuleEventMetadataType>
    implements WithAnnotation<MuleEventMetadataTypeBuilder> {


  private TypeBuilder<MessageMetadataType> messageType;
  private ObjectTypeBuilder variables;

  public MuleEventMetadataTypeBuilder() {
    super(MetadataFormat.JAVA);
    this.messageType = new MessageMetadataTypeBuilder();
    this.variables = new ObjectTypeBuilder(MetadataFormat.JAVA);
  }

  public MuleEventMetadataTypeBuilder withMessage(MessageMetadataType messageType) {
    this.messageType = new BasicTypeBuilder<>(messageType);
    return this;
  }

  public MessageMetadataTypeBuilder withMessage() {
    MessageMetadataTypeBuilder value = new MessageMetadataTypeBuilder();
    messageType = value;
    return value;
  }

  public BaseTypeBuilder addVariable(String name) {
    return variables.addField().key(name).value();
  }

  public MuleEventMetadataTypeBuilder addVariable(String name, MetadataType metadataType) {
    variables.addField().key(name).value(metadataType);
    return this;
  }

  @Override
  public MuleEventMetadataType build() {
    return new DefaultMuleEventMetadataType(messageType.build(), variables.build(), annotations);
  }

  public MuleEventMetadataTypeBuilder with(TypeAnnotation extension) {
    this.addExtension(extension);
    return this;
  }
}
