/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;

/**
 * Contract interface for a {@link BaseDeclaration} in which it's possible
 * to get/set {@link OutputDeclaration} objects of payload and attributes.
 *
 * @since 1.0
 */
@NoImplement
public interface WithOutputDeclaration {

  /**
   * @return the {@link OutputDeclaration} that describes {@code this} declarations output type.
   */
  OutputDeclaration getOutput();

  /**
   * Sets the {@link OutputDeclaration} that describes {@code this} declarations output type.
   */
  void setOutput(OutputDeclaration content);

  /**
   * @return the {@link OutputDeclaration} that describes {@code this} declarations output attributes type.
   */
  OutputDeclaration getOutputAttributes();

  /**
   * Sets the {@link OutputDeclaration} that describes {@code this} declarations output attributes type.
   */
  void setOutputAttributes(OutputDeclaration outputAttributes);
}
