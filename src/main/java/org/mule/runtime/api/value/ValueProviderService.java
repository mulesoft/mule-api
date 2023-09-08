/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.value;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.component.location.Location;

/**
 * Provides the capability of resolve the {@link Value values} for any Value Provider capable element in a Mule App.
 *
 * @since 1.0
 */
@NoImplement
public interface ValueProviderService {

  /**
   * Key under which the {@link ValueProviderService} can be found in the {@link org.mule.runtime.api.artifact.Registry}
   */
  String VALUE_PROVIDER_SERVICE_KEY = "_muleValueProviderService";

  /**
   * Resolves the {@link Value values} for a element's value provider located in the given {@link Location}.
   *
   * @param location     The {@link Location} where the element is found.
   * @param providerName The name of the value provider to resolve the {@link Value values}.
   * @return the {@link ValueResult result} of the resolving of the values.
   * @see ValueResult
   */
  ValueResult getValues(Location location, String providerName);

  /**
   * Resolves the {@link Value values} for a element's value provider located in the given {@link Location} of the parameter field
   * with the given parameter name and using the given field target selector.
   *
   * @param location       The {@link Location} where the element is found.
   * @param parameter      The name of the parameter to resolve the {@link Value values}.
   * @param targetSelector The path to the field located in the parameter.
   * @return the {@link ValueResult result} of the resolving of the values.
   * @see ValueResult
   *
   * @since 1.4.0
   */
  ValueResult getFieldValues(Location location, String parameter, String targetSelector);
}
