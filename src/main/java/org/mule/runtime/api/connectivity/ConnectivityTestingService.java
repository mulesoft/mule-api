/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.connectivity;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.component.location.Location;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.exception.ObjectNotFoundException;

/**
 * Service for doing connectivity testing.
 *
 * A {@code ConnectivityTestingService}
 *
 * @since 4.0
 * @deprecated Used only by fat-tooling, since 4.10 use the mule-framework instead.
 */
@NoImplement
@Deprecated // (since = "4.10", forRemoval = true)
public interface ConnectivityTestingService {

  /**
   * Key under which the {@link ConnectivityTestingService} can be found in the {@link org.mule.runtime.api.artifact.Registry}
   */
  String CONNECTIVITY_TESTING_SERVICE_KEY = "_muleConnectivityTestingService";

  /**
   * Does connection testing over the component registered under the provider identifier
   *
   * @param location component identifier over the one connectivity testing is done.
   * @return connectivity testing result.
   * @throws UnsupportedConnectivityTestingObjectException when it's not possible to do connectivity testing over the mule
   *                                                       component.
   * @throws {@link                                        ObjectNotFoundException} when the object to use to do connectivity does
   *                                                       not exists
   */
  ConnectionValidationResult testConnection(Location location);

}
