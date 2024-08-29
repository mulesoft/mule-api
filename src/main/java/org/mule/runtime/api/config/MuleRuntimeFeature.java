/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.config;

import static org.mule.runtime.api.util.MuleSystemProperties.ADD_MULE_SPECIFIC_TRACING_INFORMATION_IN_TRACE_STATE_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.BATCH_FIXED_AGGREGATOR_TRANSACTION_RECORD_BUFFER_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.COMPUTE_CONNECTION_ERRORS_IN_STATS_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.CREATE_CHILD_POLICY_CONTEXT_FOR_PARALLEL_SCOPES_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.DEFAULT_ERROR_HANDLER_NOT_ROLLBACK_IF_NOT_CORRESPONDING_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.DISABLE_APPLY_OBJECT_PROCESSOR_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.DISABLE_ATTRIBUTE_PARAMETER_WHITESPACE_TRIMMING_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.DISABLE_JMX_FOR_COMMONS_POOL2_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.DISABLE_POJO_TEXT_CDATA_WHITESPACE_TRIMMING_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.DISABLE_REGISTRY_BOOTSTRAP_OPTIONAL_ENTRIES_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.DISABLE_SCHEDULER_LOGGING_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.DW_HONOUR_MIXED_CONTENT_STRUCTURE_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.DW_REMOVE_SHADOWED_IMPLICIT_INPUTS_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ENABLE_BYTE_BUDDY_OBJECT_CREATION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ENABLE_POLICY_ISOLATION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ENABLE_PROFILING_SERVICE_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ENABLE_XML_SDK_MDC_RESET_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ENFORCE_EXPRESSION_VALIDATION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ENFORCE_REQUIRED_EXPRESSION_VALIDATION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ENTITY_RESOLVER_FAIL_ON_FIRST_ERROR_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ERROR_AND_ROLLBACK_TX_WHEN_TIMEOUT_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.FORCE_RUNTIME_PROFILING_CONSUMERS_ENABLEMENT_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.FOREACH_ROUTER_REJECTS_MAP_EXPRESSIONS_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.HANDLE_SPLITTER_EXCEPTION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.HONOUR_ERROR_MAPPINGS_WHEN_POLICY_APPLIED_ON_OPERATION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.HONOUR_INSECURE_TLS_CONFIGURATION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.HONOUR_PERSISTED_FLOW_STATE_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.HONOUR_RESERVED_PROPERTIES_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.MULE_DISABLE_XML_SDK_IMPLICIT_CONFIGURATION_CREATION;
import static org.mule.runtime.api.util.MuleSystemProperties.MULE_PRINT_DETAILED_COMPOSITE_EXCEPTION_LOG_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.MULE_UNSUPPORTED_EXTENSIONS_CLIENT_RUN_ASYNC;
import static org.mule.runtime.api.util.MuleSystemProperties.PARALLEL_FOREACH_FLATTEN_MESSAGE_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.PUT_TRACE_ID_AND_SPAN_ID_IN_MDC_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.RETHROW_EXCEPTIONS_IN_IDEMPOTENT_MESSAGE_VALIDATOR_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.SET_VARIABLE_WITH_NULL_VALUE_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.START_EXTENSION_COMPONENTS_WITH_ARTIFACT_CLASSLOADER_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.SUPPORT_NATIVE_LIBRARY_DEPENDENCIES_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.SUPPRESS_ERRORS_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.TO_STRING_TRANSFORMER_TRANSFORM_ITERATOR_ELEMENTS_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.USE_TRANSACTION_SINK_INDEX_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.VALIDATE_APPLICATION_MODEL_WITH_REGION_CLASSLOADER_PROPERTY;

import static java.util.Optional.ofNullable;

import java.util.Optional;

