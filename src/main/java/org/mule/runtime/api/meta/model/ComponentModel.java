/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.deprecated.DeprecableModel;
import org.mule.runtime.api.meta.model.display.HasDisplayModel;
import org.mule.runtime.api.meta.model.error.ThrowsErrors;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;
import org.mule.runtime.api.meta.model.source.SourceModel;
import org.mule.runtime.api.meta.model.stereotype.HasStereotypeModel;

/**
 * A definition of a component in an {@link ExtensionModel}. This model groups all the common contracts between extension
 * components like {@link OperationModel}, {@link SourceModel}, etc.
 *
 * @since 1.0
 */
@NoImplement
public interface ComponentModel
    extends ParameterizedModel, ComposableModel, HasStereotypeModel, EnrichableModel, HasDisplayModel, ThrowsErrors,
    DeprecableModel {

  /**
   * Accepts a {@link ComponentModelVisitor}
   *
   * @param visitor a {@link ComponentModelVisitor}
   */
  void accept(ComponentModelVisitor visitor);
}
