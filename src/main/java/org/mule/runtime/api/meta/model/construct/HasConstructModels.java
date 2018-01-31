/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
   * Each construct is guaranteed to have a unique name which will not
   * overlap with any {@link OperationModel}, {@link ConnectionProviderModel} nor {@link SourceModel}
   * defined at any level.
   *
   * @return an immutable {@link List} of {@link ConstructModel}
   */
  List<ConstructModel> getConstructModels();

  /**
   * Returns the {@link ConstructModel} that matches
   * the given name.
   *
   * @param name case sensitive operation name
   * @return an {@link Optional} {@link ConstructModel}
   */
  Optional<ConstructModel> getConstructModel(String name);
}
