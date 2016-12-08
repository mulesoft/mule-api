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
 * Default implementation for {@link MuleEventMetadataType}.
 *
 * @since 1.0
 */
public class DefaultMuleEventMetadataType extends BaseMetadataType implements ObjectType, MuleEventMetadataType {

  private static final String MESSAGE_FIELD_NAME = "message";
  private static final String VARIABLES_FIELD_NAME = "variables";
  private final ObjectFieldType messageMetadataType;
  private final ObjectFieldType variables;


  public DefaultMuleEventMetadataType(MessageMetadataType messageMetadataType, ObjectType variables,
                                      Map<Class<? extends TypeAnnotation>, TypeAnnotation> annotations) {
    super(MetadataFormat.JAVA, annotations);
    final ObjectFieldTypeBuilder messageTypeBuilder = new ObjectFieldTypeBuilder(MetadataFormat.JAVA);
    messageTypeBuilder.key(MESSAGE_FIELD_NAME).value(messageMetadataType);
    this.messageMetadataType = messageTypeBuilder.build();
    final ObjectFieldTypeBuilder variablesTypeBuilder = new ObjectFieldTypeBuilder(MetadataFormat.JAVA);
    variablesTypeBuilder.key(VARIABLES_FIELD_NAME).value(variables);
    this.variables = variablesTypeBuilder.build();
  }


  @Override
  public MessageMetadataType getMessageType() {
    return (MessageMetadataType) messageMetadataType.getValue();
  }

  @Override
  public ObjectType getVariables() {
    return (ObjectType) variables.getValue();
  }

  @Override
  public Optional<MetadataType> getVariableType(String varName) {
    return getVariables().getFieldByName(varName).map(ObjectFieldType::getValue);
  }

  @Override
  public Collection<ObjectFieldType> getFields() {
    return Arrays.asList(messageMetadataType, variables);
  }

  @Override
  public boolean isOrdered() {
    return false;
  }

  @Override
  public Optional<ObjectFieldType> getFieldByName(String name) {
    switch (name) {
      case MESSAGE_FIELD_NAME:
        return Optional.ofNullable(messageMetadataType);
      case VARIABLES_FIELD_NAME:
        return Optional.ofNullable(variables);
    }
    return Optional.empty();
  }

  @Override
  public void accept(MetadataTypeVisitor metadataTypeVisitor) {
    metadataTypeVisitor.visitObject(this);
  }
}
