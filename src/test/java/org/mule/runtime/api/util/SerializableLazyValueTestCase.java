/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;


import static org.apache.commons.lang3.SerializationUtils.deserialize;
import static org.apache.commons.lang3.SerializationUtils.serialize;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.rules.ExpectedException.none;

import java.io.Serializable;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SerializableLazyValueTestCase {

  @Rule
  public ExpectedException expectedException = none();

  @Test
  public void serializeUninitialized() throws Exception {
    LazyValue<Integer> original = new SerializableLazyValue<Integer>(() -> 1);
    LazyValue processed = deserialize(serialize((Serializable) original));
    assertSerialization(original, processed);
  }

  @Test
  public void serializeInitialized() throws Exception {
    LazyValue<Integer> original = new SerializableLazyValue<Integer>(() -> 1);
    original.get();
    LazyValue processed = deserialize(serialize((Serializable) original));
    assertSerialization(original, processed);
  }

  private void assertSerialization(LazyValue original, LazyValue processed) {
    assertThat(original.isComputed(), is(equalTo(processed.isComputed())));
    assertThat(original.get(), is(equalTo(processed.get())));
  }
}
