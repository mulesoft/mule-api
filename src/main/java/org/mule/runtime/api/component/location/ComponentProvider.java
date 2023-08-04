/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.component.location;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.component.Component;

/**
 * Interface for all objects that may have a relationship to an element in the configuration.
 * <p>
 * Common use case of this interface is to be implemented by exceptions that are related to failures in the creation/execution of
 * the component.
 *
 * @since 1.0
 */
@NoImplement
public interface ComponentProvider {

  /**
   * @return the element in the configuration.
   */
  Component getComponent();

}
