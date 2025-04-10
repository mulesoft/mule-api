/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.ExternalLibraryModel;
import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.meta.model.connection.ConnectionManagementType;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

/**
 * Allows configuring a {@link ConnectionProviderDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class ConnectionProviderDeclarer
    extends ParameterizedWithMinMuleVersionDeclarer<ConnectionProviderDeclarer, ConnectionProviderDeclaration>
    implements HasModelProperties<ConnectionProviderDeclarer>, DeclaresExternalLibraries<ConnectionProviderDeclarer>,
    HasStereotypeDeclarer<ConnectionProviderDeclarer>, HasSemanticTermsDeclarer<ConnectionProviderDeclarer> {

  /**
   * Creates a new instance
   *
   * @param declaration the declaration object to be configured
   */
  ConnectionProviderDeclarer(ConnectionProviderDeclaration declaration) {
    super(declaration);
  }

  /**
   * Adds a description to the provider
   *
   * @param description a description
   * @return {@code this} declarer
   */
  public ConnectionProviderDeclarer describedAs(String description) {
    declaration.setDescription(description);
    return this;
  }

  /**
   * Sets the type of connection management that the provider performs
   *
   * @param connectionManagementType a {@link ConnectionManagementType}
   * @return {@code this} declarer
   */
  public ConnectionProviderDeclarer withConnectionManagementType(ConnectionManagementType connectionManagementType) {
    declaration.setConnectionManagementType(connectionManagementType);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ConnectionProviderDeclarer withModelProperty(ModelProperty modelProperty) {
    declaration.addModelProperty(modelProperty);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ConnectionProviderDeclarer withExternalLibrary(ExternalLibraryModel externalLibrary) {
    declaration.addExternalLibrary(externalLibrary);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ConnectionProviderDeclarer withStereotype(StereotypeModel stereotype) {
    declaration.withStereotype(stereotype);
    return this;
  }

  public ConnectionProviderDeclarer supportsConnectivityTesting(boolean supportsConnectivityTesting) {
    declaration.setSupportsConnectivityTesting(supportsConnectivityTesting);
    return this;
  }

  /**
   * {@inheritDoc}
   *
   * @since 1.4.0
   */
  @Override
  public ConnectionProviderDeclarer withSemanticTerm(String semanticTerm) {
    declaration.addSemanticTerm(semanticTerm);
    return this;
  }
}
