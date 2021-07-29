/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.profiling.type;

import org.mule.api.annotation.Experimental;
import org.mule.runtime.api.profiling.type.context.ProcessingStrategyProfilingEventContext;

/**
 * {@link ProfilingEventType}'s associated to the Runtime {@link ProfilingEventType}'s.
 *
 * @since 4.4
 */
@Experimental
public final class RuntimeProfilingEventTypes {

  private static final String MULE_NAMESPACE = "MULE";

  /**
   * A {@link ProfilingEventType} that indicates that a runtime event has reached the processing strategy that orchestrates an
   * operation's execution. The processing strategy is scheduling the execution of the operation, which may involve a thread
   * switch.
   */
  public static final ProfilingEventType<ProcessingStrategyProfilingEventContext> PS_SCHEDULING_OPERATION_EXECUTION =
      ProcessingStrategyProfilingEventType.of("PS_SCHEDULING_OPERATION_EXECUTION", MULE_NAMESPACE);

  /**
   * A {@link ProfilingEventType} that indicates that an operation is about to begin its execution. This boundary is expressed as
   * broadly as possible, and will include, for instance, the execution of the interceptors defined for the operation.
   */
  public static final ProfilingEventType<ProcessingStrategyProfilingEventContext> STARTING_OPERATION_EXECUTION =
      ProcessingStrategyProfilingEventType.of("STARTING_OPERATION_EXECUTION", MULE_NAMESPACE);

  /**
   * A {@link ProfilingEventType} that indicates that an operation has finished its execution, and the processing strategy must
   * resolve the message passing of the resultant runtime event to the flow which may involve a thread switch.
   */
  public static final ProfilingEventType<ProcessingStrategyProfilingEventContext> OPERATION_EXECUTED =
      ProcessingStrategyProfilingEventType.of("OPERATION_EXECUTED", MULE_NAMESPACE);

  /**
   * A {@link ProfilingEventType} that indicates that the processing strategy has executed the message passing and is handling the
   * control back to the flow.
   */
  public static final ProfilingEventType<ProcessingStrategyProfilingEventContext> PS_FLOW_MESSAGE_PASSING =
      ProcessingStrategyProfilingEventType.of("PS_FLOW_MESSAGE_PASSING", MULE_NAMESPACE);

  /**
   * A {@link ProfilingEventType} that indicates that a runtime event reached the processing strategy that orchestrates a flow's
   * execution. The processing strategy is scheduling the execution of the flow, which may involve a thread switch.
   */
  public static final ProfilingEventType<ProcessingStrategyProfilingEventContext> PS_SCHEDULING_FLOW_EXECUTION =
      ProcessingStrategyProfilingEventType.of("PS_SCHEDULING_FLOW_EXECUTION", MULE_NAMESPACE);

  /**
   * A {@link ProfilingEventType} that indicates that the flow is about to begin its execution.
   */
  public static final ProfilingEventType<ProcessingStrategyProfilingEventContext> STARTING_FLOW_EXECUTION =
      ProcessingStrategyProfilingEventType.of("STARTING_FLOW_EXECUTION", MULE_NAMESPACE);

  /**
   * A {@link ProfilingEventType} that indicates that the flow has finished its execution.
   */
  public static final ProfilingEventType<ProcessingStrategyProfilingEventContext> FLOW_EXECUTED =
      ProcessingStrategyProfilingEventType.of("FLOW_EXECUTED", MULE_NAMESPACE);

  private RuntimeProfilingEventTypes() {}

}
