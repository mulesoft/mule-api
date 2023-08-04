/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el.validation;

/**
 * The kind of {@link ScopePhaseValidationItem}
 * 
 * @since 1.5.0
 */
public enum ScopePhaseValidationItemKind {
  /**
   * Indicated that a function is deprecated. These functions are discouraged from using, typically because it is dangerous, or
   * because a better alternative exists.
   * 
   * The following are the set of {@link ScopePhaseValidationItem#getParams()}} params according to this kind:
   * <ul>
   * <li>'function': The name of the deprecated function.</li>
   * <li>'since': The value indicating the expression language version since this function is deprecated.</li>
   * <li>'replacement': The replacement to use instead of the deprecated function.</li>
   * </ul>
   */
  DEPRECATED
}
