/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component.execution;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.event.Event;

/**
 * The result of executing an {@link ExecutableComponent} using an {@link InputEvent} instance. As well as providing the result
 * {@link Event} a way in which the execution can be completed is provided. This ensures that the all streams and any other
 * resources used in the execution are closed or cleaned up.
 *
 * @since 1.0
 */
@NoImplement
public interface ExecutionResult {

  /**
   * Obtain the result {@link Event}
   * 
   * @return the result Event.
   */
  Event getEvent();

  /**
   * Must be called when the {@link ExecutableComponent} caller no longer needs to read any stream payloads or variables and
   * resources can be closed.
   */
  void complete();

}
