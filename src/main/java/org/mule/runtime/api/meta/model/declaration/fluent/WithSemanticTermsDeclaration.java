/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
