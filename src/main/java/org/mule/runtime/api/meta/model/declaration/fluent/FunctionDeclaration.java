/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;


import static java.util.Optional.ofNullable;

import org.mule.runtime.api.meta.model.deprecated.DeprecationModel;
import org.mule.runtime.api.meta.model.operation.OperationModel;

import java.util.Optional;

/**
 * A declaration object for a {@link OperationModel}. It contains raw, unvalidated data which is used to declare the structure of
 * a {@link OperationModel}
 *
 * @since 1.0
 */
public class FunctionDeclaration extends ParameterizedWithMinMuleVersionDeclaration<FunctionDeclaration>
    implements WithDeprecatedDeclaration {

  private OutputDeclaration output;
  private DeprecationModel deprecation;

  /**
   * {@inheritDoc}
   */
  public FunctionDeclaration(String name) {
    super(name);
  }

  public OutputDeclaration getOutput() {
    return output;
  }

  public void setOutput(OutputDeclaration output) {
    this.output = output;
  }

  @Override
  public Optional<DeprecationModel> getDeprecation() {
    return ofNullable(deprecation);
  }

  @Override
  public void withDeprecation(DeprecationModel deprecation) {
    this.deprecation = deprecation;
  }
}
