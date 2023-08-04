/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.lifecycle;

import org.mule.runtime.api.exception.MuleException;

/**
 * {@code Stoppable} is a lifecycle interface that introduces a {@link #stop()} method to an object.
 *
 * This lifecycle interface should always be implemented with its opposite lifecycle interface {@link Stoppable}.
 *
 * @see Startable
 */
public interface Stoppable {

  String PHASE_NAME = "stop";

  void stop() throws MuleException;
}
