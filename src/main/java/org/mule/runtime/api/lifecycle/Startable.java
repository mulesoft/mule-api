/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.lifecycle;

import org.mule.runtime.api.exception.MuleException;

/**
 * {@code Startable} provides an object with a {@link #start()} method which gets called when the Mule instance gets started. This
 * is mostly used by infrastructure components, but can also be implemented by service objects.
 * <p/>
 * This lifecycle interface should always be implemented with its opposite lifecycle interface {@link Stoppable}.
 * <p/>
 * In case that the {@link #start()} method execution fails then mule will call the {@link Stoppable#stop()} method if the class
 * also implements {@link Stoppable} allowing the object to dispose any allocated resource during {@link #start()}
 * <p/>
 * 
 * @see Stoppable
 */
public interface Startable {

  String PHASE_NAME = "start";

  void start() throws MuleException;

}
