/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.tls;

/**
 * Provides methods to access the configuration of a trust store.
 *
 * @since 1.0
 */
public interface TlsContextTrustStoreConfiguration
{

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
