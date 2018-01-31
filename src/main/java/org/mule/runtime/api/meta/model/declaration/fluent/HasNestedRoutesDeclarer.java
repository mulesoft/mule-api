/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
