/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.tls;

import org.mule.api.annotation.NoImplement;

/**
 * Provides methods to access the configuration of a trust store.
 *
 * @since 1.0
 */
@NoImplement
public interface TlsContextTrustStoreConfiguration extends TlsContextStoreConfiguration {

  /**
   * @return true if the trust store was configured and set as insecure, meaning no certificate validations will be performed.
   */
  boolean isInsecure();

}
