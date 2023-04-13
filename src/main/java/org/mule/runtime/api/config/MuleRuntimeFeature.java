/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.config;

import static org.mule.runtime.api.util.MuleSystemProperties.BATCH_FIXED_AGGREGATOR_TRANSACTION_RECORD_BUFFER_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.COMPUTE_CONNECTION_ERRORS_IN_STATS_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.DEFAULT_ERROR_HANDLER_NOT_ROLLBACK_IF_NOT_CORRESPONDING_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.DW_REMOVE_SHADOWED_IMPLICIT_INPUTS_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ENABLE_BYTE_BUDDY_OBJECT_CREATION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ENABLE_POLICY_ISOLATION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ENABLE_PROFILING_SERVICE_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ENTITY_RESOLVER_FAIL_ON_FIRST_ERROR_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.FORCE_RUNTIME_PROFILING_CONSUMERS_ENABLEMENT_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.HANDLE_SPLITTER_EXCEPTION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.HONOUR_INSECURE_TLS_CONFIGURATION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.HONOUR_RESERVED_PROPERTIES_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.HONOUR_ERROR_MAPPINGS_WHEN_POLICY_APPLIED_ON_OPERATION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.MULE_PRINT_DETAILED_COMPOSITE_EXCEPTION_LOG_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.PARALLEL_FOREACH_FLATTEN_MESSAGE_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.SUPPORT_NATIVE_LIBRARY_DEPENDENCIES_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.RETHROW_EXCEPTIONS_IN_IDEMPOTENT_MESSAGE_VALIDATOR_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.SET_VARIABLE_WITH_NULL_VALUE_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.START_EXTENSION_COMPONENTS_WITH_ARTIFACT_CLASSLOADER_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.SUPPRESS_ERRORS_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.TO_STRING_TRANSFORMER_TRANSFORM_ITERATOR_ELEMENTS_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.USE_TRANSACTION_SINK_INDEX_PROPERTY;

import static java.util.Optional.ofNullable;

import org.mule.runtime.api.util.MuleSystemProperties;

import java.util.Optional;

/**
 * <p>
 * List of {@link Feature}s that can be configured to be enabled or disabled per application depending on the deployment context.
 * </p>
 *
 * <p>
 * When some Mule runtime feature needs to be flagged, it should be added here as a new enum constant. Each entry must have a
 * meaningful name, clear enough to understand what it represents, a description with enough information to know how the runtime
 * will work whether the feature is enabled or disabled, the issue that motivated this feature, and a list of Runtime versions
 * from when it exists. Each enum constant has to have its Javadoc with further information about how the feature is configured.
 * </p>
 *
 * <p>
 * For example:
 * </p>
 *
 * <pre>
 *    public enum MuleRuntimeFeature implements Feature {
 *      ...
 *
 *     {@code /}**
 *       * An application shouldn't override reserved properties, but it was possible until MULE-17659. This behaviour has
 *       * to be fixed by default to any application developed for 4.3.0+ Runtime but can be overridden by setting
 *       * &lcub;@link MuleSystemProperties#HONOUR_RESERVED_PROPERTIES_PROPERTY&rcub; System Property.
 *       *
 *       * @since 4.4.0, 4.3.1
 *       *
 *      {@code *}/
 *      HONOUR_RESERVED_PROPERTIES("Whether reserved properties such as 'app.name' can't be overridden by global properties.",
 *            "MULE-19083", "4.4.0, 4.3.1", HONOUR_RESERVED_PROPERTIES_PROPERTY),
 *
 *      ...
 *
 *    }
 * </pre>
 *
 * @since 4.4.0 4.3.1
 */
public enum MuleRuntimeFeature implements Feature {

  /**
   * An application shouldn't override reserved properties, but it was possible until MULE-17659. This behaviour has to be fixed
   * by default to any application developed for 4.3.0+ Runtime but can be overridden by setting
   * {@link MuleSystemProperties#HONOUR_RESERVED_PROPERTIES_PROPERTY} System Property.
   *
   * @since 4.4.0, 4.3.1
   */
  HONOUR_RESERVED_PROPERTIES("Whether reserved properties such as 'app.name' can't be overridden by global properties.",
      "MULE-19038", "4.4.0, 4.3.1", HONOUR_RESERVED_PROPERTIES_PROPERTY),

  /**
   * If set to true, then DW will correctly handle Splitter's exceptions, avoiding some serialization issues. Be aware that when
   * enabled, this can make {@code error.cause} return a different exception. For more information see DW-383.
   *
   * @since 4.4.0, 4.3.1, 4.2.3
   */
  HANDLE_SPLITTER_EXCEPTION(
      "If set to true, then DW will correctly handle Splitter's exceptions, avoiding some serialization issues.",
      "DW-383",
      "4.4.0, 4.3.1, 4.2.3", HANDLE_SPLITTER_EXCEPTION_PROPERTY),

