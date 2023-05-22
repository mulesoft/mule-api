/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.mule.runtime.internal.util.StringByteSizeCalculator;

import java.nio.charset.Charset;

import org.junit.Test;

public class StringByteSizeCalculatorTestCase {

  private static final String JAPANESE_MESSAGE = "\u3042 \u3047";

  private StringByteSizeCalculator calculator = new StringByteSizeCalculator();

  @Test
  public void matchesGetBytesDefaultCharset() {
    compare("test", Charset.defaultCharset());
  }

  @Test
  public void matchesGetBytesCustomCharset() {
    compare(JAPANESE_MESSAGE, Charset.forName("EUC-JP"));
  }

  private void compare(String payload, Charset charset) {
    assertThat(calculator.count(payload, charset), equalTo((long) payload.getBytes(charset).length));
  }

}
