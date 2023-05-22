/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.deployment.functional;

import static org.mule.runtime.api.functional.Either.left;
import static org.mule.runtime.api.functional.Either.right;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.mule.runtime.api.functional.Either;

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

  @Test
  public void mapRightOnLeftDoesntCallMapper() {
    left("").mapRight(r -> {
      fail("Must not call mapper");
      return r;
    });
  }

  @Test
  public void mapLeftOnRightDoesntCallMapper() {
    right("").mapLeft(l -> {
      fail("Must not call mapper");
      return l;
    });
  }

  @Test
  public void applyRightOnLeftDoesntCallConsumer() {
    left("").applyRight(r -> {
      fail("Must not call consumer");
    });
  }

  @Test
  public void applyLeftOnRightDoesntCallConsumer() {
    right("").applyLeft(l -> {
      fail("Must not call consumer");
    });
  }

  @Test
  public void applyOnLeftDoesntCallRightConsumer() {
    left("").apply(l -> {
    }, r -> {
      fail("Must not call right consumer");
    });
  }

  @Test
  public void applyOnRightDoesntCallLeftConsumer() {
    right("").apply(l -> {
      fail("Must not call left consumer");
    }, r -> {
    });
  }

  @Test
  public void reduceOnLeftDoesntCallRightFunction() {
    left("", String.class).reduce(l -> l, r -> {
      fail("Must not call right function");
      return r;
    });
  }

  @Test
  public void reduceOnRightDoesntCallLeftFunction() {
    right(String.class, "").reduce(l -> {
      fail("Must not call left function");
      return l;
    }, r -> r);
  }

  @Test
  public void leftToString() {
    assertThat(left("hello").toString(), equalTo("Either - left: { hello }"));
  }

  @Test
  public void rightToString() {
    assertThat(right("world").toString(), equalTo("Either - right: { world }"));
  }

  @Test
  public void emptyToString() {
    assertThat(Either.empty().toString(), equalTo("Either - empty"));
  }
}
