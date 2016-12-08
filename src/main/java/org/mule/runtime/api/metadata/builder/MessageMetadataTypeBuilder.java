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
import org.mule.runtime.api.metadata.DefaultMessageMetadataType;
import org.mule.runtime.api.metadata.MessageMetadataType;

/**
 * Builder for The {@link MessageMetadataType}
 */
public class MessageMetadataTypeBuilder extends AbstractBuilder<MessageMetadataType>
    implements WithAnnotation<MessageMetadataTypeBuilder> {

  private TypeBuilder<? extends MetadataType> payloadType;
  private TypeBuilder<? extends MetadataType> attributesType;

  public MessageMetadataTypeBuilder() {
    super(MetadataFormat.JAVA);
    this.payloadType = BaseTypeBuilder.create(MetadataFormat.JAVA).anyType();
    this.attributesType = BaseTypeBuilder.create(MetadataFormat.JAVA).anyType();
  }


  public BaseTypeBuilder withPayload() {
    BaseTypeBuilder baseTypeBuilder = BaseTypeBuilder.create(MetadataFormat.JAVA);
    this.payloadType = baseTypeBuilder;
    return baseTypeBuilder;
  }

  public MessageMetadataTypeBuilder withPayload(MetadataType payloadType) {
    this.payloadType = new BasicTypeBuilder<>(payloadType);
    return this;
  }

  public BaseTypeBuilder withAttributes() {
    BaseTypeBuilder baseTypeBuilder = BaseTypeBuilder.create(MetadataFormat.JAVA);
    this.attributesType = baseTypeBuilder;
    return baseTypeBuilder;
  }

  public MessageMetadataTypeBuilder withAttributes(MetadataType payloadType) {
    this.attributesType = new BasicTypeBuilder<>(payloadType);
    return this;
  }

  @Override
  public MessageMetadataType build() {
    return new DefaultMessageMetadataType(payloadType.build(), attributesType.build(), annotations);
  }

  @Override
  public MessageMetadataTypeBuilder with(TypeAnnotation typeAnnotation) {
    this.addExtension(typeAnnotation);
    return this;
  }
}
