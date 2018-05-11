/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Mule API I/O Utilities.
 * @since 1.2.0
 */
public class IOUtils {

  /**
   * This method wraps {@link org.apache.commons.io.IOUtils}' <code>toByteArray(InputStream)</code> method but catches any
   * {@link IOException} and wraps it into a {@link RuntimeException}.
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
}
