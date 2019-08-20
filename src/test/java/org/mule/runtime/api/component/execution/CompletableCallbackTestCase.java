/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component.execution;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.mule.runtime.api.util.Reference;

import org.junit.Test;

public class CompletableCallbackTestCase {

  private Reference<Object> completeReference = new Reference<>();
  private Reference<Throwable> errorReference = new Reference<>();

  private CompletableCallback<Object> callback = new CompletableCallback<Object>() {

    @Override
    public void complete(Object value) {
      completeReference.set(value);
    }

    @Override
    public void error(Throwable e) {
      errorReference.set(e);
    }
  };

  @Test
  public void andThen() {
    Reference<Integer> enrichedValue = new Reference<>();
    Reference<Throwable> enrichedError = new Reference<>();

    CompletableCallback<Object> andThen = callback.andThen(new CompletableCallback<Object>() {

      @Override
      public void complete(Object value) {
        enrichedValue.set(((Integer) value) + 1);
      }

      @Override
      public void error(Throwable e) {
        enrichedError.set(new RuntimeException(e));
      }
    });

    andThen.complete(0);
    assertThat(completeReference.get(), is(0));
    assertThat(enrichedValue.get(), is(1));

    Exception e = new Exception();
    andThen.error(e);

    assertThat(errorReference.get(), is(sameInstance(e)));
    assertThat(enrichedError.get(), is(instanceOf(RuntimeException.class)));
    assertThat(enrichedError.get().getCause(), is(sameInstance(e)));
  }
}
