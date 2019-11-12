/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.functional;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class EitherTestCase {

  @Test
  public void reduceEmptyEither() {
    assertThat(Either.empty().reduce(l -> "l", r -> "r"), nullValue());
  }

  @Test
  public void reduceEmptyEitherBuiltLeft() {
    assertThat(Either.left(null).reduce(l -> "l", r -> "r"), nullValue());
  }

  @Test
  public void reduceEmptyEitherBuiltRight() {
    assertThat(Either.right(null).reduce(l -> "l", r -> "r"), nullValue());
  }
}
