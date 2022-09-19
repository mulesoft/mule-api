/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static java.lang.System.getProperty;
import static java.lang.System.setProperty;

import org.mule.api.annotation.Experimental;
import org.mule.runtime.api.config.MuleRuntimeFeature;
import org.mule.runtime.api.meta.model.declaration.fluent.ExtensionDeclarer;
import org.mule.runtime.api.metadata.MediaType;
import org.mule.runtime.api.streaming.CursorProvider;

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
  public static final String MULE_SECURITY_PROVIDER_ENABLE_HYBRID_DRBG = MULE_SECURITY_PROVIDER_PROPERTY + ".enableHybridDrbg";
  public static final String MULE_STREAMING_BUFFER_SIZE = SYSTEM_PROPERTY_PREFIX + "streaming.bufferSize";

  /**
   * A list of comma separated names of all known {@link org.mule.runtime.api.metadata.MediaType} param names. If they all match
   * then {@link MediaType#isDefinedInApp()} returns true even if used the {@link MediaType#parse(String)} method.
   *
   * @since 1.4, 1.3.1, 1.2.4, 1.1.7
   */
  public static final String MULE_KNOWN_MEDIA_TYPE_PARAM_NAMES = SYSTEM_PROPERTY_PREFIX + "mediatype.paramNames";

  /**
   * Enables streaming statistics
   *
   * @since 4.2.0
   */
  public static final String MULE_ENABLE_STREAMING_STATISTICS = SYSTEM_PROPERTY_PREFIX + "streaming.enableStatistics";

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

  /**
   * System property key to set the maximum nested sub-flow calls to create a single execution chain with. For the nth nested
   * sub-flow (n being the value of this variable), a new execution chain will be created, as was the default behavior until
   * 4.2.1.
   * <p>
   * By default, the value is set to 10.
   *
   * @since 1.4.0, 1.3.1
   */
  public static final String MULE_FLOW_REF_MAX_SUB_FLOWS_SINGLE_CHAIN = SYSTEM_PROPERTY_PREFIX + "flowRef.maxSubFlowsSingleChain";

  public static final String TESTING_MODE_PROPERTY_NAME = SYSTEM_PROPERTY_PREFIX + "testingMode";

  /**
   * Forces the validation of all loaded extension models
   */
  public static final String FORCE_EXTENSION_VALIDATION_PROPERTY_NAME = SYSTEM_PROPERTY_PREFIX + "forceExtensionValidation";

  /**
   * Disables the {@code ignore} directive when loading an Extension.
   *
   * @since 1.4.0
   */
  public static final String DISABLE_SDK_IGNORE_COMPONENT = SYSTEM_PROPERTY_PREFIX + "disableSdkComponentIgnore";

  /**
   * When present, Polling Sources are enriched with a parameter for configuring the maximum number of items per poll to be
   * processed.
   *
   * @since 1.4.0
   */
  public static final String ENABLE_SDK_POLLING_SOURCE_LIMIT = SYSTEM_PROPERTY_PREFIX + "enablePollingSourceLimit";

  public static final String MULE_STREAMING_MAX_MEMORY = SYSTEM_PROPERTY_PREFIX + "max.streaming.memory";
  public static final String MULE_SIMPLE_LOG = SYSTEM_PROPERTY_PREFIX + "simpleLog";

  /**
   * When present, implicit configuration for the XML SDK won't be created.
   *
   * @since 1.3
   */
  public static final String MULE_DISABLE_XML_SDK_IMPLICIT_CONFIGURATION_CREATION =
      SYSTEM_PROPERTY_PREFIX + "disableXmlSdkImplicitConfigurationCreation";

  /**
   * If specified, the log separation feature will be disabled, resulting in a performance boost. This makes sense in deployment
   * models in which only one app will be deployed per runtime instance.
   * <p>
   * Log configuration file will only be fetched from {@code MULE_HOME/conf}. Deployed artifacts won't get their own file in the
   * {@code MULE_HOME/logs/} automatically.
   *
   * @since 1.3.0
   */
  public static final String MULE_LOG_SEPARATION_DISABLED = SYSTEM_PROPERTY_PREFIX + "disableLogSeparation";

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
  /**
   * Comma-separated list of packages to force as registrable.
   * <p>
   * Refer to {@link ExtensionDeclarer#isTypeRegistrable}.
   * 
   * @since 1.4
   */
  public static final String MULE_FORCE_REGISTRABLE_EXTENSION_TYPE_PACKAGES =
      SYSTEM_PROPERTY_PREFIX + "extension.forceRegistrableType.packages";

  /**
   * @deprecated Since 4.4.0 this feature was removed.
   */
  @Deprecated
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
   *
   * @deprecated Starting with Mule 4.5.0 this property no longer has any effect. Cache no longer needed.
   */
  @Deprecated
  public static final String MULE_EXTENSIONS_CLIENT_CACHE_IS_DISABLED = SYSTEM_PROPERTY_PREFIX + "extensionsClient.disableCache";

  /**
   * If set, `ee:transform` and `ee:dynamic-evaluate` will execute in the specified scheduler instead of its default.
   * <p>
   * Possible values are the enums in {@code ProcessingType}.
   */
  public static final String DATA_WEAVE_SCRIPT_PROCESSING_TYPE = SYSTEM_PROPERTY_PREFIX + "dwScript.processingType";

  /**
   * If set, Mule will precompile DataWeave expressions at application startup time and fail the deployment if any of them cannot
   * be compiled.
   *
   * @since 1.3.0
   */
  public static final String MULE_EXPRESSIONS_COMPILATION_FAIL_DEPLOYMENT =
      SYSTEM_PROPERTY_PREFIX + "expressionCompilationFailDeployment";

  /**
   * If set, Mule will propagate any exception caught during the stop/dispose phase instead of just logging it. This is useful for
   * testing shutdown behavior.
   *
   * @since 1.3.0
   */
  public static final String MULE_LIFECYCLE_FAIL_ON_FIRST_DISPOSE_ERROR =
      SYSTEM_PROPERTY_PREFIX + "lifecycle.failOnFirstDisposeError";

  /**
   * This is a configuration property that can be set at deployment time to disable the scheduler message sources to be started
   * when deploying an application.
   */
  public static final String DISABLE_SCHEDULER_SOURCES_PROPERTY = SYSTEM_PROPERTY_PREFIX + "config.scheduler.disabled";

  /**
   * Allows to change the default value for the frequency property of the &lt;fixed-frequency&gt; element. Provided value must be
   * coercible to a {@link Long} and be expressed in millis.
   *
   * @since 1.3.0
   */
  public static final String DEFAULT_SCHEDULER_FIXED_FREQUENCY =
      SYSTEM_PROPERTY_PREFIX + "config.scheduler.defaultFixedFrequency";

  /**
   * When enabled, this System Property tracks the stacktrace from where the {@link CursorProvider#close()} method was called. It
   * can be used for troubleshooting purposes (for example, if someone tries to call {@link CursorProvider#openCursor()} on an
   * already closed cursor.
   *
   * @since 1.3.0
   */
  public static final String TRACK_CURSOR_PROVIDER_CLOSE_PROPERTY = SYSTEM_PROPERTY_PREFIX + "track.cursorProvider.close";

  /**
   * When set to {@code true} this System Property, more information about streaming will be logged. It can be used for
   * troubleshooting purposes
   *
   * @since 1.4.0
   */
  public static final String STREAMING_VERBOSE_PROPERTY = SYSTEM_PROPERTY_PREFIX + "streaming.verbose";

  /**
   * When enabled this System Property, if an ErrorType is not found in the policy ErrorType repository, then it's used the app
   * ErrorType repository.
   *
   * @since 1.3.0
   */
  public static final String SHARE_ERROR_TYPE_REPOSITORY_PROPERTY = SYSTEM_PROPERTY_PREFIX + "share.errorTypeRepository";

  /**
   * When enabled this System Property, the statistics are enabled even if the monitoring service is not acivated. This property
   * is only read on deploying an app.
   *
   * @since 4.4, 4.3.1
   */
  public static final String MULE_ENABLE_STATISTICS = SYSTEM_PROPERTY_PREFIX + "enable.statistics";

  /**
   * When enabled this System Property, the payload statistics are disabled independently of the statistics flag. This property is
   * only read on deploying an app.
   *
   * @since 4.4, 4.3.1
   * @deprecated since 4.4.1, 4.5.0. Payload statistics are no longer supported, so this property will be ignored.
   */
  @Experimental
  @Deprecated
  public static final String MULE_DISABLE_PAYLOAD_STATISTICS = SYSTEM_PROPERTY_PREFIX + "disable.payload.statistics";

  /**
   * When set to {@code true}, will not use any cache to reuse preparsed schema resources on deployment phase. If set to
   * {@code false}, or not set all, schema resources will be cached. This property is only read on deploying an app.
   *
   * @since 4.4
   */
  public static final String MULE_DISABLE_DEPLOYMENT_SCHEMA_CACHE = SYSTEM_PROPERTY_PREFIX + "disable.deployment.schema.cache";

  /**
   * When enabled, the defined categories of logging will result in a blocking processing type. Categories must be comma
   * separated. For instance: {@code some.category,other.category}.
   *
   * @since 4.2.0, 4.1.6
   */
  public static final String MULE_LOGGING_BLOCKING_CATEGORIES = SYSTEM_PROPERTY_PREFIX + "logging.blockingCategories";

  /**
   * A flux sink drops an event if next() is called after complete() or error(). When enabled, if there is an event dropped, a
   * WARN will be logged on next(), showing the complete() or error() stack trace. Additionally, the accept() stack trace is
   * logged in order to get a hint about how the chain is created or what is it intended for.
   *
   * @since 4.4.0, 4.3.1, 4.2.3
   */
  public static final String MULE_PRINT_STACK_TRACES_ON_DROP = SYSTEM_PROPERTY_PREFIX + "fluxSinkRecorder.printStackTracesOnDrop";

  /**
   * When set to {@code true}, if an error type is found that does not exist in the error type repository of the application (for
   * instance, an error handler for a random error that no component raises), a WARN is logged instead of failing the deployment.
   *
   * @since 4.4
   */
  public static final String MULE_LAX_ERROR_TYPES = SYSTEM_PROPERTY_PREFIX + "errorTypes.lax";

  /**
   * When set to {@code true}, fields annotated with {@code org.mule.sdk.api.annotation.param.reference.FlowReference} or
   * {@code org.mule.runtime.extension.api.annotation.param.reference.FlowReference} will match not only {@code flow} elements,
   * but any element with the name provided in the annotated field (for instance, a {@code sub-flow}).
   *
   * @since 4.4, 4.3.1
   */
  // MULE-19110
  public static final String MULE_FLOW_REFERERENCE_FIELDS_MATCH_ANY = SYSTEM_PROPERTY_PREFIX + "flowReference.matchesAny";

  /**
   * When set to {@code true} an application won't be able to override reserved properties such as <code>app.name</code>. If it
   * isn't set, the behaviour will depend on {@link MuleRuntimeFeature#HONOUR_RESERVED_PROPERTIES}.
   *
   * @see MuleRuntimeFeature#HONOUR_RESERVED_PROPERTIES
   * @since 4.4.0, 4.3.1
   */
  public static final String HONOUR_RESERVED_PROPERTIES_PROPERTY = SYSTEM_PROPERTY_PREFIX + "honour.reserved.properties";

  /**
   * If set to true, then DW will correctly handle Splitter's exceptions, avoiding some serialization issues. Be aware that when
   * enabled, this can make {@code error.cause} return a different exception. For more information see DW-383.
   *
   * @since 4.4.0, 4.3.1, 4.2.3
   */
  public static final String HANDLE_SPLITTER_EXCEPTION_PROPERTY = "mule.dw.handle_splitter_exception";

  /**
   * If set to true, then DW will remove implicit inputs when a variable with the same name is declared.
   *
   * @since 4.4.0
   */
  public static final String DW_REMOVE_SHADOWED_IMPLICIT_INPUTS_PROPERTY = "mule.dw.remove_shadowed_implicit_inputs";

  /**
   * When this property is set to {@code true}, DataWeave retains a mixed-content structure instead of grouping text with mixed
   * content into a single text field.
   *
   * @since 4.5.0
   */
  public static final String DW_HONOUR_MIXED_CONTENT_STRUCTURE_PROPERTY =
      "com.mulesoft.dw.xml_reader.honourMixedContentStructure";

  /**
   * If set to true, then fixed batch aggregator will only commit when a full block is processed. For more information see EE-7443
   *
   * @since 4.4.0, 4.3.1, 4.2.3
   */
  public static final String BATCH_FIXED_AGGREGATOR_TRANSACTION_RECORD_BUFFER_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "batch.fixed.aggregator.transaction.record.buffer";

  /**
   * If set to true, the connection exceptions will be computed to trigger alerts.
   *
   * @since 4.4.0, 4.3.1
   */
  public static final String COMPUTE_CONNECTION_ERRORS_IN_STATS_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "compute.connection.errors.in.stats";

  /**
   * If set to true, managed iterators transformed to Strings will show the representation of the elements instead of the generic
   * 'org.mule.runtime.core.internal.streaming.object.ManagedCursorIteratorProvider$ManagedCursorIterator@######'.
   *
   * @since 4.4.0
   */
  public static final String TO_STRING_TRANSFORMER_TRANSFORM_ITERATOR_ELEMENTS_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "transformer.toString.transformIteratorElements";

  /**
   * If set to true, the entity resolver will fail if a namespace cannot be resolved, while previously it only failed after 10
   * attempts were made.
   *
   * @since 4.4.0
   */
  public static final String ENTITY_RESOLVER_FAIL_ON_FIRST_ERROR_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "entityResolver.failOnFirstError";

  /**
   * If set to true, the set variable will create a variable even if the value is null.
   *
   * @since 4.4.0
   */
  public static final String SET_VARIABLE_WITH_NULL_VALUE_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "setVariable.WithNullValue";

  /**
   * If set to true, profiling events will be produced.
   *
   * @since 4.4.0
   */
  public static final String ENABLE_PROFILING_SERVICE_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "enable.profiling.service";

  /**
   * If set to true, the errors in tracing will be propagated to the application execution. If disabled, only a warning will be
   * logged indicating the problem that occurred when tracing the app.
   *
   * @since 4.5.0
   */
  public static final String ENABLE_PROPAGATION_OF_EXCEPTIONS_IN_TRACING =
      SYSTEM_PROPERTY_PREFIX + "enable.propagation.of.exceptions.in.tracing";

  /**
   * If set to true, extensions will only be able to load exported resources from the deployable artifacts (application, policy,
   * domain).
   *
   * @since 4.4.0, 4.3.1
   */
  public static final String START_EXTENSION_COMPONENTS_WITH_ARTIFACT_CLASSLOADER_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "startExtensionComponentsWithArtifactClassloader";

  /**
   * When enabled, the Runtime will trim whitespaces from parameter values defined at the attribute level in the dsl.
   *
   * @since 4.4.1, 4.3.1
   */
  public static final String DISABLE_ATTRIBUTE_PARAMETER_WHITESPACE_TRIMMING_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "disable.attribute.parameter.whitespace.trimming";

  /**
   * When enabled, the Runtime will trim whitespaces from CDATA text parameter of pojos in the dsl.
   *
   * @since 4.5
   */
  public static final String DISABLE_POJO_TEXT_CDATA_WHITESPACE_TRIMMING_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "disable.pojo.text.parameter.whitespace.trimming";

  /**
   * When enabled, the default error handler added by the runtime will not rollback a transaction that should not be rollback by
   * it
   *
   * @since 4.5.0, 4.4.1, 4.3.1
   */
  public static final String DEFAULT_ERROR_HANDLER_NOT_ROLLBACK_IF_NOT_CORRESPONDING_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "enable.default.errorhandler.not.rollback.incorrect.tx";

  /**
   * When enabled, expression validations will be enforced for targetValue, not allowing a literal value.
   *
   * @since 4.4.0-202202
   */
  public static final String ENFORCE_REQUIRED_EXPRESSION_VALIDATION_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "enforce.expression.validation";

  /**
   * When enabled, expression validations will be enforced for all DataWeave expressions.
   *
   * @since 4.5.0
   */
  public static final String ENFORCE_EXPRESSION_VALIDATION_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "enforce.dw.expression.validation";

  /**
   * When set to true, profiling consumers implemented by the runtime will be enabled by default.
   *
   * @since 4.4.0-202202
   */
  public static final String FORCE_RUNTIME_PROFILING_CONSUMERS_ENABLEMENT_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "force.runtime.profiling.consumers.enablement";

  /**
   * When set to true, if the items to iterate over on a parallel-foreach scope are messages (such as the output of an operation
   * that returns Result objects), they will be flattened in a way that is consistent with what the foreach scope does.
   *
   * @since 4.3.0-202203
   */
  public static final String PARALLEL_FOREACH_FLATTEN_MESSAGE_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "parallelForeach.flattenMessage";

  static {
    // Maintain compatibility after fix for MULE-19406
    final String oldEnableStreamingStatisticsValue = getProperty(SYSTEM_PROPERTY_PREFIX + ".enableStreamingStatistics");
    if (getProperty(MULE_ENABLE_STREAMING_STATISTICS) == null && oldEnableStreamingStatisticsValue != null) {
      setProperty(MULE_ENABLE_STREAMING_STATISTICS, oldEnableStreamingStatisticsValue);
    }
  }

  /**
   * If set to true, extensions imported by a policy will be managed in complete isolation from the extensions imported by the
   * application that is being applied to, and validations will prevent the usage of explicit configurations declared by the
   * application as part of the policy initialization."
   *
   * @since 4.4.0, 4.3.1
   */
  public static final String ENABLE_POLICY_ISOLATION_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "enable.policy.isolation";

  /**
   * If set to true, Mule will handle redirect requests. If set to false, we will delegate the redirect to Grizzly.
   *
   * @since 4.5.0, 4.4.0-202112, 4.3.0-202112
   */
  public static final String ENABLE_MULE_REDIRECT_PROPERTY = SYSTEM_PROPERTY_PREFIX + "http.enableMuleRedirect";

  /**
   * Property used to change the deployment mode to deploy only the indicated applications with no redeployment.
   * <p>
   * <code>mule -M-Dmule.deploy.applications=app1:app2:app3</code>
   * <p>
   * Using this property will disable the directory polling to find new artifacts to deploy.
   * 
   * @since 4.0
   */
  public static final String DEPLOYMENT_APPLICATION_PROPERTY = SYSTEM_PROPERTY_PREFIX + "deploy.applications";

  /**
   * If set to true, the deployment process will ignore the serialized artifactAst included in the artifact (generated by version
   * 3.6.+ of the <code>mule-maven-plugin</code>) and parse the config xml as it was in previous versions.
   *
   * @since 4.5.0
   */
  public static final String FORCE_PARSE_CONFIG_XMLS_ON_DEPLOYMENT_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "deployment.forceParseConfigXmls";


  /**
   * If set to true, the deployment process will use the SpringLifecycleObjectSorter during initialization/disposal.
   *
   * @since 4.5.0
   */
  @Experimental
  public static final String MULE_USE_LEGACY_LIFECYCLE_OBJECT_SORTER =
      SYSTEM_PROPERTY_PREFIX + "lifecycle.useLegacyObjectSorter";

  /**
   * If set to true, the Objects factories will be created with Byte Buddy instead of CGLIB."
   *
   * @since 4.5.0, 4.4.0-202204, 4.3.0-202204
   */
  public static final String ENABLE_BYTE_BUDDY_OBJECT_CREATION_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "enable.byteBuddy.objectCreation";

  /**
   * If set to true, whenever an application has an HTTP requester configured to use TLS, Grizzly will use the
   * WorkerThreadIOStrategy.
   *
   * @since 4.5.0, 4.4.1, 4.3.1
   */
  public static final String FORCE_WORKER_THREAD_IO_STRATEGY_WHEN_TLS_ENABLED =
      SYSTEM_PROPERTY_PREFIX + "https.forceWorkerThreadIoStrategy";

  /**
   * If set to true, attribute in entries in a `registry-bootstrap.properties` will be ignored.
   *
   * @since 4.5.0
   */
  public static final String DISABLE_REGISTRY_BOOTSTRAP_OPTIONAL_ENTRIES_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "https.forceWorkerThreadIoStrategy";

  /**
   * When enabled, the application model will be validated with the region classloader. When disabled, it will be validated with
   * the application classloader.
   * 
   * @since 4.5.0
   */
  public static final String VALIDATE_APPLICATION_MODEL_WITH_REGION_CLASSLOADER_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "deployment.validateAppModelWithRegionClassloader";

  /**
   * When set to true, AbstractForkJoinRouter based processors, such as ParallelForEach and ScatterGather routers, will show
   * detailed error information for their failed routes.
   *
   * @since 4.5.0
   */
  public static final String MULE_PRINT_DETAILED_COMPOSITE_EXCEPTION_LOG_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "detailedCompositeRoutingExceptionLog";

  /**
   * When set to true, the variableName identifier in SetVariable is set to not support expressions in the Mule Extension Model
   * (W-10998630)
   *
   * @since 4.5.0, 4.4.0-202207, 4.3.0-202207
   */
  public static final String REVERT_SUPPORT_EXPRESSIONS_IN_VARIABLE_NAME_IN_SET_VARIABLE_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "revertSupportExpressionsInVariableNameInSetVariable";


  /**
   * When set to true, the operation policy's error resolution is ignored so that the error mappings of the processor on which the
   * policy was applied are set successfully
   *
   * @since 4.5.0
   */
  public static final String HONOUR_ERROR_MAPPINGS_WHEN_POLICY_APPLIED_ON_OPERATION_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "honourErrorMappingsWhenPolicyAppliedOnOperation";

  /**
   * When set to true, it disables JDK vendor validation in JdkVersionUtils
   *
   * @since 4.5.0, 4.4.0-202208, 4.3.0-202208
   */
  public static final String DISABLE_JDK_VENDOR_VALIDATION_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "disableJDKVendorValidation";

  /**
   * If set to true, the global error handlers will be reused instead of creating local copies.
   *
   * @since 4.5.0, 4.4.1, 4.3.1
   */
  public static final String REUSE_GLOBAL_ERROR_HANDLER_PROPERTY =
      SYSTEM_PROPERTY_PREFIX + "reuse.globalErrorHandler";

  /**
   * If set to true, {@link org.mule.runtime.api.notification.Notification}s related to polling sources will be emitted.
   *
   * @since 4.5.0
   */
  public static final String EMIT_POLLING_SOURCE_NOTIFICATIONS =
      SYSTEM_PROPERTY_PREFIX + "emit.polling.source.notifications";

  /**
   * When set to true, transactions will be committed in case of redelivery exhausted error.
   * 
   * @since 4.5.0, 4.4.0-202209, 4.3.0-202209
   */
  public static final String COMMIT_REDELIVERY_EXHAUSTED = SYSTEM_PROPERTY_PREFIX + "commit.on.redelivery.exhausted";

  /**
   * When set to true, error suppression will happen. This will affect, for instance, the Web Service Consumer connector and the
   * Until Successful scope that will always report errors from their corresponding namespaces (MULE and WSC). Suppressed errors
   * will be treated as underlying causes.
   * 
   * @since 4.5.0, 4.4.0-202210, 4.3.0-202210
   */
  public static final String SUPPRESS_ERRORS_PROPERTY = SYSTEM_PROPERTY_PREFIX + "suppress.mule.exceptions";

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
