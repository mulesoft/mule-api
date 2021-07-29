/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.profiling.type.context;

import org.mule.api.annotation.Experimental;
import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.profiling.type.ProcessingStrategyProfilingEventType;
import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.profiling.ProfilingEventContext;
import org.mule.runtime.api.profiling.type.ProfilingEventType;

import java.util.Optional;

/**
 * {@link ProfilingEventContext} for processing strategy related {@link ProfilingEventType}'s. This defines the data associated to
 * processing strategies profiling event types.
 *
 * @see ProcessingStrategyProfilingEventType
 * @since 4.4
 */
@NoImplement
@Experimental
public interface ProcessingStrategyProfilingEventContext extends ProfilingEventContext {

  /**
   * @return the correlation id associated with the profiling event.
   */
  String getCorrelationId();

  /**
   * @return the thread name of the profiling event.
   */
  String getThreadName();

  /**
   * @return the artifact id of the profiling event.
   */
  String getArtifactId();

  /**
   * @return the artifact type of the profiling event.
   */
  String getArtifactType();

  /**
   * @return the {@link ComponentLocation} associated with the profiling event if exists.
   */
  Optional<ComponentLocation> getLocation();

}
