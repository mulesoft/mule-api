/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
