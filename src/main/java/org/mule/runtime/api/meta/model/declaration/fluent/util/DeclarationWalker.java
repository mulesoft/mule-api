/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent.util;

import static org.mule.runtime.api.util.Preconditions.checkArgument;

import org.mule.runtime.api.meta.model.declaration.fluent.ConfigurationDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ConnectedDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ConnectionProviderDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ConstructDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ExtensionDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.FunctionDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.OperationDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ParameterDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ParameterGroupDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ParameterizedDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.SourceDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.WithConstructsDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.WithFunctionsDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.WithOperationsDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.WithSourcesDeclaration;

/**
 * Navigates a {@link ExtensionDeclaration} and invokes methods when important model components are found.
 * <p>
 * This is useful to centralize the logic of how to iterate through a model's structure without coupling to it. For example, the
 * {@link #onOperation(WithOperationsDeclaration, OperationDeclaration)} method allows handling operations without requiring to
 * know that they can exist at global or configuration level. Something similar can be said about the
 * {@link #onParameter(ParameterizedDeclaration, ParameterGroupDeclaration, ParameterDeclaration)}, etc.
 *
 * @since 1.0
 */
public abstract class DeclarationWalker {

  private boolean stopped = false;

  /**
   * Navigates the given {@code extensionDeclaration} and invokes the other public method's in this class as the navigation
   * progresses
   *
   * @param extensionDeclaration the model to navigate
   */
  public final void walk(ExtensionDeclaration extensionDeclaration) {
    checkArgument(extensionDeclaration != null, "Cannot walk a null declaration");
    this.stopped = false;

    for (ConfigurationDeclaration configuration : extensionDeclaration.getConfigurations()) {
      if (stopped) {
        return;
      }
      onConfiguration(configuration);
      ifContinue(() -> walkConnectionProviders(configuration));
      ifContinue(() -> walkParameters(configuration));
      ifContinue(() -> walkSources(configuration));
      ifContinue(() -> walkOperations(configuration));
    }

    ifContinue(() -> walkConnectionProviders(extensionDeclaration));
    ifContinue(() -> walkSources(extensionDeclaration));
    ifContinue(() -> walkOperations(extensionDeclaration));
    ifContinue(() -> walkFunctions(extensionDeclaration));
    ifContinue(() -> walkConstructs(extensionDeclaration));
  }

  /**
   * When invoked, the traversal of the {@link ExtensionDeclaration} will stop once the current visit is completed. This will not
   * break the execution within the invoking method, but instead avoid further traversal of the model from that point on.
   */
  protected final void stop() {
    stopped = true;
  }

  /**
   * Invoked when a {@link ConfigurationDeclaration} is found in the traversed {@code extensionDeclaration}
   *
   * @param declaration a {@link ConfigurationDeclaration}
   */
  protected void onConfiguration(ConfigurationDeclaration declaration) {}

  /**
   * Invoked when an {@link OperationDeclaration} is found in the traversed {@code extensionDeclaration}.
   * <p>
   * 
   * @param owner       The declaration that owns the operation
   * @param declaration the {@link WithOperationsDeclaration}
   */
  protected void onOperation(WithOperationsDeclaration owner, OperationDeclaration declaration) {}

  /**
   * Invoked when an {@link FunctionDeclaration} is found in the traversed {@code extensionDeclaration}.
   * <p>
   *
   * @param owner The declaration that owns the function
   * @param model the {@link FunctionDeclaration}
   */
  protected void onFunction(WithFunctionsDeclaration owner, FunctionDeclaration model) {}

  /**
   * Invoked when a {@link ConstructDeclaration} is found in the traversed {@code extensionDeclaration}.
   * <p>
   *
   * @param owner       The declaration that owns the operation
   * @param declaration the {@link WithOperationsDeclaration}
   */
  protected void onConstruct(WithConstructsDeclaration owner, ConstructDeclaration declaration) {}

