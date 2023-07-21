/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.construct;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.connection.ConnectionProviderModel;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.source.SourceModel;

import java.util.List;
import java.util.Optional;

/**
 * A model which contains {@link OperationModel}s
 *
 * @since 1.0
 */
@NoImplement
public interface HasConstructModels {

  /**
   * Returns a {@link List} of {@link ConstructModel}s defined at the extension level.
   * <p>
   * Each construct is guaranteed to have a unique name which will not overlap with any {@link OperationModel},
   * {@link ConnectionProviderModel} nor {@link SourceModel} defined at any level.
   *
   * @return an immutable {@link List} of {@link ConstructModel}
   */
  List<ConstructModel> getConstructModels();

  /**
   * Returns the {@link ConstructModel} that matches the given name.
   *
   * @param name case sensitive operation name
   * @return an {@link Optional} {@link ConstructModel}
   */
  Optional<ConstructModel> getConstructModel(String name);
}
