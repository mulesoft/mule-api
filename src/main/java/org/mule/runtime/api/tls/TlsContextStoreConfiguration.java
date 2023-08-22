/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.tls;

import org.mule.api.annotation.NoImplement;

/**
 * Provides methods to access the configuration of a store.
 *
 * @since 1.0
 */
@NoImplement
public interface TlsContextStoreConfiguration {

  /**
   * @return The location of the store.
   */
  String getPath();

  /**
   * @return The password to access the store.
   */
  String getPassword();

  /**
   * @return The type of store ("jks", "pkcs12", "jceks", or any other).
   */
  String getType();

  /**
   * @return The algorithm used by the store.
   */
  String getAlgorithm();

}
