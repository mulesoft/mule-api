/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;


import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.deprecated.DeprecatedModel;

import java.util.Optional;

/**
 * Contract interface for a {@link BaseDeclaration} in which it's possible to add/get {@link DeprecatedModel} objects
 *
 * @since 1.2
 */
@NoImplement
public interface WithDeprecatedDeclaration {

  /**
   * @return {@code this} components {@link DeprecatedModel}
   */
  Optional<DeprecatedModel> getDeprecation();

  /**
   * @param deprecation {@code this} components {@link DeprecatedModel}
   */
  void withDeprecation(DeprecatedModel deprecation);

}
