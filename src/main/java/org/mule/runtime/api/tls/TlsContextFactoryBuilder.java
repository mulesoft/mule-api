/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.tls;

/**
 * Builder for {@link TlsContextFactory}.
 *
 * @since 1.0
 */
public interface TlsContextFactoryBuilder {

  /**
   * @return a {@link TlsContextFactory} with the jvm default certificates.
   */
  TlsContextFactory buildDefault();
}
