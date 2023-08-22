/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.source;


import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.ComponentModelVisitor;
import org.mule.runtime.api.meta.model.ConnectableComponentModel;
import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.api.meta.model.notification.HasNotifications;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * A definition of a message source in an {@link ExtensionModel}.
 * <p>
 * Source models implement the flyweight pattern. This means that a given operation should only be represented by only one
 * instance of this class. Thus, if the same operation is contained by different {@link HasSourceModels} instances, then each of
 * those containers should reference the same operation model instance.
 *
 * @since 1.0
 */
@NoImplement
public interface SourceModel extends ConnectableComponentModel, HasNotifications {

  /**
   * @return Whether the declared source emits a response
   */
  boolean hasResponse();

  /**
   * Optionally returns a {@link SourceCallbackModel} which will listen for the values produced by the source owner each time it
   * successfully processes any of the generated messages.
   *
   * @return an {@link Optional} {@link SourceCallbackModel}
   */
  Optional<SourceCallbackModel> getSuccessCallback();

  /**
   * Optionally returns a {@link SourceCallbackModel} which will listen for errors thrown by the source owner each time it fails
   * to process any of the generated messages
   *
   * @return an {@link Optional} {@link SourceCallbackModel}
   */
  Optional<SourceCallbackModel> getErrorCallback();

  /**
   * Optionally returns a {@link SourceCallbackModel} which will listen for the results of every generated message. This callback
   * will be called after the {@link #getErrorCallback()} and {@link #getSuccessCallback()} as the last step to validate the
   * result of the flow processing.
   *
   * @return an {@link Optional} {@link SourceCallbackModel}
   */
  Optional<SourceCallbackModel> getTerminateCallback();

  /**
   * @return Whether the declared source should only run in the primary node when in cluster mode
   */
  boolean runsOnPrimaryNodeOnly();

  /**
   * Returns all the {@link ParameterModel} on all groups, including the ones declared on the success and error callbacks.
   * Parameters repeated among callbacks will be deduplicated.
   *
   * @return a flattened list of all the parameters in this model
   */
  @Override
  default List<ParameterModel> getAllParameterModels() {
    List<ParameterModel> all = new LinkedList<>();
    all.addAll(getParameterGroupModels().stream()
        .flatMap(g -> g.getParameterModels().stream())
        .collect(toList()));

    List<ParameterModel> callbackParameters = new LinkedList<>();
    getSuccessCallback().ifPresent(callback -> callbackParameters.addAll(callback.getAllParameterModels()));
    getErrorCallback().ifPresent(callback -> callbackParameters.addAll(callback.getAllParameterModels()));
    all.addAll(callbackParameters.stream().distinct().collect(toList()));

    return unmodifiableList(all);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  default void accept(ComponentModelVisitor visitor) {
    visitor.visit(this);
  }
}
