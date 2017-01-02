/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.stream.bytes;

import static java.lang.Long.MIN_VALUE;
import static java.lang.Math.abs;
import static java.lang.String.format;
import static java.util.concurrent.Executors.newFixedThreadPool;

import java.io.File;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ExecutorService;

final class TempFileHelper {

  private static final String TEMP_DIR_SYSTEM_PROPERTY = "java.io.tmpdir";
  private static final File TEMP_DIR = new File(System.getProperty(TEMP_DIR_SYSTEM_PROPERTY));
  private static Random RANDOM = new SecureRandom();

  //TODO: this cannot be static
  private static final ExecutorService asyncService = newFixedThreadPool(1);

  static File createBufferFile(String name) {
    return createTempFile("mule-buffer-${" + name + "}-", ".tmp");
  }

  static File createTempFile(String prefix, String suffix) throws RuntimeException {
    long n = RANDOM.nextLong();
    if (n == MIN_VALUE) {
      n = 0;
    } else {
      n = abs(n);
    }

    if (!TEMP_DIR.exists()) {
      throw new RuntimeException(format("Temp directory '%s' does not exits. Please check the value of the '%s' system property.",
                                        TEMP_DIR.getAbsolutePath(),
                                        TEMP_DIR_SYSTEM_PROPERTY));
    }
    return new File(TEMP_DIR, prefix + n + suffix);
  }

  public static void deleteAsync(File file) {
    asyncService.submit(file::delete);
  }

  private TempFileHelper() {}
}
