/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.meta.model.OutputModel;

/**
 * A declaration object for an {@link OutputModel}. It contains raw, unvalidated data which is used to declare the structure of an
 * {@link OutputModel}.
 *
 * @since 1.0
 */
public class OutputDeclaration extends BaseDeclaration<OutputDeclaration> implements TypedDeclaration {

  private MetadataType type;
  private boolean hasDynamicType;

  /**
   * {@inheritDoc}
   */
  @Override
  public void setType(MetadataType type, boolean isDynamic) {
    this.type = type;
    this.hasDynamicType = isDynamic;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MetadataType getType() {
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasDynamicType() {
    return hasDynamicType;
  }
}
