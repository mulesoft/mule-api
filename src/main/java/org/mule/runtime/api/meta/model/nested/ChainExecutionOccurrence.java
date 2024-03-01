/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.nested;

/**
 * Describes how many times the inner chain in a scope or route is expected to be called each time the owning component is
 * executed.
 *
 * @since 1.7.0
 */
public enum ChainExecutionOccurrence {

  /**
   * Chain execution may be skipped, but will not be executed more than once
   */
  ONCE_OR_NONE,

  /**
   * Chain execution may not be skipped, but will only be executed once
   */
  ONCE,

  /**
   * Chain may be executed multiple times, but will for sure be executed at least once
   */
  AT_LEAST_ONCE,

  /**
   * Chain may be executed multiple times or non at all
   */
  MULTIPLE_OR_NONE,

  /**
   * The owning component does not provide information about how the chain will be used. This is the default value that legacy
   * components will return
   */
  UNKNOWN
}
