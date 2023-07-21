/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
