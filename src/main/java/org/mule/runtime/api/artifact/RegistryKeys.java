/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.artifact;

/**
 * Defines constants used as keys of objects available through the {@link Registry}.
 * 
 * @since 1.6
 */
public interface RegistryKeys {

  /**
   * The name of the application as a {@link String}.
   */
  public static final String APP_NAME_PROPERTY = "app.name";

  /**
   * The name of the domain as a {@link String}.
   */
  public static final String DOMAIN_NAME_PROPERTY = "domain.name";

  /**
   * A basic scheduler config prepopulated with values from the configuration of the artifact.
   */
  public static final String OBJECT_SCHEDULER_BASE_CONFIG = "_muleSchedulerBaseConfig";

}
