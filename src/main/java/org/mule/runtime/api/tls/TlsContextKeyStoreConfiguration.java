/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
