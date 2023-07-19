/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.test.meta.model.tck;

import static org.mule.metadata.api.model.MetadataFormat.JAVA;

import org.mule.metadata.api.annotation.TypeIdAnnotation;
import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.WithAnnotation;
import org.mule.metadata.api.model.BinaryType;
import org.mule.metadata.api.model.BooleanType;
import org.mule.metadata.api.model.NumberType;
import org.mule.metadata.api.model.ObjectType;
import org.mule.metadata.api.model.StringType;
import org.mule.metadata.api.model.VoidType;

import java.io.InputStream;

public abstract class TestBaseDeclarer {

  protected final BaseTypeBuilder typeBuilder = BaseTypeBuilder.create(JAVA);

  protected StringType getStringType() {
    return withType(typeBuilder.stringType(), String.class).build();
  }

  protected NumberType getNumberType() {
    return withType(typeBuilder.numberType(), Integer.class).build();
  }

  protected BinaryType getBinaryType() {
    return withType(typeBuilder.binaryType(), InputStream.class).build();
  }

  protected BooleanType getBooleanType() {
    return withType(typeBuilder.booleanType(), Boolean.class).build();
  }

  protected ObjectType getObjectType(Class<?> type) {
    return withType(typeBuilder.objectType(), type).build();
  }

  protected VoidType getVoidType() {
    return typeBuilder.voidType().build();
  }

  protected <T extends WithAnnotation<?>> T withType(T builder, Class<?> type) {
    return (T) builder.with(new TypeIdAnnotation(type.getName()));
  }
}
