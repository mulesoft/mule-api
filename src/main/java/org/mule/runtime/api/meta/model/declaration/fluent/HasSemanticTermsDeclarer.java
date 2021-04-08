/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;

/**
 * Contract interface for a declarer in which it's possible to add semantic terms which describe
 * the declaration's meaning and effect
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
