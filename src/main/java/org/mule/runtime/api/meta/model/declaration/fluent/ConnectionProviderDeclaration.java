/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.ExternalLibraryModel;
import org.mule.runtime.api.meta.model.config.ConfigurationModel;
import org.mule.runtime.api.meta.model.connection.ConnectionManagementType;
import org.mule.runtime.api.meta.model.connection.ConnectionProviderModel;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A declaration object for a {@link ConnectionProviderModel}. It contains raw, unvalidated data which is used to declare the
 * structure of a {@link ConfigurationModel}
 *
 * @since 1.0
 */
public class ConnectionProviderDeclaration extends StereotypedDeclaration<ConnectionProviderDeclaration>
    implements WithStereotypesDeclaration {

  private ConnectionManagementType connectionManagementType;
  private final Set<ExternalLibraryModel> externalLibraryModels = new LinkedHashSet<>();
  private boolean supportsConnectivityTesting = true;
  private boolean supportsXa = false;

  /**
   * {@inheritDoc}
   */
  ConnectionProviderDeclaration(String name) {
    super(name);
  }

  /**
   * @return the {@link ConnectionManagementType} that will be applied to the produced connections
   */
  public ConnectionManagementType getConnectionManagementType() {
    return connectionManagementType;
  }

  /**
   * @return A {@link Set} of {@link ExternalLibraryModel} which represent the extension's external libraries
   */
  public Set<ExternalLibraryModel> getExternalLibraryModels() {
    return externalLibraryModels;
  }

  /**
   * Sets the {@link ConnectionManagementType}
   *
   * @param connectionManagementType the {@link ConnectionManagementType} that will be applied to the produced connections
   */
  public void setConnectionManagementType(ConnectionManagementType connectionManagementType) {
    this.connectionManagementType = connectionManagementType;
  }

  /**
   * Adds an {@link ExternalLibraryModel}
   *
   * @param externalLibraryModel the model of the external library to be referenced
   * @return {@code this} declarer
   */
  public ConnectionProviderDeclaration addExternalLibrary(ExternalLibraryModel externalLibraryModel) {
    externalLibraryModels.add(externalLibraryModel);
    return this;
  }

  public boolean isSupportsConnectivityTesting() {
    return supportsConnectivityTesting;
  }

  public void setSupportsConnectivityTesting(boolean supportsConnectivityTesting) {
    this.supportsConnectivityTesting = supportsConnectivityTesting;
  }

  public boolean isSupportsXa() {
    return supportsXa;
  }

  public void setSupportsXa(boolean supportsXa) {
    this.supportsXa = supportsXa;
  }
}
