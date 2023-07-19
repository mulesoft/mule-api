/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.declaration.fluent.HasSemanticTerms;
import org.mule.runtime.api.meta.model.deprecated.DeprecableModel;
import org.mule.runtime.api.meta.model.display.HasDisplayModel;
import org.mule.runtime.api.meta.model.error.ThrowsErrors;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;
import org.mule.runtime.api.meta.model.source.SourceModel;
import org.mule.runtime.api.meta.model.stereotype.HasStereotypeModel;
import org.mule.runtime.api.meta.model.version.HasMinMuleVersion;

/**
 * A definition of a component in an {@link ExtensionModel}. This model groups all the common contracts between extension
 * components like {@link OperationModel}, {@link SourceModel}, etc.
 *
 * @since 1.0
 */
@NoImplement
public interface ComponentModel
    extends ParameterizedModel, ComposableModel, HasStereotypeModel, EnrichableModel, HasDisplayModel, ThrowsErrors,
    DeprecableModel, HasSemanticTerms, HasMinMuleVersion {

  /**
   * Accepts a {@link ComponentModelVisitor}
   *
   * @param visitor a {@link ComponentModelVisitor}
   */
  void accept(ComponentModelVisitor visitor);

  /**
   * @return the {@link ComponentVisibility} corresponding to this ComponentModel
   */
  ComponentVisibility getVisibility();
}
