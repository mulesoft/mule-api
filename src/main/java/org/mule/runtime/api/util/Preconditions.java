/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.util;

import java.util.function.Supplier;

/**
 * <p>
 * Utility class to validate Preconditions
 * </p>
 */
public class Preconditions {

  /**
   * @param condition Condition that the argument must satisfy
   * @param message   The Message of the exception in case the condition is invalid
   */
  public static void checkArgument(boolean condition, String message) {
    if (!condition) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * @param condition Condition that the argument must satisfy
   * @param message   The Message supplier for the exception in case the condition is invalid
   */
  public static void checkArgument(boolean condition, Supplier<String> message) {
    if (!condition) {
      throw new IllegalArgumentException(message.get());
    }
  }

  /**
   * @param condition Condition that must be satisfied
   * @param message   The Message of the exception in case the condition is invalid
   */
  public static void checkState(boolean condition, String message) {
    if (!condition) {
      throw new IllegalStateException(message);
    }
  }

  /**
   * @param condition Condition that must be satisfied
   * @param message   The Message of the exception in case the condition is invalid
   * @param message   The Message supplier for the exception in case the condition is invalid
   */
  public static void checkState(boolean condition, Supplier<String> message) {
    if (!condition) {
      throw new IllegalStateException(message.get());
    }
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method is not null.
   *
   * @param reference    an object reference
   * @param errorMessage the exception message to use if the check fails
   * @return the non-null reference that was validated
   * @throws NullPointerException if {@code reference} is null
   * @deprecated Use {@link java.util.Objects#requireNonNull(Object, String)}
   */
  @Deprecated
  public static <T> T checkNotNull(T reference, String errorMessage) {
    if (reference == null) {
      throw new NullPointerException(errorMessage);
    }
    return reference;
  }

  private Preconditions() {}
}
