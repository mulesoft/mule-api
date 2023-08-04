/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
public class FunctionDeclarer extends ParameterizedWithMinMuleVersionDeclarer<FunctionDeclarer, FunctionDeclaration>
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
