/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.source;

import org.mule.runtime.api.meta.model.connection.ConnectionProviderModel;
import org.mule.runtime.api.meta.model.operation.OperationModel;

import java.util.List;
import java.util.Optional;

/**
 * A model which contains {@link SourceModel} instances
 *
 * @since 1.0
 */
public interface HasSourceModels {

  /**
   * Returns a {@link List} of {@link SourceModel}s defined at the level
   * of the component implementing this interface.
   * <p>
   * Each source is guaranteed to have a unique name which will not
   * overlap with any {@link OperationModel} or {@link ConnectionProviderModel}
   * defined at any level.
   *
   * @return an immutable {@link List} of {@link SourceModel}
   */
  List<SourceModel> getSourceModels();

  /**
   * Returns the {@link SourceModel} that matches
   * the given name.
   *
   * @param name case sensitive source name
   * @return an {@link Optional} {@link SourceModel}
   */
  Optional<SourceModel> getSourceModel(String name);
}
