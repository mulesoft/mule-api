/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.component.execution;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.mule.runtime.api.component.execution.CompletableCallback;
import org.mule.runtime.api.util.Reference;

import org.junit.Test;

public class CompletableCallbackTestCase {

  private Reference<Integer> completeReference = new Reference<>();
  private Reference<Throwable> errorReference = new Reference<>();
  private int executionIndex = 0;

  private CompletableCallback<Integer> callback = new CompletableCallback<Integer>() {

    @Override
    public void complete(Integer value) {
      completeReference.set(value + executionIndex++);
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

    CompletableCallback<Integer> andThen = callback.andThen(new CompletableCallback<Integer>() {

      @Override
      public void complete(Integer value) {
        enrichedValue.set(((Integer) value) + executionIndex++);
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

  @Test
  public void andThenConsume() {
    Reference<Integer> enrichedValue = new Reference<>();

    CompletableCallback<Integer> andThen = callback.andThen(v -> enrichedValue.set(v + executionIndex++));

    andThen.complete(0);
    assertThat(completeReference.get(), is(0));
    assertThat(enrichedValue.get(), is(1));
  }

  @Test
  public void before() {
    Reference<Integer> enrichedValue = new Reference<>();
    Reference<Throwable> enrichedError = new Reference<>();

    CompletableCallback<Integer> before = callback.before(new CompletableCallback<Integer>() {

      @Override
      public void complete(Integer value) {
        enrichedValue.set(value + executionIndex++);
      }

      @Override
      public void error(Throwable e) {
        enrichedError.set(new RuntimeException(e));
      }
    });

    before.complete(0);
    assertThat(completeReference.get(), is(1));
    assertThat(enrichedValue.get(), is(0));

    Exception e = new Exception();
    before.error(e);

    assertThat(errorReference.get(), is(sameInstance(e)));
    assertThat(enrichedError.get(), is(instanceOf(RuntimeException.class)));
    assertThat(enrichedError.get().getCause(), is(sameInstance(e)));
  }

  @Test
  public void beforeConsume() {
    Reference<Integer> enrichedValue = new Reference<>();

    CompletableCallback<Integer> before = callback.before(value -> enrichedValue.set(value + executionIndex++));

    before.complete(0);
    assertThat(completeReference.get(), is(1));
    assertThat(enrichedValue.get(), is(0));
  }

  @Test
  public void always() {
    CompletableCallback<Integer> always = CompletableCallback.always(() -> executionIndex++);

    always.complete(0);
    assertThat(executionIndex, is(1));
    always.error(new Exception());
    assertThat(executionIndex, is(2));
  }

  @Test
  public void finallyBefore() {
    CompletableCallback<Integer> finallyBefore = callback.finallyBefore(() -> executionIndex++);

    finallyBefore.complete(0);
    assertThat(completeReference.get(), is(1));
    assertThat(executionIndex, is(2));

    Exception e = new Exception();
    finallyBefore.error(e);

    assertThat(errorReference.get(), is(sameInstance(e)));
    assertThat(executionIndex, is(3));
  }

  @Test
  public void finallyAfter() {
    CompletableCallback<Integer> finallyAfter = callback.finallyAfter(() -> executionIndex++);

    finallyAfter.complete(0);
    assertThat(completeReference.get(), is(0));
    assertThat(executionIndex, is(2));

    Exception e = new Exception();
    finallyAfter.error(e);

    assertThat(errorReference.get(), is(sameInstance(e)));
    assertThat(executionIndex, is(3));
  }
}
