/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent.util;

import org.mule.runtime.api.meta.model.declaration.fluent.ConfigurationDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ConnectedDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ConnectionProviderDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ExtensionDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.OperationDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ParameterDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ParameterizedDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.SourceDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.WithOperationsDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.WithSourcesDeclaration;

/**
 * Navigates a {@link ExtensionDeclaration} and invokes methods when important
 * model components are found.
 * <p>
 * This is useful to centralize the logic of how to iterate through a
 * model's structure without coupling to it. For example, the
 * {@link #onOperation(WithOperationsDeclaration, OperationDeclaration)}
 * method allows handling operations without requiring to know that they can exist
 * at global or configuration level. Something similar can be said
 * about the {@link #onParameter(ParameterizedDeclaration, ParameterDeclaration)},
 * etc.
 *
 * @since 1.0
 */
public abstract class DeclarationWalker {

  /**
   * Navigates the given {@code extensionDeclaration} and invokes the
   * other public method's in this class as the navigation
   * progresses
   *
   * @param extensionDeclaration the model to navigate
   */
  public final void walk(ExtensionDeclaration extensionDeclaration) {
    if (extensionDeclaration == null) {
      throw new IllegalArgumentException("Cannot walk a null declaration");
    }

    extensionDeclaration.getConfigurations().forEach(configuration -> {
      onConfiguration(configuration);
      walkConnectionProviders(configuration);
      walkParameters(configuration);
      walkSources(configuration);
      walkOperations(configuration);
    });

    walkConnectionProviders(extensionDeclaration);
    walkSources(extensionDeclaration);
    walkOperations(extensionDeclaration);
  }

  /**
   * Invoked when a {@link ConfigurationDeclaration} is found in the
   * traversed {@code extensionDeclaration}
   *
   * @param declaration a {@link ConfigurationDeclaration}
   */
  public void onConfiguration(ConfigurationDeclaration declaration) {}

  /**
   * Invoked when an {@link OperationDeclaration} is found in the
   * traversed {@code extensionDeclaration}
   *
   * @param owner       The declaration that owns the operation
   * @param declaration the {@link WithOperationsDeclaration}
   */
  public void onOperation(WithOperationsDeclaration owner, OperationDeclaration declaration) {}

  /**
   * Invoked when an {@link ConnectedDeclaration} is found in the
   * traversed {@code extensionDeclaration}
   *
   * @param owner       The declaration that owns the provider
   * @param declaration the {@link ConnectionProviderDeclaration}
   */
  public void onConnectionProvider(ConnectedDeclaration owner, ConnectionProviderDeclaration declaration) {}

  /**
   * Invoked when an {@link SourceDeclaration} is found in the
   * traversed {@code extensionDeclaration}
   *
   * @param owner       The declaration that owns the source
   * @param declaration the {@link SourceDeclaration}
   */
  public void onSource(WithSourcesDeclaration owner, SourceDeclaration declaration) {}

  /**
   * Invoked when an {@link ParameterDeclaration} is found in the
   * traversed {@code extensionDeclaration}
   *
   * @param owner       The declaration that owns the parameter
   * @param declaration the {@link ParameterDeclaration}
   */
  public void onParameter(ParameterizedDeclaration owner, ParameterDeclaration declaration) {}

  private void walkSources(WithSourcesDeclaration declaration) {
    declaration.getMessageSources().forEach(source -> {
      SourceDeclaration sourceDeclaration = (SourceDeclaration) source;
      onSource(declaration, sourceDeclaration);
      walkParameters(sourceDeclaration);
    });
  }

  private void walkParameters(ParameterizedDeclaration declaration) {
    declaration.getParameters().forEach(parameter -> onParameter(declaration, (ParameterDeclaration) parameter));
  }

  private void walkConnectionProviders(ConnectedDeclaration declaration) {
    declaration.getConnectionProviders().stream().forEach(provider -> {
      final ConnectionProviderDeclaration providerDeclaration = (ConnectionProviderDeclaration) provider;
      onConnectionProvider(declaration, providerDeclaration);
      walkParameters(providerDeclaration);
    });
  }

  private void walkOperations(WithOperationsDeclaration model) {
    model.getOperations().stream().forEach(operation -> {
      final OperationDeclaration operationDeclaration = (OperationDeclaration) operation;
      onOperation(model, operationDeclaration);
      walkParameters(operationDeclaration);
    });
  }
}
