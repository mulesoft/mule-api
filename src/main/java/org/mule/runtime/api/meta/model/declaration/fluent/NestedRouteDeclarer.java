/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static org.mule.runtime.api.meta.model.parameter.ParameterGroupModel.DEFAULT_GROUP_NAME;

import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

/**
 * Allows configuring a {@link NestedComponentDeclaration} through a fluent API.
 * <p>
 * Keep in mind that Routes are required to have at least one child component, which translate to almost always having to invoke
 * the {@link NestedRouteDeclarer#withChain} method of this declarer.
 *
 * @since 1.0
 */
public class NestedRouteDeclarer extends Declarer<NestedRouteDeclaration>
    implements HasModelProperties<NestedRouteDeclarer>, HasStereotypeDeclarer<NestedRouteDeclarer>, HasNestedComponentsDeclarer,
    HasParametersDeclarer {

  /**
   * Creates a new instance
   *
   * @param declaration the {@link NestedComponentDeclaration} to be configured
   */
  public NestedRouteDeclarer(NestedRouteDeclaration declaration) {
    super(declaration);
  }

  /**
   * Sets the minimum amount of times that this route can be present on the owning component
   *
   * @param minOccurs a value equal or greater than zero
   * @return {@code this} declarer
   */
  public NestedRouteDeclarer withMinOccurs(int minOccurs) {
    declaration.setMinOccurs(minOccurs);
    return this;
  }

  /**
   * Sets the maximum amount of times that this route can be present on the owning component
   *
   * @param maxOccurs a value greater or equal than zero
   * @return {@code this} declarer
   */
  public NestedRouteDeclarer withMaxOccurs(Integer maxOccurs) {
    declaration.setMaxOccurs(maxOccurs);
    return this;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public NestedRouteDeclaration getDeclaration() {
    if (declaration.getNestedComponents().isEmpty()) {
      withChain();
    }

    return declaration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NestedComponentDeclarer withOptionalComponent(String nestedComponentName) {
    NestedComponentDeclarer<?, ?> declarer = withComponent(nestedComponentName);
    declarer.getDeclaration().setRequired(false);
    return declarer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NestedComponentDeclarer withComponent(String nestedComponentName) {
    NestedComponentDeclaration nested = new NestedComponentDeclaration(nestedComponentName);
    declaration.addNestedComponent(nested);
    return new NestedComponentDeclarer(nested);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NestedChainDeclarer withChain() {
    NestedChainDeclaration nested = new NestedChainDeclaration("processors");
    declaration.addNestedComponent(nested);
    return new NestedChainDeclarer(nested);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NestedChainDeclarer withChain(String chainName) {
    NestedChainDeclaration nested = new NestedChainDeclaration(chainName);
    declaration.addNestedComponent(nested);
    return new NestedChainDeclarer(nested);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ParameterGroupDeclarer onParameterGroup(String name) {
    return new ParameterGroupDeclarer(declaration.getParameterGroup(name));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ParameterGroupDeclarer onDefaultParameterGroup() {
    return onParameterGroup(DEFAULT_GROUP_NAME);
  }

  @Override
  public NestedRouteDeclarer withModelProperty(ModelProperty modelProperty) {
    declaration.addModelProperty(modelProperty);
    return this;
  }

  /**
   * Adds the given {@code stereotype}
   *
   * @param stereotype a {@link StereotypeModel}
   * @return {@code this} declarer
   *
   * @since 1.7
   */
  @Override
  public NestedRouteDeclarer withStereotype(StereotypeModel stereotype) {
    declaration.withStereotype(stereotype);
    return this;
  }

  /**
   * Adds a description
   *
   * @param description a description
   * @return {@code this} declarer
   */
  public NestedRouteDeclarer describedAs(String description) {
    declaration.setDescription(description);
    return this;
  }
}
