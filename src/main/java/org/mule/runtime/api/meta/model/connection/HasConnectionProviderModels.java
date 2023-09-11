/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.connection;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.source.SourceModel;

import java.util.List;
import java.util.Optional;

/**
 * A model which contains {@link ConnectionProviderModel} instances
 *
 * @since 1.0
 */
@NoImplement
public interface HasConnectionProviderModels {

  /**
   * Returns a {@link List} of {@link ConnectionProviderModel}s defined at the level of the component implementing this interface.
   *
   * Each provider is guaranteed to have a unique name which will not overlap with any {@link OperationModel} or
   * {@link SourceModel} defined in the same {@link ExtensionModel}
   *
   * @return an immutable {@link List} of {@link ConnectionProviderModel}
   */
  List<ConnectionProviderModel> getConnectionProviders();

  /**
   * Returns the {@link ConnectionProviderModel} that matches the given name.
   *
   * @param name case sensitive provider name
   * @return an {@link Optional} {@link ConnectionProviderModel}
   */
  Optional<ConnectionProviderModel> getConnectionProviderModel(String name);
}
