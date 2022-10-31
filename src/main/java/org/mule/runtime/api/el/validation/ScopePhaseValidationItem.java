/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el.validation;

import org.mule.api.annotation.NoImplement;

import java.util.Map;

/**
 * Represents a scope phase validation item
 *
 * @since 1.5.0
 */
@NoImplement
public interface ScopePhaseValidationItem {

  /**
   * @return The kind of the scope validation item. It may be null if it does not exist a proper
   *         {@link ScopePhaseValidationItemKind} kind value to map from the expression language.
   */
  ScopePhaseValidationItemKind getKind();

  /**
   * @return The validation message
   */
  String getMessage();

  /**
   * @return A key-value pairs of parameter relative to the validation item
   */
  Map<String, String> getParams();

  /**
   * @return The {@link Location} location of validation item
   */
  Location getLocation();
}