/**
 * <p>
 * List of {@link Feature}s that will be enabled or disabled per application depending on the deployment context.
 * </p>
 *
 * <p>
 * When some Mule runtime feature needs to be flagged, it should be added here as a new enum constant. Each entry must have a
 * meaningful, customer facing name, and must provide values for all the {@link Feature} fields.
 * <p>
 * For example:
 * </p>
 *
 * <pre>
 *    public enum MuleRuntimeFeature implements Feature {
 *      ...
 *
 *     {@code /}**
 *       * When enabled, reserved properties such as 'app.name' can't be overridden by global properties (overrides will be ignored).
 *       *
 *       * @since 4.4.0, 4.3.0-202102
 *       *
 *      {@code *}/
 *      HONOUR_RESERVED_PROPERTIES("When enabled, reserved properties such as 'app.name' can't be overridden by global properties (overrides will be ignored).",
 *            "MULE-19083", "4.4.0, 4.3.1", HONOUR_RESERVED_PROPERTIES_PROPERTY),
 *
 *      ...
 *
 *    }
 * </pre>
 *
 * @see Feature
 * @see FeatureFlaggingService
 * @since 4.4.0 4.3.1
 */
public enum MuleRuntimeFeature implements Feature {

  /**
   * When enabled, reserved properties such as 'app.name' can't be overridden by global properties (overrides will be ignored).
   *
   * @since 4.4.0, 4.3.0-202102
   */
  HONOUR_RESERVED_PROPERTIES(
      "When enabled, reserved properties such as 'app.name' can't be overridden by global properties (overrides are be ignored).",
      "MULE-17659", "4.4.0, 4.3.0", HONOUR_RESERVED_PROPERTIES_PROPERTY),

  /**
   * When enabled, DataWeave will correctly handle splitter exceptions, avoiding Scala serialization issues.
   *
   * @since 4.4.0
   */
  HANDLE_SPLITTER_EXCEPTION(
      "When enabled, DataWeave correctly handles internal exceptions while splitting a payload, preventing subsequent serialization errors.",
      "DW-383",
      "4.4.0", HANDLE_SPLITTER_EXCEPTION_PROPERTY),

  /**
   * When enabled, fixed batch aggregators will only commit when a full block is processed. See EE-7443 for more information.
   *
   * @since 4.4.0, 4.3.0-202103, 4.2.2-202103"
   */
  BATCH_FIXED_AGGREGATOR_TRANSACTION_RECORD_BUFFER(
      "When enabled, batch aggregators with fixed size aggregators commit only when a full block is processed.",
      "MULE-19218",
      "", BATCH_FIXED_AGGREGATOR_TRANSACTION_RECORD_BUFFER_PROPERTY),

  /**
   * When enabled, Connection errors will be computed as part of alerts triggering.
   *
   * @since 4.4.0, 4.3.0-202103
   */
  COMPUTE_CONNECTION_ERRORS_IN_STATS(
      "When enabled, connection errors are computed as part of alerts triggering.",
      "MULE-19020",
      "4.4.0", COMPUTE_CONNECTION_ERRORS_IN_STATS_PROPERTY),

  /**
   * When enabled, managed cursor iterators transformed to Strings will show the representation of the elements instead of the
   * generic 'org.mule.runtime.core.internal.streaming.object.ManagedCursorIteratorProvider$ManagedCursorIterator@######'.
   *
   * @since 4.4.0
   */
  TO_STRING_TRANSFORMER_TRANSFORM_ITERATOR_ELEMENTS(
      "When enabled, managed cursor iterators transformed to strings show the representation of the elements instead of generic value `org.mule.runtime.core.internal.streaming.object.-ManagedCursorIteratorProvider$ManagedCursorIterator@.`",
      "MULE-19323",
      "4.4.0",
      TO_STRING_TRANSFORMER_TRANSFORM_ITERATOR_ELEMENTS_PROPERTY),

  /**
   * When enabled, extensions imported by a policy will be managed in complete isolation from the extensions imported by the
   * application that is being applied to, and validations will prevent the usage of explicit configurations declared by the
   * application as part of the policy initialization."
   *
   * @since 4.4.0, 4.3.0-202107
   */
  ENABLE_POLICY_ISOLATION(
      "When enabled, Mule manages extensions imported by a policy in complete isolation from the extensions imported by the Mule application. Also, validations prevent the use of explicit configurations that the application declared as part of the policy initialization.",
      "MULE-19226",
      "4.4.0", ENABLE_POLICY_ISOLATION_PROPERTY),

