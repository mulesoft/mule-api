/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.profiling.notification;

import org.mule.api.annotation.Experimental;
import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.notification.NotificationListener;

/**
 * An interface for {@link ProfilingNotification} listeners.
 * This is implemented by the runtime to use notifications for producing profiling data.
 *
 * @param <T> extension of {@link ProfilingNotification}
 */
@Experimental
@NoImplement
public interface ProfilerNotificationListener<T extends ProfilingNotification>
    extends NotificationListener<ProfilingNotification> {

}
