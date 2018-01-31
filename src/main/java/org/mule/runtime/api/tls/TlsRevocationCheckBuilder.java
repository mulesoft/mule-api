/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.tls;

import org.mule.api.annotation.NoImplement;

/**
 * Factory for creating currently supported certificate revocation mechanisms.
 *
 * @since 4.1
 */
@NoImplement
public interface TlsRevocationCheckBuilder {

  /**
   * Configures a revocation checking mechanism based on standard CRLDP and OCSP extension points in the certificate.
   *
   * @param onlyEndEntities only verify last element of the certificate chain, false by default
   * @param preferCrls try CRL instead of OCSP first, false by default
   * @param noFallback do not use the secondary checking method (the one not selected before), false by default
   * @param softFail avoid verification failure when the revocation server can not be reached or is busy, false by default
   * @return the configured factory builder
   */
  TlsContextFactoryBuilder standard(boolean onlyEndEntities, boolean preferCrls, boolean noFallback, boolean softFail);

  /**
   * Configures a revocation checking implementation based on a custom OCSP responder.
   *
   * @param url the URL of the responder
   * @param certAlias alias of the signing certificate for the OCSP response (must be in the trust store), or null if not present
   * @return the configured factory builder
   */
  TlsContextFactoryBuilder customOcsp(String url, String certAlias);

  /**
   * Configures a revocation checking implementation based on a local CRL file.
   *
   * @param path the path to the CRL file
   * @return the configured factory builder
   */
  TlsContextFactoryBuilder crlFile(String path);
}