  /**
   * If set to true, then fixed batch aggregator will only commit when a full block is processed. For more information see EE-7443
   *
   * @since 4.4.0, 4.3.1, 4.2.3
   */
  BATCH_FIXED_AGGREGATOR_TRANSACTION_RECORD_BUFFER(
      "If set to true, then fixed batch aggregator will only commit when a full block is processed.",
      "EE-7443",
      "4.4.0, 4.3.1, 4.2.3", BATCH_FIXED_AGGREGATOR_TRANSACTION_RECORD_BUFFER_PROPERTY),

  /**
   * If set to true, the connection errors will be taken into account to trigger alerts.
   *
   * @since 4.4.0, 4.3.1
   */
  COMPUTE_CONNECTION_ERRORS_IN_STATS(
      "If set to true, the connection errors will be computed to trigger alerts.",
      "DW-383",
      "4.4.0, 4.3.1", COMPUTE_CONNECTION_ERRORS_IN_STATS_PROPERTY),

  /**
   * If set to true, managed iterators transformed to Strings will show the representation of the elements instead of the generic
   * 'org.mule.runtime.core.internal.streaming.object.ManagedCursorIteratorProvider$ManagedCursorIterator@######'.
   *
   * @since 4.4.0
   */
  TO_STRING_TRANSFORMER_TRANSFORM_ITERATOR_ELEMENTS(
      "If set to true, managed iterators transformed to Strings will show the representation of the elements instead of the generic 'org.mule.runtime.core.internal.streaming.object.ManagedCursorIteratorProvider$ManagedCursorIterator@######'.",
      "MULE-19323",
      "4.4.0",
      TO_STRING_TRANSFORMER_TRANSFORM_ITERATOR_ELEMENTS_PROPERTY),

  /**
   * If set to true, extensions imported by a policy will be managed in complete isolation from the extensions imported by the
   * application that is being applied to, and validations will prevent the usage of explicit configurations declared by the
   * application as part of the policy initialization."
   *
   * @since 4.4.0, 4.3.1
   */
  ENABLE_POLICY_ISOLATION(
      "If set to true, extensions imported by a policy will be managed in complete isolation from the extensions imported by the application that is being applied to, and validations will prevent the usage of explicit configurations declared by the application as part of the policy initialization.",
      "MULE-19226",
      "4.4.0, 4.3.1", ENABLE_POLICY_ISOLATION_PROPERTY),

  /**
   * If set to true, the Mule XML DSL parser will fail when deploying an application that declares a schema that cannot be
   * located. If set to false, it will only fail if such application makes use of the namespace that such schema defines, instead
   * of just declaring it.
   *
   * @since 4.4.0
   */
  ENTITY_RESOLVER_FAIL_ON_FIRST_ERROR(
      "If set to true, the Mule XML DSL parser will fail when deploying an application that declares a schema that cannot be located. If set to false, it will only fail if such application makes use of the namespace that such schema defines, instead of just declaring it.",
      "EE-7827",
      "4.4.0",
      ENTITY_RESOLVER_FAIL_ON_FIRST_ERROR_PROPERTY),

  /**
   * If set to true, profiling events will be produced.
   *
   * @since 4.4.0
   */
  ENABLE_PROFILING_SERVICE(
      "If set to true, profiling events will be produced",
      "MULE-19588",
      "4.5.0",
      ENABLE_PROFILING_SERVICE_PROPERTY),

  SET_VARIABLE_WITH_NULL_VALUE(
      "If set to true, the set variable will create a variable even if the value is null",
      "MULE-19443",
      "4.4.0",
      SET_VARIABLE_WITH_NULL_VALUE_PROPERTY),

  /**
   * If set to true, extensions will only be able to load exported resources from the deployable artifacts (application, policy,
   * domain).
   *
   * @since 4.4.0
   */
  START_EXTENSION_COMPONENTS_WITH_ARTIFACT_CLASSLOADER(
      "When enabled, extensions will only be able to load exported resources from the deployable artifacts (application, policy, domain).",
      "MULE-19815",
      "4.4.0",
      START_EXTENSION_COMPONENTS_WITH_ARTIFACT_CLASSLOADER_PROPERTY),

  /**
   * When enabled, DataWeave will remove implicit inputs when a variable with the same name is declared at the root level.
   *
   * @since 4.4.0-202111
   */
  DW_REMOVE_SHADOWED_IMPLICIT_INPUTS(
      "When enabled, DataWeave will remove implicit inputs when a variable with the same name is declared at the root level.",
      "DW-893",
      "4.4.0", DW_REMOVE_SHADOWED_IMPLICIT_INPUTS_PROPERTY),

