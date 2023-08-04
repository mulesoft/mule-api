/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.nested.NestableElementModel;

/**
 * A declaration object for a {@link NestableElementModel}. It contains raw, unvalidated data which is used to declare the
 * structure of a {@link NestableElementModel}
 *
 * @since 1.0
 */
public class NestableElementDeclaration<T extends NestableElementDeclaration> extends ComponentDeclaration<T> {

  private boolean isRequired = false;
  private int minOccurs = 0;
  private Integer maxOccurs;

  /**
   * Creates a new instance
   *
   * @param name the name of the component being declared
   */
  public NestableElementDeclaration(String name) {
    super(name);
  }

  public int getMinOccurs() {
    return minOccurs;
  }

  public void setMinOccurs(int minOccurs) {
    this.minOccurs = minOccurs;
    isRequired = minOccurs > 0;
  }

  public boolean isRequired() {
    return isRequired;
  }

  public void setRequired(boolean required) {
    isRequired = required;
    if (!required) {
      minOccurs = 0;
    }
  }

  public Integer getMaxOccurs() {
    return maxOccurs;
  }

  public void setMaxOccurs(Integer maxOccurs) {
    this.maxOccurs = maxOccurs;
  }
}
