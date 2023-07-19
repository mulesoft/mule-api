/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;


import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.deprecated.DeprecationModel;

import java.util.Optional;

/**
 * Contract interface for a {@link BaseDeclaration} in which it's possible to add/get {@link DeprecationModel} objects
 *
 * @since 1.2
 */
@NoImplement
public interface WithDeprecatedDeclaration {

  /**
   * @return {@code this} components {@link DeprecationModel}
   */
  Optional<DeprecationModel> getDeprecation();

  /**
   * @param deprecation {@code this} components {@link DeprecationModel}
   */
  void withDeprecation(DeprecationModel deprecation);

}