  /**
   * Invoked when an {@link ConnectedDeclaration} is found in the traversed {@code extensionDeclaration}
   *
   * @param owner       The declaration that owns the provider
   * @param declaration the {@link ConnectionProviderDeclaration}
   */
  protected void onConnectionProvider(ConnectedDeclaration owner, ConnectionProviderDeclaration declaration) {}

  /**
   * Invoked when an {@link SourceDeclaration} is found in the traversed {@code extensionDeclaration}
   *
   * @param owner       The declaration that owns the source
   * @param declaration the {@link SourceDeclaration}
   */
  protected void onSource(WithSourcesDeclaration owner, SourceDeclaration declaration) {}

  /**
   * Invoked when an {@link ParameterGroupDeclaration} is found in the traversed {@code extensionDeclaration}
   *
   * @param owner       The declaration that owns the parameter
   * @param declaration the {@link ParameterGroupDeclaration}
   */
  protected void onParameterGroup(ParameterizedDeclaration owner, ParameterGroupDeclaration declaration) {}

  /**
   * Invoked when an {@link ParameterDeclaration} is found in the traversed {@code extensionDeclaration}
   *
   * @param owner          The declaration that owns the parameter
   * @param parameterGroup the group to which the declaration belongs
   * @param declaration    the {@link ParameterDeclaration}
   */
  protected void onParameter(ParameterizedDeclaration owner, ParameterGroupDeclaration parameterGroup,
                             ParameterDeclaration declaration) {}


  private void walkConstructs(WithConstructsDeclaration<?> declaration) {
    for (ConstructDeclaration construct : declaration.getConstructs()) {
      if (stopped) {
        return;
      }
      onConstruct(declaration, construct);
      ifContinue(() -> walkParameters(construct));
    }
  }

  private void walkSources(WithSourcesDeclaration declaration) {
    for (Object source : declaration.getMessageSources()) {
      if (stopped) {
        return;
      }
      SourceDeclaration sourceDeclaration = (SourceDeclaration) source;
      onSource(declaration, sourceDeclaration);
      ifContinue(() -> walkParameters(sourceDeclaration));
      ifContinue(() -> sourceDeclaration.getSuccessCallback().ifPresent(this::walkParameters));
      ifContinue(() -> sourceDeclaration.getErrorCallback().ifPresent(this::walkParameters));
    }
  }

  private void walkParameters(ParameterizedDeclaration declaration) {
    for (Object g : declaration.getParameterGroups()) {
      if (stopped) {
        return;
      }
      ParameterGroupDeclaration group = (ParameterGroupDeclaration) g;
      onParameterGroup(declaration, group);
      ifContinue(() -> walkGroupParameters(declaration, group));
    }
  }

  private void walkGroupParameters(ParameterizedDeclaration declaration, ParameterGroupDeclaration group) {
    for (ParameterDeclaration p : group.getParameters()) {
      if (stopped) {
        return;
      }
      onParameter(declaration, group, p);
    }
  }

  private void walkConnectionProviders(ConnectedDeclaration declaration) {
    for (Object provider : declaration.getConnectionProviders()) {
      if (stopped) {
        return;
      }
      final ConnectionProviderDeclaration providerDeclaration = (ConnectionProviderDeclaration) provider;
      onConnectionProvider(declaration, providerDeclaration);
      ifContinue(() -> walkParameters(providerDeclaration));
    }
  }

  private void walkOperations(WithOperationsDeclaration<?> model) {
    for (Object operation : model.getOperations()) {
      if (stopped) {
        return;
      }
      final OperationDeclaration declaration = (OperationDeclaration) operation;
      onOperation(model, declaration);

      ifContinue(() -> walkParameters(declaration));
    }
  }

  private void walkFunctions(WithFunctionsDeclaration declaration) {
    for (Object function : declaration.getFunctions()) {
      if (stopped) {
        return;
      }

      onFunction(declaration, (FunctionDeclaration) function);
      ifContinue(() -> walkParameters((FunctionDeclaration) function));
    }
  }

  private void ifContinue(Runnable action) {
    if (!stopped) {
      action.run();
    }
  }
}
