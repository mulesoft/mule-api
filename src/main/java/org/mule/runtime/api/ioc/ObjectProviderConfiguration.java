/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.ioc;

import org.mule.runtime.api.component.ConfigurationProperties;

/**
 * Configuration for the {@link ObjectProvider}
 * 
 * @since 1.0
 */
public interface ObjectProviderConfiguration {

  /**
   * {@link ObjectProvider} implementation for objects of the mule runtime.
   * 
   * @return {@link ObjectProvider} implementation for objects of the mule runtime.
   */
  ObjectProvider getArtifactObjectProvider();

  /**
   * Configuration properties defined by the user in the mule artifact.
   * <p/>
   * It may be made available to be used in the configuration of the objects defined in this provider
   *
   * @return the configuration properties of the artifact.
   */
  ConfigurationProperties getConfigurationProperties();

}
