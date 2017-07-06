/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.util;

import static org.mule.runtime.api.util.Preconditions.checkArgument;
import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.api.meta.model.config.ConfigurationModel;
import org.mule.runtime.api.meta.model.connection.ConnectionProviderModel;
import org.mule.runtime.api.meta.model.connection.HasConnectionProviderModels;
import org.mule.runtime.api.meta.model.operation.HasOperationModels;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.operation.RouterModel;
import org.mule.runtime.api.meta.model.operation.ScopeModel;
import org.mule.runtime.api.meta.model.parameter.ParameterGroupModel;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;
import org.mule.runtime.api.meta.model.source.HasSourceModels;
import org.mule.runtime.api.meta.model.source.SourceModel;

/**
 * Navigates a {@link ExtensionModel} and invokes methods when important
 * model components are found.
 * <p>
 * This is useful to centralize the logic of how to iterate through a
 * model's structure without coupling to it. For example, the
 * {@link #onOperation(HasOperationModels, OperationModel)} method allows
 * handling operations without requiring to know that they can exist
 * at global or configuration level. Something similar can be said
 * about the {@link #onParameter(ParameterizedModel, ParameterGroupModel, ParameterModel)},
 * etc.
 *
 * @since 1.0
 */
public abstract class ExtensionWalker {

  private boolean stopped;

  /**
   * Navigates the given {@code extensionModel} and invokes the
   * other public method's in this class as the navigation
   * progresses
   *
   * @param extensionModel the model to navigate
   */
  public final void walk(ExtensionModel extensionModel) {
    checkArgument(extensionModel != null, "Cannot walk a null model");
    this.stopped = false;

    for (ConfigurationModel model : extensionModel.getConfigurationModels()) {
      if (stopped) {
        return;
      }
      onConfiguration(model);
      ifContinue(() -> walkConnectionProviders(model));
      ifContinue(() -> walkParameters(model));
      ifContinue(() -> walkSources(model));
      ifContinue(() -> walkOperations(model));
    }

    ifContinue(() -> walkConnectionProviders(extensionModel));
    ifContinue(() -> walkSources(extensionModel));
    ifContinue(() -> walkOperations(extensionModel));
  }

  /**
   * When invoked, the traversal of the {@link ExtensionModel} will
   * stop once the current visit is completed.
   * This will not break the execution within the invoking method,
   * but instead avoid further traversal of the model from that point on.
   */
  protected final void stop() {
    stopped = true;
  }

  /**
   * Invoked when a {@link ConfigurationModel} is found in the
   * traversed {@code extensionModel}
   *
   * @param model a {@link ConfigurationModel}
   */
  protected void onConfiguration(ConfigurationModel model) {}

  /**
   * Invoked when an {@link OperationModel} is found in the
   * traversed {@code extensionModel}.
   * <p>
   * This method is also invoked when a {@link ScopeModel} or a
   * {@link RouterModel} are found, if and only if the
   * {@link #onScope(HasOperationModels, ScopeModel)}
   * or {@link #onRouter(HasOperationModels, RouterModel)}
   * methods were not overridden respectively.
   *
   * @param owner The component that owns the operation
   * @param model the {@link OperationModel}
   */
  protected void onOperation(HasOperationModels owner, OperationModel model) {}

  /**
   * Invoked when a {@link ScopeModel} is found in the
   * traversed {@code extensionModel}.
   * <p>
   * By default, this method simply delegates on {@link #onOperation(HasOperationModels, OperationModel)}
   *
   * @param owner The component that owns the operation
   * @param model the {@link OperationModel}
   */
  protected void onScope(HasOperationModels owner, ScopeModel model) {
    onOperation(owner, model);
  }

  /**
   * Invoked when a {@link RouterModel} is found in the
   * traversed {@code extensionModel}.
   * <p>
   * By default, this method simply delegates on {@link #onOperation(HasOperationModels, OperationModel)}
   *
   * @param owner The component that owns the operation
   * @param model the {@link OperationModel}
   */
  protected void onRouter(HasOperationModels owner, RouterModel model) {
    onOperation(owner, model);
  }

  /**
   * Invoked when an {@link ConnectionProviderModel} is found in the
   * traversed {@code extensionModel}
   *
   * @param owner The component that owns the provider
   * @param model the {@link ConnectionProviderModel}
   */
  protected void onConnectionProvider(HasConnectionProviderModels owner, ConnectionProviderModel model) {}

  /**
   * Invoked when an {@link SourceModel} is found in the
   * traversed {@code extensionModel}
   *
   * @param owner The component that owns the source
   * @param model the {@link SourceModel}
   */
  protected void onSource(HasSourceModels owner, SourceModel model) {}

  /**
   * Invoked when an {@link ParameterGroupModel} is found in the
   * traversed {@code extensionModel}
   *
   * @param owner The component that owns the source
   * @param model the {@link ParameterGroupModel}
   */
  protected void onParameterGroup(ParameterizedModel owner, ParameterGroupModel model) {}

  /**
   * Invoked when an {@link ParameterModel} is found in the
   * traversed {@code extensionModel}
   *
   * @param owner      The component that owns the parameter
   * @param groupModel the {@link ParameterGroupModel} in which the {@code model} is contained
   * @param model      the {@link ParameterModel}
   */
  protected void onParameter(ParameterizedModel owner, ParameterGroupModel groupModel, ParameterModel model) {}

  private void walkSources(HasSourceModels model) {
    for (SourceModel source : model.getSourceModels()) {
      if (stopped) {
        return;
      }
      onSource(model, source);
      ifContinue(() -> walkParameters(source));
      ifContinue(() -> source.getSuccessCallback().ifPresent(this::walkParameters));
      ifContinue(() -> source.getErrorCallback().ifPresent(this::walkParameters));
    }
  }

  private void walkParameters(ParameterizedModel model) {
    for (ParameterGroupModel group : model.getParameterGroupModels()) {
      if (stopped) {
        return;
      }
      onParameterGroup(model, group);
      ifContinue(() -> walkGroupParameters(model, group));
    }
  }

  private void walkGroupParameters(ParameterizedModel model, ParameterGroupModel group) {
    for (ParameterModel p : group.getParameterModels()) {
      if (stopped) {
        return;
      }
      onParameter(model, group, p);
    }
  }

  private void walkConnectionProviders(HasConnectionProviderModels model) {
    for (ConnectionProviderModel provider : model.getConnectionProviders()) {
      if (stopped) {
        return;
      }
      onConnectionProvider(model, provider);
      ifContinue(() -> walkParameters(provider));
    }
  }

  private void walkOperations(HasOperationModels model) {
    for (OperationModel operation : model.getOperationModels()) {
      if (stopped) {
        return;
      }

      operation.accept(new ComponentModelVisitor() {

        @Override
        public void visit(OperationModel operationModel) {
          onOperation(model, operation);
        }

        @Override
        public void visit(ScopeModel scopeModel) {
          onScope(model, scopeModel);
        }

        @Override
        public void visit(RouterModel routerModel) {
          onRouter(model, routerModel);
        }

        @Override
        public void visit(SourceModel sourceModel) {}
      });

      ifContinue(() -> walkParameters(operation));
    }
  }

  private void ifContinue(Runnable action) {
    if (!stopped) {
      action.run();
    }
  }

}
