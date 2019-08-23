/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component.execution;

import java.util.function.Consumer;

public interface CompletableCallback<T> {

  static <T> CompletableCallback<T> empty() {
    return new CompletableCallback<T>() {

      @Override
      public void complete(T value) {

      }

      @Override
      public void error(Throwable e) {

      }
    };
  }

  static <T> CompletableCallback<T> merely(Runnable runnable) {
    return CompletableCallback.<T>empty().finallyAfter(runnable);
  }

  static <T> CompletableCallback<T> from(Runnable runnable) {
    return new CompletableCallback<T>() {

      @Override
      public void complete(T value) {
        runnable.run();
      }

      @Override
      public void error(Throwable e) {
        runnable.run();
      }
    };
  }

  /**
   * Invoked when processing completed successfully. {@code null} is allowed.
   *
   * @param value the processing result
   */
  void complete(T value);

  /**
   * This method is invoked when the process fails to execute.
   *
   * @param e the exception found
   */
  void error(Throwable e);

  default CompletableCallback<T> andThen(Consumer<T> consumer) {
    return andThen(new CompletableCallback<T>() {

      @Override
      public void complete(T value) {
        consumer.accept(value);
      }

      @Override
      public void error(Throwable e) {

      }
    });
  }

  default CompletableCallback<T> andThen(CompletableCallback<T> afterCallback) {
    return new CompletableCallback<T>() {

      @Override
      public void complete(T value) {
        CompletableCallback.this.complete(value);
        afterCallback.complete(value);
      }

      @Override
      public void error(Throwable e) {
        CompletableCallback.this.error(e);
        afterCallback.error(e);
      }
    };
  }

  default CompletableCallback<T> before(Consumer<T> consumer) {
    return before(new CompletableCallback<T>() {

      @Override
      public void complete(T value) {
        consumer.accept(value);
      }

      @Override
      public void error(Throwable e) {

      }
    });
  }

  default CompletableCallback<T> before(CompletableCallback<T> beforeCallback) {
    return new CompletableCallback<T>() {

      @Override
      public void complete(T value) {
        beforeCallback.complete(value);
        CompletableCallback.this.complete(value);
      }

      @Override
      public void error(Throwable e) {
        beforeCallback.error(e);
        CompletableCallback.this.error(e);
      }
    };
  }

  default CompletableCallback<T> finallyBefore(Runnable runnable) {
    return before(new CompletableCallback<T>() {

      @Override
      public void complete(T value) {
        runnable.run();
      }

      @Override
      public void error(Throwable e) {
        runnable.run();
      }
    });
  }

  default CompletableCallback<T> finallyAfter(Runnable runnable) {
    return andThen(new CompletableCallback<T>() {

      @Override
      public void complete(T value) {
        runnable.run();
      }

      @Override
      public void error(Throwable e) {
        runnable.run();
      }
    });
  }
}