  /**
   * When enabled, the Mule XML DSL parser will fail when deploying an application that declares a schema that cannot be located.
   * Otherwise, it will fail if the application also makes use of the namespace that such schema is bound to.
   *
   * @since 4.4.0
   */
  ENTITY_RESOLVER_FAIL_ON_FIRST_ERROR(
      "When enabled, the Mule XML DSL parser fails when deploying an application that declares a schema that cannot be located. Otherwise, the parser fails if the application also makes use of the namespace to which such a schema is bound.",
      "EE-7827",
      "4.4.0",
      ENTITY_RESOLVER_FAIL_ON_FIRST_ERROR_PROPERTY),

  /**
   * When enabled, runtime profiling capabilities will become available.
   *
   * @since 4.4.0
   */
  ENABLE_PROFILING_SERVICE(
      "When enabled, Mule runtime profiling capabilities become available.",
      "MULE-19588",
      "",
      ENABLE_PROFILING_SERVICE_PROPERTY),

  /**
   * When enabled, the Set Variable component will create a variable even if it's value is null.
   *
   * @since 4.4.0, 4.3.0-202106
   */
  SET_VARIABLE_WITH_NULL_VALUE(
      "When enabled, the Set Variable component creates a variable even if its value is `null`.",
      "MULE-19443",
      "4.4.0",
      SET_VARIABLE_WITH_NULL_VALUE_PROPERTY),

  /**
   * If set to true, extensions will only be able to load exported resources from the deployable artifacts (application, policy,
   * domain).
   *
   * @since 4.4.0, 4.3.0-202110
   */
  START_EXTENSION_COMPONENTS_WITH_ARTIFACT_CLASSLOADER(
      "When enabled, extensions can load exported resources only from the deployable artifacts (application, policy, domain).",
      "MULE-19815",
      "4.4.0",
      START_EXTENSION_COMPONENTS_WITH_ARTIFACT_CLASSLOADER_PROPERTY),

  /**
   * When enabled, DataWeave will remove implicit inputs when a variable with the same name is declared at the root level.
   *
   * @since 4.5.0, 4.4.0-202111
   */
  DW_REMOVE_SHADOWED_IMPLICIT_INPUTS(
      "When enabled, DataWeave will remove implicit inputs when a variable with the same name is declared at the root level.",
      "DW-893",
      "4.4.0", DW_REMOVE_SHADOWED_IMPLICIT_INPUTS_PROPERTY),

  /**
   * When this property is set to {@code true}, DataWeave retains a mixed-content structure instead of grouping text with mixed
   * content into a single text field.
   *
   * @since 4.5.0
   */
  DW_HONOUR_MIXED_CONTENT_STRUCTURE(
      "When this property is set to true, DataWeave retains a mixed-content structure instead of grouping text with mixed content into a single text field.",
      "W-11071481",
      "4.5.0", DW_HONOUR_MIXED_CONTENT_STRUCTURE_PROPERTY),

  /**
   * When enabled, error types validations will be enforced, even for error handlers/components that are not being referenced.
   *
   * @since 4.5.0
   */
  ENFORCE_ERROR_TYPES_VALIDATION(
      "When enabled, error types validations will be enforced, even for error handlers/components that are not being referenced.",
      "MULE-19879",
      "4.5.0"),

  /**
   * When enabled, the Runtime will trim whitespaces from parameter values defined at the attribute level in the dsl.
   *
   * @since 4.5.0
   */
  DISABLE_ATTRIBUTE_PARAMETER_WHITESPACE_TRIMMING(
      "When enabled, the Runtime will trim whitespaces from parameter values defined at the attribute level in the dsl",
      "MULE-19803", "4.5.0", DISABLE_ATTRIBUTE_PARAMETER_WHITESPACE_TRIMMING_PROPERTY),

  /**
   * When enabled, the Runtime will trim whitespaces from CDATA text parameter of pojos in the dsl.
   *
   * @since 4.5.0
   */
  DISABLE_POJO_TEXT_CDATA_WHITESPACE_TRIMMING(
      "When enabled, the Runtime will trim whitespaces from CDATA text parameter of pojos in the dsl",
      "MULE-20048", "4.5.0", DISABLE_POJO_TEXT_CDATA_WHITESPACE_TRIMMING_PROPERTY),

