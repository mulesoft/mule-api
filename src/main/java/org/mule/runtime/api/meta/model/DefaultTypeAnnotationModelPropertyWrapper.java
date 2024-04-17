/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;


public class DefaultTypeAnnotationModelPropertyWrapper implements TypeAnnotationModelPropertyWrapper {

  private final ModelProperty modelProperty;

  public DefaultTypeAnnotationModelPropertyWrapper(ModelProperty modelProperty) {
    this.modelProperty = modelProperty;
  }

  public ModelProperty asModelProperty() {
    return modelProperty;
  }

  @Override
  public String getName() {
    return modelProperty.getName();
  }

  @Override
  public boolean isPublic() {
    return false;
  }
}
