/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.tls;

import org.mule.api.annotation.NoImplement;

/**
 * Connectors that implement this interface allow to enable/disable TLS on-the-fly using the provided TLS context
 * factory. Only one TLS context factory is allowed to be configured at any one time. If you re-enable TLS for
 * a connector that already has TLS enabled it will override the previous TLS context factory without the possiblity
 * to rollback unless you have a reference to it.
 *
 * @since 4.2
 */
@NoImplement
public interface DynamicTlsProvider {

  /**
   * Enable TLS dynamically on this server using the supplied TLS context factory
   *
   * @param tlsContextFactory The TLS context factory to be used when dynamically enabled TLS at this server
   */
  void enableTls(TlsContextFactory tlsContextFactory);

  /**
   * Disable TLS dynamically. It should work for when the TLS was dynamically enabled or even when the TLS
   * was statically configured in the listener configuration section of the application.
   */
  void disableTls();
}
