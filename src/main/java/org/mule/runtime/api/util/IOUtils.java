/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static org.mule.runtime.internal.util.FileUtils.newFile;
import org.mule.runtime.internal.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mule API I/O Utilities.
 * 
 * @since 1.2.0
 */
public class IOUtils {

  private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

  /**
   * This method wraps {@link org.apache.commons.io.IOUtils}' <code>toByteArray(InputStream)</code> method but catches any
   * {@link IOException} and wraps it into a {@link RuntimeException}.
   * 
   * @param input
   * @return the byte array representation of the stream.
   */
  public static byte[] toByteArray(InputStream input) {
    try {
      return org.apache.commons.io.IOUtils.toByteArray(input);
    } catch (IOException iox) {
      throw new RuntimeException(iox);
    }
  }

  /**
   * Attempts to load a resource from the file system or from the classpath, in that order.
   *
   * @param resourceName The name of the resource to load
   * @param callingClass The Class object of the calling object
   * @return an URL to the resource or null if resource not found
   */
  public static URL getResourceAsUrl(final String resourceName, final Class callingClass) {
    return getResourceAsUrl(resourceName, callingClass, true, true);
  }

  /**
   * Attempts to load a resource from the file system or from the classpath, in that order.
   *
   * @param resourceName The name of the resource to load
   * @param callingClass The Class object of the calling object
   * @param tryAsFile - try to load the resource from the local file system
   * @param tryAsUrl - try to load the resource as a Url string
   * @return an URL to the resource or null if resource not found
   */
  public static URL getResourceAsUrl(final String resourceName, final Class callingClass, boolean tryAsFile, boolean tryAsUrl) {
    if (resourceName == null) {
      throw new IllegalArgumentException("resourceName cannot be null");
    }
    URL url = null;

    // Try to load the resource from the file system.
    if (tryAsFile) {
      try {
        File file = newFile(resourceName);
        if (file.exists()) {
          url = file.getAbsoluteFile().toURL();
        } else {
          logger.debug("Unable to load resource from the file system: " + file.getAbsolutePath());
        }
      } catch (Exception e) {
        logger.debug("Unable to load resource from the file system: " + e.getMessage());
      }
    }

    // Try to load the resource from the classpath.
    if (url == null) {
      try {
        url = (URL) AccessController.doPrivileged((PrivilegedAction) () -> ClassUtils.getResource(resourceName, callingClass));
        if (url == null) {
          logger.debug("Unable to load resource " + resourceName + " from the classpath");
        }
      } catch (Exception e) {
        logger.debug("Unable to load resource " + resourceName + " from the classpath: " + e.getMessage());
      }
    }

    if (url == null) {
      try {
        url = new URL(resourceName);
      } catch (MalformedURLException e) {
        // ignore
      }
    }
    return url;
  }

}
