/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.operation;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.connection.ConnectionProviderModel;
import org.mule.runtime.api.meta.model.source.SourceModel;

import java.util.List;
import java.util.Optional;

/**
 * A model which contains {@link OperationModel}s
 *
 * @since 1.0
 */
@NoImplement
public interface HasOperationModels {

  /**
   * Returns a {@link List} of {@link OperationModel}s defined at the level of the component implementing this interface.
   *
   * Each operation is guaranteed to have a unique name which will not overlap with any {@link SourceModel} or
   * {@link ConnectionProviderModel} defined at any level.
   *
   * @return an immutable {@link List} of {@link OperationModel}
   */
  List<OperationModel> getOperationModels();

  /**
   * Returns the {@link OperationModel} that matches the given name.
   *
   * @param name case sensitive operation name
   * @return an {@link Optional} {@link OperationModel}
   */
  Optional<OperationModel> getOperationModel(String name);
}
