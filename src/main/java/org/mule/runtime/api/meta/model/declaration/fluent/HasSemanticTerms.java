/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static java.util.Collections.emptySet;

import org.mule.api.annotation.NoImplement;

import java.util.Set;

/**
 * Contract interface for a declarer in which it's possible to add semantic terms
 *
 * @since 1.4.0
 */
@NoImplement
public interface HasSemanticTerms {

  default Set<String> getSemanticTerms() {
    return emptySet();
  }
}
