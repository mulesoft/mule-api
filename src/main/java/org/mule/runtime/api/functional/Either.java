/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.functional;

import static java.lang.String.format;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This class allow to represent a type that holds two different values.
 * <p>
 * Only one value can be present at any given type.
 * <p>
 * This class can be used as a monad to interact and chain functions to be executed over the possible return values.
 * <p>
 * Most likely the left type represent an error or failure result and the right value represent a successful result.
 *
 * @param <L> the type of the left value
 * @param <R> the type of the right value
 *
 * @since 1.3
 */
public class Either<L, R> {

  /**
   * Common instance for {@code empty()}.
   */
  private static final Either<?, ?> EMPTY = new Either<>(null, null);

  /**
   * Creates an {@link Either} that doesn't have any left or right value.
   *
   * @return a singleton empty {@code Either instance}
   */
  public static <L, R> Either<L, R> empty() {
    return (Either<L, R>) EMPTY;
  }

  /**
   * Creates an {@code Either} with a left value.
   *
   * @param value the left value
   * @param <L>   the left value type
   * @param <R>   the right value type
   * @return the created {@code Either instance}
   */
  public static <L, R> Either<L, R> left(L value) {
    if (value == null) {
      return (Either<L, R>) EMPTY;
    }
    return new Either<>(value, null);
  }

  /**
   * Creates an {@code Either} with a left value and forcing a {@code class} for its empty right.
   * <p>
   * This is useful because it avoids the casting afterwards which can clutter the code and affect its readability.
   *
   * @param value the left value
   * @param <L>   the left value type
   * @param <R>   the right value type
   * @return the created {@code Either instance}
   */
  public static <L, R> Either<L, R> left(L value, Class<R> rightClass) {
    return left(value);
  }

  /**
   * Creates an {@code Either} with a right value.
   *
   * @param value the right value
   * @param <L>   the left value type
   * @param <R>   the right value type
   * @return the created {@code Either instance}
   */
  public static <L, R> Either<L, R> right(R value) {
    if (value == null) {
      return (Either<L, R>) EMPTY;
    }
    return new Either<>(null, value);
  }

  /**
   * Creates an {@code Either} with a right value and forcing a {@code class} for its empty left.
   * <p>
   * This is useful because it avoids the casting afterwards which can clutter the code and affect its readability.
   *
   * @param value the right value
   * @param <L>   the left value type
   * @param <R>   the right value type
   * @return the created {@code Either instance}
   */
  public static <L, R> Either<L, R> right(Class<L> leftClass, R value) {
    return right(value);
  }

  private final L left;
  private final R right;

  private Either(L l, R r) {
    left = l;
    right = r;
  }

  /**
   * Allows to reduce to a single value using left and right functions with the same return type.
   *
   * @param leftFunc  the function to apply to the left value
   * @param rightFunc the function to apply to the left value
   * @param <T>       the return type of the function.
   * @return the result of applying the function on left of right values, or {@code null} if none were set.
   */
  public <T> T reduce(Function<? super L, ? extends T> leftFunc, Function<? super R, ? extends T> rightFunc) {
    if (isLeft()) {
      return leftFunc.apply(left);
    } else {
      return isRight()
          ? rightFunc.apply(right)
          : null;
    }
  }

  /**
   * Allows to execute a function over the left value if it is present
   *
   * @param func the function to apply to the left value
   * @param <T>  the return type of the function.
   * @return a new {@code Either} created from the result of applying the function.
   */
  public <T> Either<T, R> mapLeft(Function<? super L, ? extends T> func) {
    if (left == null) {
      return (Either<T, R>) this;
    } else {
      return new Either<>(func.apply(left), right);
    }
  }

  public void applyLeft(Consumer<? super L> consumer) {
    if (left != null) {
      consumer.accept(left);
    }
  }

  public void applyRight(Consumer<? super R> consumer) {
    if (right != null) {
      consumer.accept(right);
    }
  }

  /**
   * Allows to execute a function over the right value if it is present
   *
   * @param func the function to apply to the right value
   * @param <T>  the return type of the function.
   * @return a new {@code Either} created from the result of applying the function.
   */
  public <T> Either<L, T> mapRight(Function<? super R, ? extends T> func) {
    if (right == null) {
      return (Either<L, T>) this;
    } else {
      return new Either<>(left, func.apply(right));
    }
  }

  /**
   * Receives a {@link Consumer} functions for both, the left and right value and applies the one over the value that is present.
   *
   * @param leftConsumer  the function to apply to the left value
   * @param rightConsumer the function to apply to the right value
   */
  public void apply(Consumer<? super L> leftConsumer, Consumer<? super R> rightConsumer) {
    applyLeft(leftConsumer);
    applyRight(rightConsumer);
  }

  /**
   * @return true if it holds a value for the left type, false otherwise
   */
  public boolean isLeft() {
    return left != null;
  }

  /**
   * @return true if it holds a value for the right type, false otherwise
   */
  public boolean isRight() {
    return right != null;
  }

  /**
   * @return the left value
   */
  public L getLeft() {
    return left;
  }

  /**
   * @return the right value
   */
  public R getRight() {
    return right;
  }

  public <T> Optional<T> getValue() {
    if (left != null) {
      return (Optional<T>) Optional.of(left);
    } else if (right != null) {
      return (Optional<T>) Optional.of(right);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public String toString() {
    if (left != null) {
      return format("%s - left: { %s }", this.getClass().getSimpleName(), left.toString());
    } else if (right != null) {
      return format("%s - right: { %s }", this.getClass().getSimpleName(), right.toString());
    } else {
      return format("%s - empty", this.getClass().getSimpleName());
    }
  }
}
