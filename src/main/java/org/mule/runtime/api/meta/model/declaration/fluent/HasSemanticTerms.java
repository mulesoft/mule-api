/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
