/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;

/**
 * Contract interface for a declarer in which it's possible to add semantic terms which describe the declaration's meaning and
 * effect
 *
 * @since 1.4.0
 */
@NoImplement
public interface HasSemanticTermsDeclarer<T> {

  /**
   * Adds the given {@code semanticTerm}
   *
   * @param semanticTerm a semantic term
   * @return {@code this} declarer
   */
  T withSemanticTerm(String semanticTerm);

}