  /**
   * When enabled, the default error handler added by the runtime will not rollback a transaction that should not be rollback by
   * it
   *
   * @since 4.5.0, 4.4.0-202201, 4.3.0-202201
   */
  DEFAULT_ERROR_HANDLER_NOT_ROLLBACK_IF_NOT_CORRESPONDING(
      "When enabled, the default error handler added by the runtime will not rollback a transaction that should not be rollback by it",
      "MULE-19919", "4.5.0, 4.4.1, 4.3.1", DEFAULT_ERROR_HANDLER_NOT_ROLLBACK_IF_NOT_CORRESPONDING_PROPERTY),

  /**
   * When enabled, expression validations will be enforced for targetValue, not allowing a literal value.
   *
   * @since 4.5.0
   */
  ENFORCE_REQUIRED_EXPRESSION_VALIDATION(
      "When enabled, expression validations will be enforced for targetValue, not allowing a literal value.",
      "MULE-19987", "4.5.0", ENFORCE_REQUIRED_EXPRESSION_VALIDATION_PROPERTY),

  /**
   * When enabled, expression validations will be enforced for all DataWeave expressions.
   *
   * @since 4.5.0
   */
  // TODO W-10883564 Remove this feature flag along with the work for W-10883564.
  ENFORCE_EXPRESSION_VALIDATION(
      "When enabled, expression validations will be enforced for all DataWeave expressions.",
      "MULE-19967", "4.5.0", ENFORCE_EXPRESSION_VALIDATION_PROPERTY),

  /**
   * When enabled, profiling consumers implemented by the runtime will be enabled by default.
   *
   * @since 4.5.0, 4.4.0-202202
   */
  FORCE_RUNTIME_PROFILING_CONSUMERS_ENABLEMENT(
      "When enabled, profiling consumers implemented by the runtime will be enabled by default.",
      "MULE-19967", "", FORCE_RUNTIME_PROFILING_CONSUMERS_ENABLEMENT_PROPERTY),

  /**
   * When enabled, if the items to iterate over on a parallel-foreach scope are messages (such as the output of an operation that
   * returns Result objects), they will be flattened in a way that is consistent with what the foreach scope does.
   *
   * @since 4.5.0, 4.4.0-202202, 4.3.0-202202
   */
  PARALLEL_FOREACH_FLATTEN_MESSAGE(
      "When enabled, if the items to iterate over on a parallel-foreach scope are messages (such as the output of an operation that returns Result objects), they will be flattened in a way that is consistent with what the foreach scope does.",
      "MULE-20067", "4.5.0", PARALLEL_FOREACH_FLATTEN_MESSAGE_PROPERTY),

  /**
   * When enabled, `optional` attribute in entries in a `registry-bootstrap.properties` will be ignored.
   * 
   * @since 4.5.0
   * @deprecated since 4.9.0, `optional` attribute in entries are no longer supported
   */
  @Deprecated
  DISABLE_REGISTRY_BOOTSTRAP_OPTIONAL_ENTRIES(
      "When enabled, `optional` attribute in entries in a `registry-bootstrap.properties` will be ignored.",
      "W-10736301", "4.5.0", DISABLE_REGISTRY_BOOTSTRAP_OPTIONAL_ENTRIES_PROPERTY),

  /**
   * When enabled, org.mule.runtime.core.privileged.registry.ObjectProcessor implementations will not be applied on objects
   * registered into the `SimpleRegistry`.
   * 
   * @since 4.5.0
   */
  // TODO W-10781591 Remove this feature flag along with the work for W-10781591
  DISABLE_APPLY_OBJECT_PROCESSOR(
      "When enabled, org.mule.runtime.core.privileged.registry.ObjectProcessor implementations will not be applied on objects registered into the `SimpleRegistry`.",
      "MULE-11737", "4.5.0", DISABLE_APPLY_OBJECT_PROCESSOR_PROPERTY),

  /**
   * When enabled, the Objects factories will be created with Byte Buddy instead of CGLIB.
   *
   * @since 4.4.0-202203, 4.3.0-202203
   * @deprecated since 4.5.0, ByteBuddy is always used.
   */
  @Deprecated
  ENABLE_BYTE_BUDDY_OBJECT_CREATION(
      "When enabled, the Objects factories will be created with Byte Buddy instead of CGLIB.",
      "W-10672687", "4.4.0", ENABLE_BYTE_BUDDY_OBJECT_CREATION_PROPERTY),

