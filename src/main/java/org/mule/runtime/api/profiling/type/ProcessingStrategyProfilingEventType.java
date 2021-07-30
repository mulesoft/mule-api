/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.profiling.type;

import static java.util.Objects.requireNonNull;

import org.mule.api.annotation.Experimental;
import org.mule.runtime.api.profiling.ProfilingDataConsumer;
import org.mule.runtime.api.profiling.type.context.ProcessingStrategyProfilingEventContext;

import java.util.Objects;

/**
 * A {@link ProfilingEventType} for processing strategies. A {@link ProfilingDataConsumer} can be created to consume the data
 * associated to these profiling events.
 *
 * @see ProfilingEventType
 * @see ProfilingDataConsumer
 * @since 4.4
 */
@Experimental
public final class ProcessingStrategyProfilingEventType implements ProfilingEventType<ProcessingStrategyProfilingEventContext> {

  private final String profilingEventTypeIdentifier;
  private final String profilingEventTypeNamespace;

  // TODO Can this be refactored and become a generic ProfilingEventType generator?
  public static ProcessingStrategyProfilingEventType of(String profilingEventTypeIdentifier, String profilingEventTypeNamespace) {
    return new ProcessingStrategyProfilingEventType(profilingEventTypeIdentifier, profilingEventTypeNamespace);
  }

  private ProcessingStrategyProfilingEventType(String profilingEventTypeIdentifier, String profilingEventTypeNamespace) {
    requireNonNull(profilingEventTypeIdentifier);
    requireNonNull(profilingEventTypeNamespace);
    this.profilingEventTypeIdentifier = profilingEventTypeIdentifier;
    this.profilingEventTypeNamespace = profilingEventTypeNamespace;
  }

  @Override
  public String getProfilingEventTypeIdentifier() {
    return profilingEventTypeIdentifier;
  }

  @Override
  public String getProfilingEventTypeNamespace() {
    return profilingEventTypeNamespace;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ProcessingStrategyProfilingEventType that = (ProcessingStrategyProfilingEventType) o;
    return profilingEventTypeIdentifier.equals(that.profilingEventTypeIdentifier)
        && profilingEventTypeNamespace.equals(that.profilingEventTypeNamespace);
  }

  @Override
  public int hashCode() {
    return Objects.hash(profilingEventTypeIdentifier, profilingEventTypeNamespace);
  }
}
