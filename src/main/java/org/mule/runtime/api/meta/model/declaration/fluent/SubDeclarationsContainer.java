/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.comparing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Utility class which groups operations, connection providers and sources together,
 * also providing methods to add/get them
 *
 * @since 1.0
 */
final class SubDeclarationsContainer {

  private final Set<OperationDeclaration> operations = new TreeSet<>(comparing(NamedDeclaration::getName));
  private final Set<SourceDeclaration> messageSources = new TreeSet<>(comparing(NamedDeclaration::getName));
  private final List<ConnectionProviderDeclaration> connectionProviders = new LinkedList<>();

  /**
   * @return an unmodifiable {@link List} with
   * the available {@link OperationDeclaration}s
   */
  public List<OperationDeclaration> getOperations() {
    return unmodifiableList(new ArrayList<>(operations));
  }

  /**
   * @return an unmodifiable {@link List} with the available {@link ConnectionProviderDeclaration}s
   */
  public List<ConnectionProviderDeclaration> getConnectionProviders() {
    return unmodifiableList(connectionProviders);
  }

  /**
   * @return an unmodifiable {@link List} with the available {@link SourceDeclaration}s
   */
  public List<SourceDeclaration> getMessageSources() {
    return unmodifiableList(new ArrayList<>(messageSources));
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
}