  /**
   * When enabled, the application model will be validated with the region classloader. When disabled, it will be validated with
   * the application classloader.
   *
   * @since 4.5.0
   */
  // TODO W-10855416 Remove this feature flag along with the work for W-10855416.
  VALIDATE_APPLICATION_MODEL_WITH_REGION_CLASSLOADER(
      "When enabled, the application model will be validated with the region classloader. When disabled, it will be validated with the application classloader.",
      "W-10808757", "4.5.0", VALIDATE_APPLICATION_MODEL_WITH_REGION_CLASSLOADER_PROPERTY),


  /**
   * When enabled, AbstractForkJoinRouter based processors, such as ParallelForEach and ScatterGather routers, will show detailed
   * error information for their failed routes.
   *
   * @since 4.5.0, 4.4.0-202206
   */
  MULE_PRINT_DETAILED_COMPOSITE_EXCEPTION_LOG(
      "When enabled, AbstractForkJoinRouter based processors, such as ParallelForEach and ScatterGather routers, will show detailed error information for their failed routes.",
      "W-10965130", "4.5.0", MULE_PRINT_DETAILED_COMPOSITE_EXCEPTION_LOG_PROPERTY),


  /**
   * When enabled, the operation policy's error resolution is ignored so that the error mappings of the processor on which the
   * policy was applied are set successfully
   *
   * @since 4.5.0, 4.4.0-202207, 4.3.0-202207
   */
  HONOUR_ERROR_MAPPINGS_WHEN_POLICY_APPLIED_ON_OPERATION(
      "When enabled, the operation policy's error resolution is ignored so that the error mappings of the processor on which the policy was applied are set successfully",
      "W-11147961", "4.5.0", HONOUR_ERROR_MAPPINGS_WHEN_POLICY_APPLIED_ON_OPERATION_PROPERTY),

  /**
   * <p>
   * When enabled, error suppression occurs. This feature prevents component such as the Web Service Consumer connector and the
   * Until Successful scope from reporting errors outside their namespaces.
   * </p>
   * <p>
   * Log extract for a connectivity error at the Web Service Consumer (HTTP:CONNECTIVITY is being suppressed):
   * </p>
   * 
   * <pre>
   * Error type : WSC:INVALID_WSDL
   * Caused by  : HTTP:CONNECTIVITY
   * </pre>
   * <p>
   * Suppressed errors are treated as underlying causes that can also be matched by On Error handlers.
   * </p>
   *
   * @since 4.5.0, 4.4.0-202210
   */
  SUPPRESS_ERRORS(
      "When enabled, error suppression will happen. This will affect, for example, the Web Service Consumer connector and the Until Successful scope that will always report errors from their corresponding namespaces (MULE and WSC). Suppressed errors will be treated as underlying causes.",
      "W-11308645", "4.5.0, 4.4.0-202210, 4.3.0-202210", SUPPRESS_ERRORS_PROPERTY),

  /**
   * When enabled, internal exceptions when processing an event in the 'idempotent-message-validator' will be rethrown instead of
   * raising a general MULE:DUPLICATE_MESSAGE.
   *
   * @since 4.5.0
   */
  RETHROW_EXCEPTIONS_IN_IDEMPOTENT_MESSAGE_VALIDATOR(
      "When enabled, internal exceptions when processing an event in the 'idempotent-message-validator' will be rethrown instead of throwing a general MULE:DUPLICATE_MESSAGE.",
      "W-11529823", "4.5.0", RETHROW_EXCEPTIONS_IN_IDEMPOTENT_MESSAGE_VALIDATOR_PROPERTY),

  /**
   * When enabled, the 'foreach' router will generate an {@link IllegalArgumentException} if the collection expression evaluates
   * to a {@link java.util.Map}.
   *
   * @since 4.5.0
   */
  FOREACH_ROUTER_REJECTS_MAP_EXPRESSIONS(
      "When enabled, the 'foreach' router will generate an IllegalArgumentException if the collection expression evaluates to a java.util.Map.",
      "W-12207110", "4.5.0", FOREACH_ROUTER_REJECTS_MAP_EXPRESSIONS_PROPERTY),

  /**
   * When enabled, the insecure attribute of the trust-store element will be honoured even when other attributes are configured.
   *
   * @since 4.5.0
   */
  HONOUR_INSECURE_TLS_CONFIGURATION(
      "When enabled, the insecure TLS configuration will be honoured even if there are fields of the TrustStore configured.",
      "W-10822938", "4.5.0", HONOUR_INSECURE_TLS_CONFIGURATION_PROPERTY),

