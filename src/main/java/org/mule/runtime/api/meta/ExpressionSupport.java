/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta;

/**
 * Enumerates the different types of support that a given component can provide regarding expressions
 *
 * @since 1.0
 */
public enum ExpressionSupport {
  /**
   * Expressions are required but not enforced. Static values are accepted too.
   */
  SUPPORTED,

  /**
   * Expressions not allowed. Static values only
   */
  NOT_SUPPORTED,

  /**
   * Requires expressions. Static values are not supported
   */
  REQUIRED
}
