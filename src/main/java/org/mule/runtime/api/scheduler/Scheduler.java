/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Provides access to a specific executor in the Mule runtime.
 * <p>
 * See {@link ScheduledExecutorService} and {@link ExecutorService} for documentation on the provided methods.
 * 
 * @since 1.0
 */
public interface Scheduler extends ScheduledExecutorService {

}
