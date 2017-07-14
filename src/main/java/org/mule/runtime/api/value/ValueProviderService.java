/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.value;

import org.mule.runtime.api.component.location.Location;

/**
 * Provides the capability of resolve the {@link Value values} for any Value Provider capable element in a Mule App.
 *
 * @since 1.0
 */
public interface ValueProviderService {

  /**
   * Resolves the {@link Value values} for a element's value provider located in the given {@link Location}.
   *
   * @param location     The {@link Location} where the element is found.
   * @param providerName The name of the value provider to resolve the {@link Value values}.
   * @return the {@link ValueResult result} of the resolving of the values.
   * @see ValueResult
   */
  ValueResult getValues(Location location, String providerName);

}
