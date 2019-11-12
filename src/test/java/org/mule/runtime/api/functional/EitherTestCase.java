/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.functional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mule.runtime.api.functional.Either.left;
import static org.mule.runtime.api.functional.Either.right;

import org.junit.Test;

public class EitherTestCase {

  @Test
  public void getValueLeftEither() {
    assertThat(left(1).getValue(), equalTo(of(1)));
  }

  @Test
  public void getValueRightEither() {
    assertThat(right(2).getValue(), equalTo(of(2)));
  }

  @Test
  public void getValueEmptyEither() {
    assertThat(Either.empty().getValue(), equalTo(empty()));
  }

  @Test
  public void getValueEmptyEitherBuiltLeft() {
    assertThat(left(null).getValue(), equalTo(empty()));
  }

  @Test
  public void getValueEmptyEitherBuiltRight() {
    assertThat(right(null).getValue(), equalTo(empty()));
  }

  @Test
  public void reduceLeftEither() {
    assertThat(left(1).reduce(l -> "l", r -> "r"), equalTo("l"));
  }

  @Test
  public void reduceRightEither() {
    assertThat(right(2).reduce(l -> "l", r -> "r"), equalTo("r"));
  }

  @Test
  public void reduceEmptyEither() {
    assertThat(Either.empty().reduce(l -> "l", r -> "r"), nullValue());
  }

  @Test
  public void reduceEmptyEitherBuiltLeft() {
    assertThat(left(null).reduce(l -> "l", r -> "r"), nullValue());
  }

  @Test
  public void reduceEmptyEitherBuiltRight() {
    assertThat(right(null).reduce(l -> "l", r -> "r"), nullValue());
  }
}
