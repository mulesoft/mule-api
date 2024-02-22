/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.comparing;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Utility class which groups operations, connection providers and sources together, also providing methods to add/get them
 *
 * @since 1.0
 */
final class SubDeclarationsContainer {

  private final Set<ConstructDeclaration> constructs = new LinkedHashSet<>();
  private final Set<FunctionDeclaration> functions = new LinkedHashSet<>();
  private final Set<OperationDeclaration> operations = new LinkedHashSet<>();
  private final Set<SourceDeclaration> messageSources = new LinkedHashSet<>();
  private final List<ConnectionProviderDeclaration> connectionProviders = new LinkedList<>();

  /**
   * @return an unmodifiable {@link List} with the available {@link OperationDeclaration}s
   */
  public List<OperationDeclaration> getOperations() {
    return alphaSorted(operations);
  }

  /**
   * @return an unmodifiable {@link List} with the available {@link ConnectionProviderDeclaration}s
   */
  public List<ConnectionProviderDeclaration> getConnectionProviders() {
    return alphaSorted(connectionProviders);
  }

  /**
   * @return an unmodifiable {@link List} with the available {@link SourceDeclaration}s
   */
  public List<SourceDeclaration> getMessageSources() {
    return alphaSorted(messageSources);
  }

  /**
   * @return an unmodifiable {@link List} with the available {@link FunctionDeclaration}s
   */
  public List<FunctionDeclaration> getFunctions() {
    return alphaSorted(functions);
  }

  /**
   * {@inheritDoc}
   */
  public List<ConstructDeclaration> getConstructs() {
    return alphaSorted(constructs);
  }

  /**
   * {@inheritDoc}
   */
  public void addConstruct(ConstructDeclaration declaration) {
    constructs.add(declaration);
  }

  /**
   * Removes the {@link ConstructDeclaration}.
   * 
   * @param declaration
   */
  void removeConstruct(ConstructDeclaration declaration) {
    constructs.remove(declaration);
  }

  /**
   * Adds a {@link ConnectionProviderDeclaration}
   *
   * @param connectionProvider a not {@code null} {@link ConnectionProviderDeclaration}
   * @throws IllegalArgumentException if {@code connectionProvider} is {@code null}
   */
  public void addConnectionProvider(ConnectionProviderDeclaration connectionProvider) {
    if (connectionProvider == null) {
      throw new IllegalArgumentException("Can't add a null connection provider");
    }

    connectionProviders.add(connectionProvider);
  }

  /**
   * Adds a {@link OperationDeclaration}
   *
   * @param operation a not {@code null} {@link OperationDeclaration}
   * @throws {@link IllegalArgumentException} if {@code operation} is {@code null}
   */
  public void addOperation(OperationDeclaration operation) {
    if (operation == null) {
      throw new IllegalArgumentException("Can't add a null operation");
    }

    operations.add(operation);
  }

  /**
   * Adds a {@link OperationDeclaration}
   *
   * @param function a not {@code null} {@link FunctionDeclaration}
   * @throws {@link IllegalArgumentException} if {@code function} is {@code null}
   */
  public void addFunction(FunctionDeclaration function) {
    checkArgument(function != null, "Can't add a null function");
    functions.add(function);
  }

  /**
   * Adds a {@link SourceDeclaration}
   *
   * @param sourceDeclaration a not {@code null} {@link SourceDeclaration}
   * @throws {@link IllegalArgumentException} if {@code sourceDeclaration} is {@code null}
   */
  public void addMessageSource(SourceDeclaration sourceDeclaration) {
    if (sourceDeclaration == null) {
      throw new IllegalArgumentException("Can't add a null message source");
    }

    messageSources.add(sourceDeclaration);
  }

  private <T extends NamedDeclaration> List<T> alphaSorted(Collection<T> collection) {
    ArrayList<T> list = new ArrayList<>(collection);
    sort(list, comparing(NamedDeclaration::getName));
    return unmodifiableList(list);
  }
}
