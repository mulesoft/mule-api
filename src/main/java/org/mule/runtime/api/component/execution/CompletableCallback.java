/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component.execution;

import java.util.function.Consumer;

/**
 * A generic callback for asynchronous processing. The callback is completed normally when the {@link #complete(Object)} method is
 * invoked, or completed exceptionally when {@link #error(Throwable)} is used instead. Per each instance, only one of those
 * methods should be called, and they should be called only once.
 * <p>
 * This interface also provides some utility methods for functional composition of several callbacks.
 * <p>
 * Support for void processes can be achieved using {@link Void} as the generic param {@code <T>} and using {@code null} when
 * invoking {@link #complete(Object)}
 *
 * @param <T> the generic type of the values that complete the callback
 * @since 1.3.0
 */
public interface CompletableCallback<T> {

  /**
   * Returns an empty callback which does nothing
   *
   * @param <T> the generic type of the callback
   * @return an empty callback which does nothing.
   */
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

  /**
   * Returns a new callback which always executes the given {@code runnable} upon completion, regardless of said completion being
   * normal or exceptional.
   *
   * @param runnable the task to run
   * @param <T>      the callback's generic type
   * @return a new callback
   */
  static <T> CompletableCallback<T> always(Runnable runnable) {
    return CompletableCallback.<T>empty().finallyAfter(runnable);
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

  /**
   * Returns a new composed callback which propagates the normal completion signal to the given {@code consumer} AFTER
   * {@code this} callback has finished executing its {@link #complete(Object)} method.
   * <p>
   * Notice that if {@code this} callback throws exceptions, then the {@code consumer} will never be invoked
   *
   * @param consumer the consumer to compose
   * @return a new composed callback
   */
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

  /**
   * Returns a new composed callback which propagates the completion signal to the given {@code afterCallback} AFTER {@code this}
   * callback has finished executing its {@link #complete(Object)} or {@link #error(Throwable)} methods.
   * <p>
   * Notice that if {@code this} callback throws exceptions, then the completion signal will never reach the {@code afterCallback}
   *
   * @param afterCallback the callback to compose
   * @return a new composed callback
   */
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

  /**
   * Returns a new composed callback which propagates the normal completion signal to the given {@code beforeCallback} BEFORE the
   * {@link #complete(Object)} method is executed on {@code this} callback.
   * <p>
   * Notice that if the {@code consumer} throws exception, the completion signal will never reach {@code this} callback.
   *
   * @param consumer the consumer to compose
   * @return a new composed callback
   */
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

  /**
   * Returns a new composed callback which propagates the completion signal to the given {@code beforeCallback} BEFORE the
   * {@link #complete(Object)} or {@link #error(Throwable)} methods are executed on {@code this} callback.
   * <p>
   * Notice that if the {@code beforeCallback} throws exception, the completion signal will never reach {@code this} callback.
   *
   * @param beforeCallback the callback to compose
   * @return a new composed callback
   */
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

  /**
   * Returns a new composed callback which always invokes the given {@code runnable} BEFORE {@code this} callback is completed
   * either normally or exceptionally.
   * <p>
   * Notice that if the {@code runnable} throws exception, the completion signal will never reach {@code this} callback.
   *
   * @param runnable the task to execute upon completion.
   * @return a new composed callback
   */
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

  /**
   * Returns a new composed callback which always invokes the given {@code runnable} AFTER {@code this} callback is completed
   * either normally or exceptionally.
   * <p>
   * Notice that if {@code this} callback throws exception, the {@code runnable} will never be invoked.
   *
   * @param runnable the task to execute upon completion.
   * @return a new composed callback
   */
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
