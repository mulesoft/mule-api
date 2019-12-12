/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static java.lang.System.getProperty;

/**
 * Defines Mule System properties.
 *
 * @since 1.2.0
 */
public final class MuleSystemProperties {

  public static final String SYSTEM_PROPERTY_PREFIX = "mule.";

  public static final String MULE_CONTEXT_PROPERTY = SYSTEM_PROPERTY_PREFIX + "context";
  public static final String MULE_ENCODING_SYSTEM_PROPERTY = SYSTEM_PROPERTY_PREFIX + "encoding";
  public static final String MULE_SECURITY_SYSTEM_PROPERTY = SYSTEM_PROPERTY_PREFIX + "security.model";
  public static final String MULE_SECURITY_PROVIDER_PROPERTY = SYSTEM_PROPERTY_PREFIX + "security.provider";
  public static final String MULE_STREAMING_BUFFER_SIZE = SYSTEM_PROPERTY_PREFIX + "streaming.bufferSize";

  /**
   * Enables streaming statistics
   *
   * @since 4.2.0
   */
  public static final String MULE_ENABLE_STREAMING_STATISTICS = SYSTEM_PROPERTY_PREFIX + ".enableStreamingStatistics";

  /**
   * System property key for the default size of a streaming buffer bucket
   */
  public static final String MULE_STREAMING_BUCKET_SIZE = SYSTEM_PROPERTY_PREFIX + "streaming.bucketSize";

  /**
   * System property key for the max percentage of the total heap memory that can be assigned to repeatable streaming memory.
   * <p>
   * Valid values are doubles between 0 and 1 (inclusive)
   *
   * @since 1.3.0
   */
  public static final String MULE_STREAMING_MAX_HEAP_PERCENTAGE = SYSTEM_PROPERTY_PREFIX + "streaming.maxHeapPercentage";

  /**
   * System property key for the max size of byte buffer pools used for repeatable streaming
   *
   * @since 1.3.0
   */
  public static final String MULE_STREAMING_MAX_BUFFER_POOL_SIZE = SYSTEM_PROPERTY_PREFIX + "streaming.bufferPoolMaxSize";

  /**
   * System property key to set the maximum allowed flowStack entries for an event context, before raising a {@code MULE:CRITICAL}
   * error.
   * <p>
   * By default, the value is set to 50.
   *
   * @since 1.3.0
   */
  public static final String MULE_FLOW_STACK_MAX_DEPTH = SYSTEM_PROPERTY_PREFIX + "flowStack.maxDepth";

  public static final String TESTING_MODE_PROPERTY_NAME = SYSTEM_PROPERTY_PREFIX + "testingMode";

  /**
   * Forces the validation of all loaded extension models
   */
  public static final String FORCE_EXTENSION_VALIDATION_PROPERTY_NAME = SYSTEM_PROPERTY_PREFIX + "forceExtensionValidation";

  public static final String MULE_STREAMING_MAX_MEMORY = SYSTEM_PROPERTY_PREFIX + "max.streaming.memory";
  public static final String MULE_SIMPLE_LOG = SYSTEM_PROPERTY_PREFIX + "simpleLog";
  public static final String MULE_FORCE_CONSOLE_LOG = SYSTEM_PROPERTY_PREFIX + "forceConsoleLog";
  public static final String MULE_LOG_CONTEXT_DISPOSE_DELAY_MILLIS = SYSTEM_PROPERTY_PREFIX + "log.context.dispose.delay.millis";
  public static final String MULE_LOG_DEFAULT_POLICY_INTERVAL =
      SYSTEM_PROPERTY_PREFIX + "log.defaultAppender.timeBasedTriggerPolicy.interval";
  public static final String MULE_LOG_DEFAULT_STRATEGY_MAX = SYSTEM_PROPERTY_PREFIX + "log.defaultAppender.rolloverStrategy.max";
  public static final String MULE_LOG_DEFAULT_STRATEGY_MIN = SYSTEM_PROPERTY_PREFIX + "log.defaultAppender.rolloverStrategy.min";
  public static final String MULE_FLOW_TRACE = SYSTEM_PROPERTY_PREFIX + "flowTrace";
  public static final String MULE_LOG_VERBOSE_CLASSLOADING = SYSTEM_PROPERTY_PREFIX + "classloading.verbose";
  public static final String MULE_MEL_AS_DEFAULT = SYSTEM_PROPERTY_PREFIX + "test.mel.default";
  public static final String MULE_DISABLE_RESPONSE_TIMEOUT = SYSTEM_PROPERTY_PREFIX + "timeout.disable";
  public static final String MULE_ALLOW_JRE_EXTENSION = SYSTEM_PROPERTY_PREFIX + "classloading.jreExtension";
  public static final String MULE_JRE_EXTENSION_PACKAGES = SYSTEM_PROPERTY_PREFIX + "classloading.jreExtension.packages";
  public static final String MULE_LOGGING_INTERVAL_SCHEDULERS_LATENCY_REPORT =
      SYSTEM_PROPERTY_PREFIX + "schedulers.latency.report.interval";


  /**
   * This is the timeout in milliseconds to wait before we detect that the test connectivity is done in case the test connectivity
   * is asynchronously done.
   *
   * @since 1.3.0
   */
  public static final String ASYNC_TEST_CONNECTIVITY_TIMEOUT_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "async.test.connectivity.timeout";

  /**
   * If set to true, the extension client will not use any cache to reuse resources between calls. If set to false, or not set at
   * all, the extension client will cache resources.
   */
  public static final String MULE_EXTENSIONS_CLIENT_CACHE_IS_DISABLED = SYSTEM_PROPERTY_PREFIX + "extensionsClient.disableCache";

  /**
   * If set, `ee:transform` and `ee:dynamic-evaluate` will execute in the specified scheduler instead of its default.
   * <p>
   * Possible values are the enums in {@code ProcessingType}.
   */
  public static final String DATA_WEAVE_SCRIPT_PROCESSING_TYPE = SYSTEM_PROPERTY_PREFIX + "dwScript.processingType";

  /**
   * If set, Mule will precompile DataWeave expressions at application startup time and fail the deployment if any of them
   * cannot be compiled.
   *
   * @since 1.3.0
   */
  public static final String MULE_EXPRESSIONS_COMPILATION_FAIL_DEPLOYMENT =
      SYSTEM_PROPERTY_PREFIX + "expressionCompilationFailDeployment";

  /**
   * @return {@code true} if the {@link #TESTING_MODE_PROPERTY_NAME} property has been set (regardless of the value)
   */
  public static boolean isTestingMode() {
    return getProperty(TESTING_MODE_PROPERTY_NAME) != null;
  }

  /**
   * @return {@code true} if the {@link #FORCE_EXTENSION_VALIDATION_PROPERTY_NAME} property has been set (regardless of the value)
   */
  public static boolean isForceExtensionValidation() {
    return getProperty(FORCE_EXTENSION_VALIDATION_PROPERTY_NAME) != null;
  }

  private MuleSystemProperties() {}
}
