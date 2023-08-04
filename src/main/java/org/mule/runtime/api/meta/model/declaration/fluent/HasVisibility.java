/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.ComponentVisibility;

/**
 * Contract interface for a declarer in which it's possible to set visibility to third partys
 *
 * @since 1.5.0
 */
@NoImplement
public interface HasVisibility<T> {

  /**
   * Adds the given {@code visibility}
   *
   * @param visibility a {@link ComponentVisibility}
   * @return {@code this} declarer
   */
  T withVisibility(ComponentVisibility visibility);

}