  /**
   * When enabled, flux sinks will be cached using index as part of the key. If a sink is already in use, new sink will be created
   * to avoid deadlock.
   *
   * @since 4.4.0
   */
  USE_TRANSACTION_SINK_INDEX(
      "When enabled, flux sinks will be cached using index as part of the key. If a sink is already in use, new sink will be created\n"
          +
          "to avoid deadlock.",
      "W-12128703", "4.0.0, 4.1.0, 4.2.0, 4.3.0, 4.4.0, 4.5.0", USE_TRANSACTION_SINK_INDEX_PROPERTY),

  /**
   * When enabled, if an application access a native library, the rest of its declared native libraries are also loaded. This
   * prevents errors like UnsatisfiedLinkError when the accessed native library depends on another native library. Libraries must
   * be declared at the sharedLibraries configuration following the dependency order, meaning that if library A depends on library
   * B, then A must be declared first. Declaring the native libraries at a domain is also supported.
   *
   * @since 4.5.0, 4.4.0-202305
   */
  SUPPORT_NATIVE_LIBRARY_DEPENDENCIES(
      "When enabled, if an application access a native library, the rest of its declared native libraries are also loaded. This prevents errors like UnsatisfiedLinkError when the accessed native library depends on another native library. Libraries must be declared at the sharedLibraries configuration following the dependency order, meaning that if library A depends on library B, then A must be declared first. Declaring the native libraries at a domain is also supported.",
      "W-11855052",
      "4.5.0",
      SUPPORT_NATIVE_LIBRARY_DEPENDENCIES_PROPERTY),
  /**
   * When enabled, the trace id and span id will be added to the MDC when available.
   *
   * @since 4.5.0
   */
  PUT_TRACE_ID_AND_SPAN_ID_IN_MDC("When enabled, the trace id and span id will be added to the MDC when available.",
      "W-12979787", "4.5.0", PUT_TRACE_ID_AND_SPAN_ID_IN_MDC_PROPERTY),
  /**
   * When enabled the ancestor-mule-span-id value will be added in the trace state when a span is propagated.
   *
   * @since 4.5.0
   */
  ADD_MULE_SPECIFIC_TRACING_INFORMATION_IN_TRACE_STATE(
      "When enabled the ancestor-mule-span-id value will be added in the trace state when a span is propagated.",
      "W-13215870", "", ADD_MULE_SPECIFIC_TRACING_INFORMATION_IN_TRACE_STATE_PROPERTY),

  /**
   * "When enabled, a new (Source) Policy Context is created for the execution of parallel scopes: ParallelForeach, ScatterGather
   * and Async"
   * 
   * @since 4.5.0, 4.4.0-202306
   */
  CREATE_CHILD_POLICY_CONTEXT_FOR_PARALLEL_SCOPES(
      "When enabled, a new (Source) Policy Context is created for the execution of parallel scopes: ParallelForeach, ScatterGather and Async",
      "W-13509911", "4.5.0", CREATE_CHILD_POLICY_CONTEXT_FOR_PARALLEL_SCOPES_PROPERTY),

  /**
   * When enabled, MBeans will not be registered for commons-pool2.
   *
   * @since 4.6.0
   */
  DISABLE_JMX_FOR_COMMONS_POOL2(
      "When enabled, MBeans will not be registered for commons-pool2.",
      "W-12422473", "4.6.0", DISABLE_JMX_FOR_COMMONS_POOL2_PROPERTY),

  /**
   * When enabled, the Scheduler does not log exceptions.
   *
   * @since 4.6.0
   */
  DISABLE_SCHEDULER_LOGGING(
      "When enabled, the Scheduler does not log exceptions.",
      "W-13881167", "4.6.0", DISABLE_SCHEDULER_LOGGING_PROPERTY),

  /**
   * When enabled, when a (local or xa) transaction reached timeout, an error will be thrown that can be handled using error
   * handling.
   *
   * @since 4.6.1
   */
  ERROR_AND_ROLLBACK_TX_WHEN_TIMEOUT(
      "When enabled, when a (local or xa) transaction reached timeout, an error will be thrown that can be handled using error handling",
      "W-14608096", "4.6.1", ERROR_AND_ROLLBACK_TX_WHEN_TIMEOUT_PROPERTY),

