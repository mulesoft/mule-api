/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static java.util.Comparator.comparing;
import org.mule.runtime.api.meta.model.ExternalLibraryModel;
import org.mule.runtime.api.meta.model.config.ConfigurationModel;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A declaration object for a {@link ConfigurationModel}. It contains raw, unvalidated
 * data which is used to declare the structure of a {@link ConfigurationModel}
 *
 * @since 1.0
 */
public class ConfigurationDeclaration extends ParameterizedDeclaration<ConfigurationDeclaration>
    implements ConnectedDeclaration<ConfigurationDeclaration>, WithSourcesDeclaration<ConfigurationDeclaration>,
    WithOperationsDeclaration<ConfigurationDeclaration> {

  private final SubDeclarationsContainer subDeclarations = new SubDeclarationsContainer();
  private final Set<ExternalLibraryModel> externalLibraryModels = new TreeSet<>(comparing(ExternalLibraryModel::getName));


  /**
   * {@inheritDoc}
   */
  ConfigurationDeclaration(String name) {
    super(name);
  }

  /**
   * @return an unmodifiable {@link List} with
   * the available {@link OperationDeclaration}s
   */
  @Override
  public List<OperationDeclaration> getOperations() {
    return subDeclarations.getOperations();
  }

  /**
   * @return an unmodifiable {@link List} with the available {@link ConnectionProviderDeclaration}s
   */
  @Override
  public List<ConnectionProviderDeclaration> getConnectionProviders() {
    return subDeclarations.getConnectionProviders();
  }

  /**
   * @return an unmodifiable {@link List} with the available {@link SourceDeclaration}s
   */
  @Override
  public List<SourceDeclaration> getMessageSources() {
    return subDeclarations.getMessageSources();
  }

  /**
   * @return A {@link Set} of {@link ExternalLibraryModel} which represent the extension's external libraries
   */
  public Set<ExternalLibraryModel> getExternalLibraryModels() {
    return externalLibraryModels;
  }

  /**
   * Adds a {@link ConnectionProviderDeclaration}
   *
   * @param connectionProvider a not {@code null} {@link ConnectionProviderDeclaration}
   * @return {@code this} declaration
   * @throws IllegalArgumentException if {@code connectionProvider} is {@code null}
   */
  @Override
  public ConfigurationDeclaration addConnectionProvider(ConnectionProviderDeclaration connectionProvider) {
    subDeclarations.addConnectionProvider(connectionProvider);
    return this;
  }

  /**
   * Adds a {@link OperationDeclaration}
   *
   * @param operation a not {@code null} {@link OperationDeclaration}
   * @return {@code this} declaration
   * @throws {@link IllegalArgumentException} if {@code operation} is {@code null}
   */
  @Override
  public ConfigurationDeclaration addOperation(OperationDeclaration operation) {
    subDeclarations.addOperation(operation);
    return this;
  }

  /**
   * Adds a {@link SourceDeclaration}
   *
   * @param sourceDeclaration a not {@code null} {@link SourceDeclaration}
   * @return {@code this} declaration
   * @throws {@link IllegalArgumentException} if {@code sourceDeclaration} is {@code null}
   */
  @Override
  public ConfigurationDeclaration addMessageSource(SourceDeclaration sourceDeclaration) {
    subDeclarations.addMessageSource(sourceDeclaration);
    return this;
  }

  /**
   * Adds an {@link ExternalLibraryModel}
   *
   * @param externalLibraryModel the model of the external library to be referenced
   * @return {@code this} declarer
   */
  public ConfigurationDeclaration addExternalLibrary(ExternalLibraryModel externalLibraryModel) {
    externalLibraryModels.add(externalLibraryModel);
    return this;
  }
}
