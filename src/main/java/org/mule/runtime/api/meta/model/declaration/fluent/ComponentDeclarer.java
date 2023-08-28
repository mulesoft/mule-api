/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.ComponentVisibility;
import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.meta.model.deprecated.DeprecationModel;
import org.mule.runtime.api.meta.model.error.ErrorModel;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

/**
 * Allows configuring a {@link ComponentDeclaration} through a fluent API
 *
 * @since 1.0
 */
public abstract class ComponentDeclarer<T extends ComponentDeclarer, D extends ComponentDeclaration>
    extends ParameterizedWithMinMuleVersionDeclarer<T, D>
    implements HasModelProperties<T>, HasNestedComponentsDeclarer,
    HasNestedRoutesDeclarer, HasStereotypeDeclarer<T>, HasDeprecatedDeclarer<T>, HasSemanticTermsDeclarer<T>, HasVisibility<T> {

  /**
   * Creates a new instance
   *
   * @param declaration the {@link ComponentDeclaration} which will be configured
   */
  ComponentDeclarer(D declaration) {
    super(declaration);
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
  public NestedRouteDeclarer withRoute(String routeName) {
    NestedRouteDeclaration nested = new NestedRouteDeclaration(routeName);
    declaration.addNestedComponent(nested);
    return new NestedRouteDeclarer(nested);
  }

  /**
   * Adds the given {@code stereotype}
   *
   * @param stereotype a {@link StereotypeModel}
   * @return {@code this} declarer
   */
  public T withStereotype(StereotypeModel stereotype) {
    declaration.withStereotype(stereotype);
    return (T) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T withModelProperty(ModelProperty modelProperty) {
    declaration.addModelProperty(modelProperty);
    return (T) this;
  }

  /**
   * Adds an {@link ErrorModel} to indicate that the current operation could throw the added error.
   *
   * @param error {@link ErrorModel} to add to the {@link OperationDeclaration}
   * @return {@code this} declarer
   */
  public T withErrorModel(ErrorModel error) {
    declaration.addErrorModel(error);
    return (T) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T withDeprecation(DeprecationModel deprecation) {
    declaration.withDeprecation(deprecation);
    return (T) this;
  }

  /**
   * {@inheritDoc}
   *
   * @since 1.4.0
   */
  @Override
  public T withSemanticTerm(String semanticTerm) {
    declaration.addSemanticTerm(semanticTerm);
    return (T) this;
  }

  /**
   * {@inheritDoc}
   *
   * @since 1.5.0
   */
  public T withVisibility(ComponentVisibility visibility) {
    declaration.setVisibility(visibility);
    return (T) this;
  }
}
