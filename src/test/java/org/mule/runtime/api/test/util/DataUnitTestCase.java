/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.util;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mule.runtime.api.util.DataUnit.BYTE;
import static org.mule.runtime.api.util.DataUnit.GB;
import static org.mule.runtime.api.util.DataUnit.KB;
import static org.mule.runtime.api.util.DataUnit.MB;

import org.junit.Test;

public class DataUnitTestCase {

  private static final int ONE = 1;
  private static final int ONE_KB = ONE * 1024;
  private static final int ONE_MB = ONE_KB * 1024;
  private static final int ONE_GB = ONE_MB * 1024;

  @Test
  public void testBtoB() {
    assertThat(BYTE.toBytes(ONE_GB), is(ONE_GB));
  }

  @Test
  public void testBtoKB() {
    assertThat(BYTE.toKB(ONE_GB), is(ONE_MB));
  }

  @Test
  public void testBtoMB() {
    assertThat(BYTE.toMB(ONE_GB), is(ONE_KB));
  }

  @Test
  public void testBtoGB() {
    assertThat(BYTE.toGB(ONE_GB), is(ONE));
  }

  @Test
  public void testKBtoB() {
    assertThat(KB.toBytes(ONE_MB), is(ONE_GB));
  }

  @Test
  public void testKBtoKB() {
    assertThat(KB.toKB(ONE_MB), is(ONE_MB));
  }

  @Test
  public void testKBtoMB() {
    assertThat(KB.toMB(ONE_MB), is(ONE_KB));
  }

  @Test
  public void testKBtoGB() {
    assertThat(KB.toGB(ONE_MB), is(ONE));
  }

  @Test
  public void testMBtoB() {
    assertThat(MB.toBytes(ONE_KB), is(ONE_GB));
  }

  @Test
  public void testMBtoKB() {
    assertThat(MB.toKB(ONE_KB), is(ONE_MB));
  }

  @Test
  public void testMBtoMB() {
    assertThat(MB.toMB(ONE_KB), is(ONE_KB));
  }

  @Test
  public void testMBtoGB() {
    assertThat(MB.toGB(ONE_KB), is(ONE));
  }

  @Test
  public void testGBtoB() {
    assertThat(GB.toBytes(ONE), is(ONE_GB));
  }

  @Test
  public void testGBtoKB() {
    assertThat(GB.toKB(ONE), is(ONE_MB));
  }

  @Test
  public void testGBtoMB() {
    assertThat(GB.toMB(ONE), is(ONE_KB));
  }

  @Test
  public void testGBtoGB() {
    assertThat(GB.toGB(ONE), is(ONE));
  }

}
