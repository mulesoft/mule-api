/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
