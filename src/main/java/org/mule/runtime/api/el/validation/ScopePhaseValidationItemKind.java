/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
   * The following are the set of {@link ScopePhaseValidationItem#getParams()}} params according to this kind: - 'function': The
   * name of the deprecated function. - 'since': The value indicating the expression language version since this function is
   * deprecated. - 'replacement': The replacement to use instead of the deprecated function.
   */
  DEPRECATED
}
