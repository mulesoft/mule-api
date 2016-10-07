/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.util;

import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.api.meta.model.config.ConfigurationModel;
import org.mule.runtime.api.meta.model.connection.ConnectionProviderModel;
import org.mule.runtime.api.meta.model.connection.HasConnectionProviderModels;
import org.mule.runtime.api.meta.model.operation.HasOperationModels;
import org.mule.runtime.api.meta.model.operation.OperationModel;
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
 * about the {@link #onParameter(ParameterizedModel, ParameterModel)}, etc.
 *
 * @since 1.0
 */
public abstract class ExtensionWalker {

  /**
   * Navigates the given {@code extensionModel} and invokes the
   * other public method's in this class as the navigation
   * progresses
   *
   * @param extensionModel the model to navigate
   */
  public final void walk(ExtensionModel extensionModel) {
    if (extensionModel == null) {
      throw new IllegalArgumentException("Cannot walk a null model");
    }

    extensionModel.getConfigurationModels().forEach(model -> {
      onConfiguration(model);
      walkConnectionProviders(model);
      walkParameters(model);
      walkSources(model);
      walkOperations(model);
    });

    walkConnectionProviders(extensionModel);
    walkSources(extensionModel);
    walkOperations(extensionModel);
  }

  /**
   * Invoked when a {@link ConfigurationModel} is found in the
   * traversed {@code extensionModel}
   *
   * @param model a {@link ConfigurationModel}
   */
  public void onConfiguration(ConfigurationModel model) {}

  /**
   * Invoked when an {@link OperationModel} is found in the
   * traversed {@code extensionModel}
   *
   * @param owner The component that owns the operation
   * @param model the {@link OperationModel}
   */
  public void onOperation(HasOperationModels owner, OperationModel model) {}

  /**
   * Invoked when an {@link ConnectionProviderModel} is found in the
   * traversed {@code extensionModel}
   *
   * @param owner The component that owns the provider
   * @param model the {@link ConnectionProviderModel}
   */
  public void onConnectionProvider(HasConnectionProviderModels owner, ConnectionProviderModel model) {}

  /**
   * Invoked when an {@link SourceModel} is found in the
   * traversed {@code extensionModel}
   *
   * @param owner The component that owns the source
   * @param model the {@link SourceModel}
   */
  public void onSource(HasSourceModels owner, SourceModel model) {}

  /**
   * Invoked when an {@link ParameterModel} is found in the
   * traversed {@code extensionModel}
   *
   * @param owner The component that owns the parameter
   * @param model the {@link ParameterModel}
   */
  public void onParameter(ParameterizedModel owner, ParameterModel model) {}

  private void walkSources(HasSourceModels model) {
    model.getSourceModels().forEach(source -> {
      onSource(model, source);
      walkParameters(source);
    });
  }

  private void walkParameters(ParameterizedModel model) {
    model.getParameterModels().forEach(parameter -> onParameter(model, parameter));
  }

  private void walkConnectionProviders(HasConnectionProviderModels model) {
    model.getConnectionProviders().stream().forEach(provider -> {
      onConnectionProvider(model, provider);
      walkParameters(provider);
    });
  }

  private void walkOperations(HasOperationModels model) {
    model.getOperationModels().stream().forEach(operation -> {
      onOperation(model, operation);
      walkParameters(operation);
    });
  }
}
