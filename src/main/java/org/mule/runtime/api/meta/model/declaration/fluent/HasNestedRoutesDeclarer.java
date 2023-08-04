/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.nested.NestedRouteModel;

/**
 * A contract interface for a declarer capable of adding a {@link NestedRouteModel} as child
 *
 * @since 1.0
 */
@NoImplement
public interface HasNestedRoutesDeclarer {

  /**
   * Adds a component of the given {@code name}
   *
   * @param routeName a non blank name
   * @return a {@link NestedComponentDeclarer} which allows describing the created component
   */
  NestedRouteDeclarer withRoute(String routeName);

}
