/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;

import java.util.Set;

/**
 * Contract interface for a {@link BaseDeclaration} in which it's possible to add/get semantic terms which describe the
 * declaration's meaning and effect
 *
 * @since 1.4.0
 */
@NoImplement
public interface WithSemanticTermsDeclaration {

  Set<String> getSemanticTerms();

  /**
   * Adds the given {@code semanticTerm}
   *
   * @param semanticTerm a semantic term which describes the
   */
  void addSemanticTerm(String semanticTerm);

}
