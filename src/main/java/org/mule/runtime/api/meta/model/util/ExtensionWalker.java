/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.util;

import static org.mule.runtime.api.util.Preconditions.checkArgument;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.meta.model.ComposableModel;
import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.api.meta.model.SubTypesModel;
import org.mule.runtime.api.meta.model.config.ConfigurationModel;
import org.mule.runtime.api.meta.model.connection.ConnectionProviderModel;
import org.mule.runtime.api.meta.model.connection.HasConnectionProviderModels;
import org.mule.runtime.api.meta.model.construct.ConstructModel;
import org.mule.runtime.api.meta.model.construct.HasConstructModels;
import org.mule.runtime.api.meta.model.function.FunctionModel;
import org.mule.runtime.api.meta.model.function.HasFunctionModels;
import org.mule.runtime.api.meta.model.nested.NestableElementModel;
import org.mule.runtime.api.meta.model.operation.HasOperationModels;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.parameter.ParameterGroupModel;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;
import org.mule.runtime.api.meta.model.source.HasSourceModels;
import org.mule.runtime.api.meta.model.source.SourceModel;

/**
 * Navigates a {@link ExtensionModel} and invokes methods when important model components are found.
 * <p>
 * This is useful to centralize the logic of how to iterate through a model's structure without coupling to it. For example, the
 * {@link #onOperation(HasOperationModels, OperationModel)} method allows handling operations without requiring to know that they
 * can exist at global or configuration level. Something similar can be said about the
 * {@link #onParameter(ParameterizedModel, ParameterGroupModel, ParameterModel)}, etc.
 *
 * @since 1.0
 */
public abstract class ExtensionWalker {

  private boolean stopped;

  /**
   * Navigates the given {@code extensionModel} and invokes the other public method's in this class as the navigation progresses
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
    ifContinue(() -> walkFunctions(extensionModel));
    ifContinue(() -> walkConstructs(extensionModel));
    ifContinue(() -> walkTypes(extensionModel));

  }

  /**
   * When invoked, the traversal of the {@link ExtensionModel} will stop once the current visit is completed. This will not break
   * the execution within the invoking method, but instead avoid further traversal of the model from that point on.
   */
  protected final void stop() {
    stopped = true;
  }

  /**
   * Invoked when a {@link ConfigurationModel} is found in the traversed {@code extensionModel}
   *
   * @param model a {@link ConfigurationModel}
   */
  protected void onConfiguration(ConfigurationModel model) {}

  /**
   * Invoked when an {@link OperationModel} is found in the traversed {@code extensionModel}.
   * <p>
   *
   * @param owner The component that owns the operation
   * @param model the {@link OperationModel}
   */
  protected void onOperation(HasOperationModels owner, OperationModel model) {}

  /**
   * Invoked when an {@link FunctionModel} is found in the traversed {@code extensionModel}.
   * <p>
   *
   * @param owner The component that owns the function
   * @param model the {@link FunctionModel}
   */
  protected void onFunction(HasFunctionModels owner, FunctionModel model) {}

  /**
   * Invoked when an {@link ConstructModel} is found in the traversed {@code extensionModel}
   *
   * @param owner The component that owns the source
   * @param model the {@link ConstructModel}
   */
  protected void onConstruct(HasConstructModels owner, ConstructModel model) {}

  /**
   * Invoked when an {@link ConnectionProviderModel} is found in the traversed {@code extensionModel}
   *
   * @param owner The component that owns the provider
   * @param model the {@link ConnectionProviderModel}
   */
  protected void onConnectionProvider(HasConnectionProviderModels owner, ConnectionProviderModel model) {}

  /**
   * Invoked when an {@link SourceModel} is found in the traversed {@code extensionModel}
   *
   * @param owner The component that owns the source
   * @param model the {@link SourceModel}
   */
  protected void onSource(HasSourceModels owner, SourceModel model) {}

  /**
   * Invoked when an {@link ParameterGroupModel} is found in the traversed {@code extensionModel}
   *
   * @param owner The component that owns the source
   * @param model the {@link ParameterGroupModel}
   */
  protected void onParameterGroup(ParameterizedModel owner, ParameterGroupModel model) {}

  /**
   * Invoked when an {@link ParameterModel} is found in the traversed {@code extensionModel}
   *
   * @param owner      The component that owns the parameter
   * @param groupModel the {@link ParameterGroupModel} in which the {@code model} is contained
   * @param model      the {@link ParameterModel}
   */
  protected void onParameter(ParameterizedModel owner, ParameterGroupModel groupModel, ParameterModel model) {}

  /**
   * Invoked when an {@link NestableElementModel} is found in the traversed {@code extensionModel}
   *
   * @param owner The component that owns the parameter
   * @param model the {@link NestableElementModel}
   */
  protected void onNestable(ComposableModel owner, NestableElementModel model) {}

  /**
   * Invoked when an {@link MetadataType} is found in the traversed {@code extensionModel}.
   * <p>
   * For each distinct type found, this method will be invoked just once.
   *
   * @param owner The component that owns the parameter
   * @param type  the {@link MetadataType}
   *
   * @since 1.4
   */
  protected void onType(ExtensionModel owner, MetadataType type) {}

  private void walkSources(HasSourceModels model) {
    for (SourceModel source : model.getSourceModels()) {
      if (stopped) {
        return;
      }
      onSource(model, source);
      ifContinue(() -> walkParameters(source));
      ifContinue(() -> source.getSuccessCallback().ifPresent(this::walkParameters));
      ifContinue(() -> source.getErrorCallback().ifPresent(this::walkParameters));
      ifContinue(() -> walkNesteable(source));
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

  private void walkNesteable(ComposableModel model) {
    for (NestableElementModel nested : model.getNestedComponents()) {
      if (stopped) {
        return;
      }
      onNestable(model, nested);
      ifContinue(() -> walkNesteable(nested));
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

  private void walkConstructs(ExtensionModel model) {
    for (ConstructModel construct : model.getConstructModels()) {
      if (stopped) {
        return;
      }
      onConstruct(model, construct);
      ifContinue(() -> walkParameters(construct));
      ifContinue(() -> walkNesteable(construct));
    }
  }

  private void walkOperations(HasOperationModels model) {
    for (OperationModel operation : model.getOperationModels()) {
      if (stopped) {
        return;
      }

      onOperation(model, operation);
      ifContinue(() -> walkParameters(operation));
      ifContinue(() -> walkNesteable(operation));
    }
  }

  private void walkFunctions(HasFunctionModels model) {
    for (FunctionModel function : model.getFunctionModels()) {
      if (stopped) {
        return;
      }

      onFunction(model, function);
      ifContinue(() -> walkParameters(function));
    }
  }

  private void walkTypes(ExtensionModel model) {
    for (MetadataType type : model.getTypes()) {
      if (stopped) {
        return;
      }

      onType(model, type);
    }
    for (SubTypesModel subType : model.getSubTypes()) {
      for (MetadataType type : subType.getSubTypes()) {
        if (stopped) {
          return;
        }

        onType(model, type);
      }
    }
  }

  private void ifContinue(Runnable action) {
    if (!stopped) {
      action.run();
    }
  }

}