  /**
   * When enabled, the default error handler added by the runtime will not rollback a transaction that should not be rollback by
   * it
   *
   * @since 4.4.0-202201
   */
  DEFAULT_ERROR_HANDLER_NOT_ROLLBACK_IF_NOT_CORRESPONDING(
      "When enabled, the default error handler added by the runtime will not rollback a transaction that should not be rollback by it",
      "MULE-19919", "4.5.0, 4.4.1", DEFAULT_ERROR_HANDLER_NOT_ROLLBACK_IF_NOT_CORRESPONDING_PROPERTY),

  FORCE_RUNTIME_PROFILING_CONSUMERS_ENABLEMENT(
      "When enabled, profiling consumers implemented by the runtime will be enabled by default.",
      "MULE-19967", "", FORCE_RUNTIME_PROFILING_CONSUMERS_ENABLEMENT_PROPERTY),


  /**
   * When enabled, if the items to iterate over on a parallel-foreach scope are messages (such as the output of an operation that
   * returns Result objects), they will be flattened in a way that is consistent with what the foreach scope does.
   *
   * @since 4.3.0-202203
   */
  PARALLEL_FOREACH_FLATTEN_MESSAGE(
      "When enabled, if the items to iterate over on a parallel-foreach scope are messages (such as the output of an operation that returns Result objects), they will be flattened in a way that is consistent with what the foreach scope does.",
      "MULE-20067", "4.5.0", PARALLEL_FOREACH_FLATTEN_MESSAGE_PROPERTY),

  /**
   * When enabled, the Objects factories will be created with Byte Buddy instead of CGLIB.
   *
   * @since 4.5.0, 4.4.0-202204, 4.3.0-202204
   */
  // TODO W-10815440 Remove this feature flag along with the work for W-10815440.
  ENABLE_BYTE_BUDDY_OBJECT_CREATION(
      "When enabled, the Objects factories will be created with Byte Buddy instead of CGLIB.",
      "W-10672687", "4.4.0", ENABLE_BYTE_BUDDY_OBJECT_CREATION_PROPERTY),

  /**
   * When enabled, AbstractForkJoinRouter based processors, such as ParallelForEach and ScatterGather routers, will show detailed
   * error information for their failed routes.
   *
   * @since 4.5.0, 4.4.0-202207
   */
  MULE_PRINT_DETAILED_COMPOSITE_EXCEPTION_LOG(
      "When enabled, AbstractForkJoinRouter based processors, such as ParallelForEach and ScatterGather routers, will show detailed error information for their failed routes.",
      "W-10965130", "4.5.0", MULE_PRINT_DETAILED_COMPOSITE_EXCEPTION_LOG_PROPERTY),


  /**
   * When set to true, the operation policy's error resolution is ignored so that the error mappings of the processor on which the
   * policy was applied are set successfully
   *
   * @since 4.5.0, 4.4.0-202207, 4.3.0-202207
   */
  HONOUR_ERROR_MAPPINGS_WHEN_POLICY_APPLIED_ON_OPERATION(
      "When enabled, the operation policy's error resolution is ignored so that the error mappings of the processor on which the policy was applied are set successfully",
      "W-11147961", "4.5.0", HONOUR_ERROR_MAPPINGS_WHEN_POLICY_APPLIED_ON_OPERATION_PROPERTY),

  /**
   * When enabled, error suppression will happen. This will affect, for instance, the Web Service Consumer connector and the Until
   * Successful scope that will always report errors from their corresponding namespaces (MULE and WSC). Suppressed errors will be
   * treated as underlying causes.
   *
   * @since 4.5.0, 4.4.0-202210, 4.3.0-202210
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
   * When enabled, and the application needs to load a native library, the rest of the native libraries are preloaded in the
   * application's Classloader.
   *
   * @since 4.5.0, 4.4.0-202305
   */
  SUPPORT_NATIVE_LIBRARY_DEPENDENCIES(
      "When enabled, and the application needs to load a native library, the rest of the native libraries are preloaded in the application's Classloader.",
      "W-11855052",
      "4.6.0",
      SUPPORT_NATIVE_LIBRARY_DEPENDENCIES_PROPERTY);

  private final String description;
  private final String issueId;
  private final String since;
  private final String overridingSystemPropertyName;

  MuleRuntimeFeature(String description, String issue, String since) {
    this(description, issue, since, null);
  }

  MuleRuntimeFeature(String description, String issueId, String since, String overridingSystemPropertyName) {
    this.description = description;
    this.issueId = issueId;
    this.since = since;
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
    return since;
  }

  @Override
  public Optional<String> getOverridingSystemPropertyName() {
    return ofNullable(overridingSystemPropertyName);
  }

}


