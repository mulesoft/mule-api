/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import org.mule.api.annotation.NoImplement;

import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

/**
 * Provides access to resources according to its calling object, even considering specific artifacts to search on.
 *
 * @since 1.2
 */
@NoImplement
public interface ResourceLocator {

  /**
   * Looks for a resource as in {@link #find(String, Object)} and returns it's {@link InputStream} if possible.
   *
   * @param resource the name of the resource to load
   * @param caller the calling object
   * @return a stream for this resource
   */
  Optional<InputStream> load(String resource, Object caller);

  /**
   * Looks for a resource based on the calling class classloader and the current context classloader.
   *
   * @param resource the name of the resource to load
   * @param caller the calling object
   * @return the {@link URL} for this resource
   */
  Optional<URL> find(String resource, Object caller);

  /**
   *
   * @param resource the name of the resource to load
   * @param groupId the group ID of the artifact where to look
   * @param artifactId the artifact ID of the artifact where to look
   * @param version the optional version of the artifact where to look
   * @param caller the calling object
   * @return a stream for this resource
   */
  Optional<InputStream> loadFrom(String resource, String groupId, String artifactId, Optional<String> version, Object caller);

  /**
   *
   * @param resource the name of the resource to load
   * @param groupId the group ID of the artifact where to look
   * @param artifactId the artifact ID of the artifact where to look
   * @param version the optional version of the artifact where to look
   * @param caller the calling object
   * @return the {@link URL} for this resource
   */
  Optional<URL> findIn(String resource, String groupId, String artifactId, Optional<String> version, Object caller);

}
