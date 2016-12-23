/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.tck;

import static org.mule.metadata.api.model.MetadataFormat.JAVA;
import org.mule.metadata.api.annotation.TypeIdAnnotation;
import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.WithAnnotation;
import org.mule.metadata.api.model.BinaryType;
import org.mule.metadata.api.model.BooleanType;
import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.api.model.NumberType;
import org.mule.metadata.api.model.ObjectType;
import org.mule.metadata.api.model.StringType;
import org.mule.runtime.api.message.Attributes;

import java.io.InputStream;

public abstract class BaseDeclarerTestCase {

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

  protected MetadataType getAttributesType() {
    return withType(typeBuilder.objectType(), Attributes.class).build();
  }

  protected BooleanType getBooleanType() {
    return withType(typeBuilder.booleanType(), Boolean.class).build();
  }

  protected ObjectType getObjectType(Class<?> type) {
    return withType(typeBuilder.objectType(), type).build();
  }

  private <T extends WithAnnotation<?>> T withType(T builder, Class<?> type) {
    return (T) builder.with(new TypeIdAnnotation(type.getName()));
  }
}
