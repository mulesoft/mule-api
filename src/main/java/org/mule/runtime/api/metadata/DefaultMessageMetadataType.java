/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;


import org.mule.metadata.api.annotation.TypeAnnotation;
import org.mule.metadata.api.builder.ObjectFieldTypeBuilder;
import org.mule.metadata.api.model.MetadataFormat;
import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.api.model.ObjectFieldType;
import org.mule.metadata.api.model.ObjectType;
import org.mule.metadata.api.model.impl.BaseMetadataType;
import org.mule.metadata.api.visitor.MetadataTypeVisitor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Default implementation for {@link MessageMetadataType}.
 *
 * @since 1.0
 */
public class DefaultMessageMetadataType extends BaseMetadataType implements ObjectType, MessageMetadataType {

  private static final String PAYLOAD_FIELD_NAME = "payload";
  private static final String ATTRIBUTES_FIELD_NAME = "attributes";
  private final ObjectFieldType payloadMetadataType;
  private final ObjectFieldType attributes;


  public DefaultMessageMetadataType(MetadataType payloadMetadataType, MetadataType attributes,
                                    Map<Class<? extends TypeAnnotation>, TypeAnnotation> annotations) {
    super(MetadataFormat.JAVA, annotations);
    final ObjectFieldTypeBuilder messageTypeBuilder = new ObjectFieldTypeBuilder(MetadataFormat.JAVA);
    messageTypeBuilder.key(PAYLOAD_FIELD_NAME).value(payloadMetadataType);
    this.payloadMetadataType = messageTypeBuilder.build();
    final ObjectFieldTypeBuilder variablesTypeBuilder = new ObjectFieldTypeBuilder(MetadataFormat.JAVA);
    variablesTypeBuilder.key(ATTRIBUTES_FIELD_NAME).value(attributes);
    this.attributes = variablesTypeBuilder.build();
  }

  @Override
  public Collection<ObjectFieldType> getFields() {
    return Arrays.asList(payloadMetadataType, attributes);
  }

  @Override
  public boolean isOrdered() {
    return false;
  }

  @Override
  public Optional<ObjectFieldType> getFieldByName(String name) {
    switch (name) {
      case PAYLOAD_FIELD_NAME:
        return Optional.ofNullable(payloadMetadataType);
      case ATTRIBUTES_FIELD_NAME:
        return Optional.ofNullable(attributes);
    }
    return Optional.empty();
  }

  @Override
  public void accept(MetadataTypeVisitor metadataTypeVisitor) {
    metadataTypeVisitor.visitObject(this);
  }

  @Override
  public MetadataType getPayloadType() {
    return payloadMetadataType.getValue();
  }

  @Override
  public MetadataType getAttributesType() {
    return attributes.getValue();
  }
}
