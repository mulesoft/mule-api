/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component.execution;

import org.mule.runtime.api.event.Event;
import org.mule.runtime.api.component.Component;
import org.mule.runtime.api.event.EventContext;

import java.util.concurrent.CompletableFuture;

/**
 * Component representable in the Mule configuration that allows to be executed programmatically
 *
 * @since 1.0
 */
public interface ExecutableComponent extends Component {

  /**
   * Executes the component based on a {@link InputEvent} created programmatically be the user.
   * <p>
   * Streams will be closed and resources cleaned up when the callee invokes {@link ExecutionResult#complete()}.
   *
   * @param inputEvent the input to execute the component
   * @return a {@link ExecutionResult} with the content of the result
   * @throws ComponentExecutionException if there is an unhandled error within the execution
   */
  CompletableFuture<ExecutionResult> execute(InputEvent inputEvent);

  /**
   * Executes the component based on a {@link Event} that may have been provided by another component execution.
   * <p>
   * Streams will be closed and resources cleaned up when the existing root {@link EventContext} completes.
   *
   * @param event the input to execute the component
   * @return a {@link Event} with the content of the result
   * @throws ComponentExecutionException if there is an unhandled error within the execution
   */
  CompletableFuture<Event> execute(Event event);

}
