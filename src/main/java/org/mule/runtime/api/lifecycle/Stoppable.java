/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
