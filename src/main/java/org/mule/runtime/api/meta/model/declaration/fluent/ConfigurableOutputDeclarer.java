/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

/**
 * Base class for a declarer which allows adding {@link OutputDeclaration}s to
 * a {@link ComponentDeclaration}
 *
 * @since 1.0
 */
public abstract class ConfigurableOutputDeclarer<D extends ComponentDeclaration> extends ParameterizedDeclarer<D> {

  public ConfigurableOutputDeclarer(D declaration) {
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
    return new OutputDeclarer<>(outputPayload);
  }

  /**
   * Declares element output
   *
   * @return a new {@link OutputDeclarer}
   */
  public OutputDeclarer withOutputAttributes() {
    OutputDeclaration outputAttributes = new OutputDeclaration();
    declaration.setOutputAttributes(outputAttributes);
    return new OutputDeclarer<>(outputAttributes);
  }

}
