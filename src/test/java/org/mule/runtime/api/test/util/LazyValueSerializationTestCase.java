/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.test.util;


import static org.apache.commons.lang3.SerializationUtils.deserialize;
import static org.apache.commons.lang3.SerializationUtils.serialize;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.rules.ExpectedException.none;

import org.mule.runtime.api.util.LazyValue;
import org.mule.runtime.api.util.SerializableLazyValue;

import java.io.Serializable;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LazyValueSerializationTestCase {

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
