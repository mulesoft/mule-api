/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static org.mule.runtime.api.meta.model.ComponentVisibility.PUBLIC;

import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.meta.model.ComponentVisibility;
import org.mule.runtime.api.meta.model.error.ErrorModel;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A declaration object for a {@link ComponentModel}. It contains raw, unvalidated data which is used to declare the structure of
 * a {@link ComponentModel}
 *
 * @since 1.0
 */
public class ComponentDeclaration<T extends ComponentDeclaration> extends StereotypedDeclaration<T>
    implements WithNestedComponentsDeclaration<T>, WithStereotypesDeclaration {


  private List<NestableElementDeclaration> nestedComponents = new LinkedList<>();
  private Set<ErrorModel> errorModels = new LinkedHashSet<>();
  private ComponentVisibility visibility = PUBLIC;

  /**
   * {@inheritDoc}
   */
  ComponentDeclaration(String name) {
    super(name);
  }

  public List<NestableElementDeclaration> getNestedComponents() {
    return nestedComponents;
  }

  public T addNestedComponent(NestableElementDeclaration nestedComponentDeclaration) {
    nestedComponents.add(nestedComponentDeclaration);
    return (T) this;
  }

  public void addErrorModel(ErrorModel errorModel) {
    errorModels.add(errorModel);
  }

  public Set<ErrorModel> getErrorModels() {
    return errorModels;
  }

  /**
   * @return this {@link ComponentDeclaration}'s {@link ComponentVisibility}.
   */
  public ComponentVisibility getVisibility() {
    return visibility;
  }

  /**
   * @param visibility to set to this {@link ComponentDeclaration}.
   */
  public void setVisibility(ComponentVisibility visibility) {
    this.visibility = visibility;
  }
}
