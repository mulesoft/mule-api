/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.tls;

import org.mule.api.annotation.NoImplement;

/**
 * Provides methods to access the configuration of a key store.
 *
 * @since 1.0
 */
@NoImplement
public interface TlsContextKeyStoreConfiguration extends TlsContextStoreConfiguration {

  /**
   * @return The alias of the private key to use.
   */
  String getAlias();

  /**
   * @return The password used to access the private key.
   */
  String getKeyPassword();

}
