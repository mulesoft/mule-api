/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.meta.MuleVersion;
import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.meta.model.deprecated.DeprecationModel;

/**
 * Allows configuring a {@link FunctionDeclaration} through a fluent API
 *
 * @since 1.0
 */
public class FunctionDeclarer extends ParameterizedDeclarer<FunctionDeclarer, FunctionDeclaration>
    implements HasModelProperties<FunctionDeclarer>,
    HasDeprecatedDeclarer<FunctionDeclarer>, HasMinMuleVersionDeclarer<FunctionDeclarer> {

  /**
   * Creates a new instance
   *
   * @param declaration the {@link FunctionDeclaration} which will be configured
   */
  FunctionDeclarer(FunctionDeclaration declaration) {
    super(declaration);
  }

  /**
   * Declares element output
   *
   * @return a new {@link OutputDeclarer}
   */
  public OutputDeclarer withOutput() {
    OutputDeclaration outputPayload = new OutputDeclaration();
    declaration.setOutput(outputPayload);
    return new OutputDeclarer<OutputDeclarer>(outputPayload) {

      @Override
      public OutputDeclarer ofDynamicType(MetadataType type) {
        throw new UnsupportedOperationException("Functions do not support dynamic types for their output.");
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FunctionDeclarer withModelProperty(ModelProperty modelProperty) {
    declaration.addModelProperty(modelProperty);
    return this;
  }

  @Override
  public FunctionDeclarer withDeprecation(DeprecationModel deprecation) {
    declaration.withDeprecation(deprecation);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FunctionDeclarer withMinMuleVersion(MuleVersion minMuleVersion) {
    declaration.withMinMuleVersion(minMuleVersion);
    return this;
  }
}
