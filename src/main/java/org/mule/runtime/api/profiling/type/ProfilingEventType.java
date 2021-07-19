/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.profiling.type;

import org.mule.api.annotation.Experimental;
import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.profiling.ProfilingEventContext;

/**
 * The profiling event type. This is implemented for defining profiling event types by the runtime
 *
 * @param <T> the {@link ProfilingEventContext} associated to the type.
 *
 * @since 1.4
 */
@Experimental
@NoImplement
public interface ProfilingEventType<T extends ProfilingEventContext> {

  /**
   * @return the profiling event type name.
   */
  String getProfilingEventName();
}
