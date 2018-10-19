/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.deprecated.DeprecatedModel;
import org.mule.runtime.api.meta.model.operation.OperationModel;

import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * A declaration object for a {@link OperationModel}. It contains raw, unvalidated data which is used to declare the structure of
 * a {@link OperationModel}
 *
 * @since 1.0
 */
public class FunctionDeclaration extends ParameterizedDeclaration<FunctionDeclaration> implements WithDeprecatedDeclaration {

  private OutputDeclaration output;
  private DeprecatedModel deprecation;

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
  public Optional<DeprecatedModel> getDeprecation() {
    return ofNullable(deprecation);
  }

  @Override
  public void withDeprecation(DeprecatedModel deprecation) {
    this.deprecation = deprecation;
  }
}
