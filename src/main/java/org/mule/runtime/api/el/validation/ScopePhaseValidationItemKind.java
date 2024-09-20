/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el.validation;

/**
 * The kind of {@link ScopePhaseValidationItem}
 * 
 * @since 1.5.0
 * @deprecated since 1.9.0. Replaced with new validation API that not only include scope phase but also the rest of the phases.
 *  *             {@link ConstraintViolation}.
 */
@Deprecated
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
