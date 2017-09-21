/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component.execution;

import org.mule.runtime.api.event.Event;
import org.mule.runtime.api.event.InputEvent;

/**
 * The result of executing an {@link ExecutableComponent} using an {@link InputEvent} instance. As well as providing the result
 * {@link Event} a way in which to complete the execution and ensure that the {@link ExecutableComponent} and close all streams
 * and any other resources is provided.
 */
public interface ExecutionResult {

  /**
   * Obtain the result {@link Event}
   * 
   * @return the result Event.
   */
  Event getEvent();

  /**
   * Must be called when the {@link ExecutableComponent} caller no longer needs to read any stream payloads or variables and
   * resource can be closed.
   */
  void complete();

}
