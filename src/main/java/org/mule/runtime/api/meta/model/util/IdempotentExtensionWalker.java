/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.util;

import org.mule.runtime.api.meta.model.connection.ConnectionProviderModel;
import org.mule.runtime.api.meta.model.connection.HasConnectionProviderModels;
import org.mule.runtime.api.meta.model.construct.ConstructModel;
import org.mule.runtime.api.meta.model.construct.HasConstructModels;
import org.mule.runtime.api.meta.model.function.FunctionModel;
import org.mule.runtime.api.meta.model.function.HasFunctionModels;
import org.mule.runtime.api.meta.model.operation.HasOperationModels;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.parameter.ParameterGroupModel;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;
import org.mule.runtime.api.meta.model.source.HasSourceModels;
import org.mule.runtime.api.meta.model.source.SourceModel;
import org.mule.runtime.api.util.Reference;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A {@link ExtensionWalker} which assures that each component is visited only once, making it easy to handle the fact that some
 * components such as {@link OperationModel}, {@link SourceModel}, {@link ConnectionProviderModel}, etc, implement the flyweight
 * pattern, which means that the same instance might be present at different levels.
 * <p>
 * The use of this walker makes it unnecessary to manually control if a given component has already been seen.
 *
 * @since 1.0
 */
public abstract class IdempotentExtensionWalker extends ExtensionWalker {

  private Set<Reference<SourceModel>> sources = new HashSet<>();
  private Set<Reference<ParameterModel>> parameters = new HashSet<>();
  private Set<Reference<ParameterGroupModel>> parameterGroups = new HashSet<>();
  private Set<Reference<OperationModel>> operations = new HashSet<>();
  private Set<Reference<FunctionModel>> functions = new HashSet<>();
  private Set<Reference<ConstructModel>> constructs = new HashSet<>();
  private Set<Reference<ConnectionProviderModel>> connectionProviders = new HashSet<>();

  private <T> boolean isFirstAppearance(Set<Reference<T>> accumulator, T item) {
    return accumulator.add(new Reference<>(item));
  }

  @Override
  protected final void onSource(HasSourceModels owner, SourceModel model) {
    doOnce(sources, model, this::onSource);
  }

  @Override
  protected void onParameterGroup(ParameterizedModel owner, ParameterGroupModel model) {
    doOnce(parameterGroups, model, group -> onParameterGroup(owner, group));
  }

  @Override
  protected void onParameter(ParameterizedModel owner, ParameterGroupModel groupModel, ParameterModel model) {
    doOnce(parameters, model, p -> onParameter(groupModel, p));
  }

  @Override
  protected final void onOperation(HasOperationModels owner, OperationModel model) {
    doOnce(operations, model, this::onOperation);
  }

  @Override
  protected final void onFunction(HasFunctionModels owner, FunctionModel model) {
    doOnce(functions, model, this::onFunction);
  }

  @Override
  protected final void onConstruct(HasConstructModels owner, ConstructModel model) {
    doOnce(constructs, model, this::onConstruct);
  }

  @Override
  protected final void onConnectionProvider(HasConnectionProviderModels owner, ConnectionProviderModel model) {
    doOnce(connectionProviders, model, this::onConnectionProvider);
  }

  private <T> void doOnce(Set<Reference<T>> accumulator, T item, Consumer<T> delegate) {
    if (isFirstAppearance(accumulator, item)) {
      delegate.accept(item);
    }
  }

  /**
   * Invoked when an {@link ConnectionProviderModel} is found in the traversed {@code extensionModel}.
   * <p>
   * This method will only be invoked once per each found instance
   *
   * @param model the {@link ConnectionProviderModel}
   */
  protected void onConnectionProvider(ConnectionProviderModel model) {}

  /**
   * Invoked when an {@link SourceModel} is found in the traversed {@code extensionModel}.
   * <p>
   * This method will only be invoked once per each found instance
   *
   * @param model the {@link SourceModel}
   */
  protected void onSource(SourceModel model) {}

  /**
   * Invoked when an {@link ConstructModel} is found in the traversed {@code extensionModel}.
   * <p>
   * This method will only be invoked once per each found instance
   *
   * @param model the {@link ConstructModel}
   */
  protected void onConstruct(ConstructModel model) {}

  /**
   * Invoked when an {@link ParameterModel} is found in the traversed {@code extensionModel}.
   * <p>
   * This method will only be invoked once per each found instance
   *
   * @param groupModel the {@link ParameterGroupModel} on which the {@code model} is contained
   * @param model      the {@link ParameterModel}
   */
  protected void onParameter(ParameterGroupModel groupModel, ParameterModel model) {}

  /**
   * Invoked when an {@link ParameterGroupModel} is found in the traversed {@code extensionModel}.
   * <p>
   * This method will only be invoked once per each found instance
   *
   * @param model the {@link ParameterModel}
   */
  protected void onParameterGroup(ParameterGroupModel model) {}

  /**
   * Invoked when an {@link OperationModel} is found in the traversed {@code extensionModel}.
   * <p>
   * This method will only be invoked once per each found instance
   *
   * @param model the {@link OperationModel}
   */
  protected void onOperation(OperationModel model) {}

  /**
   * Invoked when an {@link FunctionModel} is found in the traversed {@code extensionModel}.
   * <p>
   * This method will only be invoked once per each found instance
   *
   * @param model the {@link FunctionModel}
   */
  protected void onFunction(FunctionModel model) {}

}
