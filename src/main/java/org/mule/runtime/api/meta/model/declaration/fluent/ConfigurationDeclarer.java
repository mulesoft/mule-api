/*
/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.ExternalLibraryModel;
import org.mule.runtime.api.meta.model.ModelProperty;

/**
 * Allows configuring a {@link ConfigurationDeclaration} through a fluent API
 *
 * @since 1.0
 */
public class ConfigurationDeclarer extends ParameterizedDeclarer<ConfigurationDeclaration> implements
    HasOperationDeclarer, HasConnectionProviderDeclarer, HasSourceDeclarer, HasModelProperties<ConfigurationDeclarer>,
    DeclaresExternalLibraries<ConfigurationDeclarer> {

  /**
   * Creates a new instance
   *
   * @param declaration the declaration object to be configured
   */
  ConfigurationDeclarer(ConfigurationDeclaration declaration) {
    super(declaration);
  }

  /**
   * Adds a description to the configuration
   *
   * @param description a description
   * @return {@code this} declarer
   */
  public ConfigurationDeclarer describedAs(String description) {
    declaration.setDescription(description);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OperationDeclarer withOperation(String name) {
    OperationDeclaration operation = new OperationDeclaration(name);

    final OperationDeclarer operationDeclarer = new OperationDeclarer(operation);
    withOperation(operationDeclarer);

    return operationDeclarer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ScopeDeclarer withScope(String name) {
    ScopeDeclaration scope = new ScopeDeclaration(name);
    final ScopeDeclarer scopeDeclarer = new ScopeDeclarer(scope);
    withScope(scopeDeclarer);

    return scopeDeclarer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void withScope(ScopeDeclarer declarer) {
    declaration.addOperation(declarer.getDeclaration());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RouterDeclarer withRouter(String name) {
    RouterDeclaration router = new RouterDeclaration(name);
    final RouterDeclarer routerDeclarer = new RouterDeclarer(router);
    withRouter(routerDeclarer);

    return routerDeclarer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void withRouter(RouterDeclarer declarer) {
    declaration.addOperation(declarer.getDeclaration());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void withOperation(OperationDeclarer declarer) {
    declaration.addOperation(declarer.getDeclaration());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ConnectionProviderDeclarer withConnectionProvider(String name) {
    ConnectionProviderDeclaration declaration = new ConnectionProviderDeclaration(name);

    final ConnectionProviderDeclarer connectionProviderDeclarer = new ConnectionProviderDeclarer(declaration);
    withConnectionProvider(connectionProviderDeclarer);

    return connectionProviderDeclarer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void withConnectionProvider(ConnectionProviderDeclarer declarer) {
    declaration.addConnectionProvider(declarer.getDeclaration());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SourceDeclarer withMessageSource(String name) {
    SourceDeclaration declaration = new SourceDeclaration(name);

    final SourceDeclarer sourceDeclarer = new SourceDeclarer(declaration);
    withMessageSource(sourceDeclarer);

    return sourceDeclarer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void withMessageSource(SourceDeclarer declarer) {
    declaration.addMessageSource(declarer.getDeclaration());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ConfigurationDeclarer withModelProperty(ModelProperty modelProperty) {
    declaration.addModelProperty(modelProperty);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ConfigurationDeclarer withExternalLibrary(ExternalLibraryModel externalLibrary) {
    declaration.addExternalLibrary(externalLibrary);
    return this;
  }

}