  /**
   * When enabled, implicit configuration for the XML SDK won't be created.
   *
   * @since 4.7.0
   */
  DISABLE_XML_SDK_IMPLICIT_CONFIGURATION_CREATION(
      "When enabled, implicit configuration for the XML SDK won't be created.",
      "W-10634129", "4.7,0", MULE_DISABLE_XML_SDK_IMPLICIT_CONFIGURATION_CREATION),

  /**
   * When enabled, the MDC context will reset after XML SDK Operation has been executed.
   *
   * @since 4.8.0, 4.7.1, 4.6.4, 4.4.0-202406
   */
  ENABLE_XML_SDK_MDC_RESET(
      "When enabled, the MDC context will reset after XML SDK Operation has been executed.",
      "W-15206528", "4.8.0", ENABLE_XML_SDK_MDC_RESET_PROPERTY),

  /**
   * When enabled, org.mule.runtime.extension.api.client.ExtensionsClient deprecated methods (executeAsync(String, String,
   * OperationParameters) and execute(String, String, OperationParameters)) will throw an UnsupportedOperationException.
   * 
   * @since 1.8
   */
  UNSUPPORTED_EXTENSIONS_CLIENT_RUN_ASYNC(
      "When enabled, org.mule.runtime.extension.api.client.ExtensionsClient deprecated methods (executeAsync(String, String, OperationParameters) and execute(String, String, OperationParameters)) will throw an UnsupportedOperationException.",
      "W-15399821", "4.8.0", MULE_UNSUPPORTED_EXTENSIONS_CLIENT_RUN_ASYNC),

  /**
   * When enabled, root element of `import` targets will validated to be the same as the importing config.
   *
   * @since 4.8
   */
  ENFORCE_IMPORT_TARGET_SAME_TYPE(
      "When enabled, root element of `import` targets will validated to be the same as the importing config.",
      "W-15509819",
      "4.8.0"),

  /**
   * When enabled, flows will honour the state configured in flows.deployment.properties when restarting the app, regardless of
   * the initial state. This will only apply to Standalone/Hybrid deployments and has no effect in CloudHub.
   *
   * @since 4.8
   */
  HONOUR_PERSISTED_FLOW_STATE(
      "When enabled, flows will honour the state configured in flows.deployment.properties when restarting the app, regardless of the initial state. "
          + "This will only apply to Standalone/Hybrid deployments and has no effect in CloudHub.",
      "W-15750334",
      "4.8.0", HONOUR_PERSISTED_FLOW_STATE_PROPERTY);

  private final String description;
  private final String issueId;
  private final String enabledByDefaultSince;
  private final String overridingSystemPropertyName;

  /**
   * @param description           Description of the feature. This description will be part of end user documentation.
   * @param issueId               The issue that caused this feature addition. For instance, <code>MULE-1234</code>.
   * @param enabledByDefaultSince See {@link Feature#getEnabledByDefaultSince()}.
   */
  MuleRuntimeFeature(String description, String issueId, String enabledByDefaultSince) {
    this(description, issueId, enabledByDefaultSince, null);
  }

  /**
   * @param description                  Description of the feature. This description will be part of end user documentation.
   * @param issueId                      The issue that caused this feature addition. For instance, <code>MULE-1234</code>.
   * @param enabledByDefaultSince        See {@link Feature#getEnabledByDefaultSince()}.
   * @param overridingSystemPropertyName See {@link Feature#getOverridingSystemPropertyName()}.
   */
  MuleRuntimeFeature(String description, String issueId, String enabledByDefaultSince, String overridingSystemPropertyName) {
    this.description = description;
    this.issueId = issueId;
    this.enabledByDefaultSince = enabledByDefaultSince;
    this.overridingSystemPropertyName = overridingSystemPropertyName;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public String getIssueId() {
    return issueId;
  }

  @Override
  public String getSince() {
    return getEnabledByDefaultSince();
  }

  @Override
  public String getEnabledByDefaultSince() {
    return enabledByDefaultSince;
  }

  @Override
  public Optional<String> getOverridingSystemPropertyName() {
    return ofNullable(overridingSystemPropertyName);
  }

}
