/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LazyValueTestCase {

  private LazyValue<Object> lazy;

  @Test
  public void computeOnlyOnce() {
    lazy = new LazyValue<>(Object::new);
    Object value = lazy.get();
    assertThat(lazy.get(), is(sameInstance(value)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullSupplier() {
    new LazyValue<>(null);
  }
}
