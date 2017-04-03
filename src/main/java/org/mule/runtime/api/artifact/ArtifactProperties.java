/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.artifact;

import java.util.Map;
import java.util.Set;

/**
 * Configuration properties of the application.
 * <p>
 * This class contains the set of application properties configured by the following mechanism: <lu>
 * <li>global properties configured in the mule configuration files</li>
 * <li>properties defined in spring property placeholder files</li>
 * <li>mule-artifact.properties file from the classpath root</li>
 * <li>java environment properties</li>
 * <li>java system variables</li> </lu>
 * <p>
 * After application startup, changes on environment properties or system properties will have no effect.
 * <p>
 * Access to the {@link ArtifactProperties} instance of the artifact can be obtained by dependency injection.
 *
 * @since 4.0
 */
public interface ArtifactProperties {

  /**
   * It returns the most specific property from the different properties scopes, in the following order: <lu>
   * <li>global properties configured in the mule configuration files</li>
   * <li>mule-artifact.properties file from the classpath root</li>
   * <li>java environment properties</li>
   * <li>java system variables</li> </lu>
   * 
   * @param key the property key
   * @param <T> the type of the return value
   * @param <K> the type of the key
   * @return
   */
  <T, K> T getProperty(K key);

  /**
   * @return the list of properties names.
   */
  Set<Object> getPropertyNames();

  /**
   * @return an immutable map with all the properties following the most specific scope rules defined by (@code getProperty}
   *         method.
   */
  Map<Object, Object> toImmutableMap();

}
