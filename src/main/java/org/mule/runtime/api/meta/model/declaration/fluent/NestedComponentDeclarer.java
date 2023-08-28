/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

import java.util.stream.Stream;

/**
 * Allows configuring a {@link NestedComponentDeclaration} through a fluent API
 *
 * @since 1.0
 */
public class NestedComponentDeclarer<T extends NestedComponentDeclarer, D extends NestedComponentDeclaration>
    extends ComponentDeclarer<T, D> {

  /**
   * Creates a new instance
   *
   * @param declaration the {@link NestedComponentDeclaration} to be configured
   */
  public NestedComponentDeclarer(D declaration) {
    super(declaration);
  }

  /**
   * Adds the given {@code stereotypes} to the ones which are allowed on the route.
   *
   * @param stereotypes the stereotypes to add
   * @return {@code this} declarer
   */
  public NestedComponentDeclarer withAllowedStereotypes(StereotypeModel... stereotypes) {
    Stream.of(stereotypes).forEach(declaration::addAllowedStereotype);
    return this;
  }

  @Override
  public T withModelProperty(ModelProperty modelProperty) {
    return super.withModelProperty(modelProperty);
  }

  /**
   * Adds a description
   *
   * @param description a description
   * @return {@code this} declarer
   */
  public T describedAs(String description) {
    return super.describedAs(description);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T withStereotype(StereotypeModel stereotype) {
    return super.withStereotype(stereotype);
  }

  /**
   * Sets the minimum amount of times that this component can be present on the owning one.
   * <p>
   * Setting this to zero means that the element becomes optional.
   *
   * @param minOccurs a value equal or greater than zero
   * @return {@code this} declarer
   */
  public NestedComponentDeclarer<T, D> withMinOccurs(int minOccurs) {
    declaration.setMinOccurs(minOccurs);
    return this;
  }

  /**
   * Sets the maximum amount of times that this component can be present on the owning one.
   * <p>
   * Setting this to {@code null} means that repetitions are unbounded.
   *
   * @param maxOccurs a value greater or equal than zero
   * @return {@code this} declarer
   */
  public NestedComponentDeclarer<T, D> withMaxOccurs(Integer maxOccurs) {
    declaration.setMaxOccurs(maxOccurs);
    return this;
  }
}
