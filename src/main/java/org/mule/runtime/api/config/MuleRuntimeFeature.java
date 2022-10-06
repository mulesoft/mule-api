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
import static org.mule.runtime.api.util.MuleSystemProperties.ENABLE_BYTE_BUDDY_OBJECT_CREATION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ENABLE_POLICY_ISOLATION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.HONOUR_INSECURE_TLS_CONFIGURATION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.HONOUR_RESERVED_PROPERTIES_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.HONOUR_ERROR_MAPPINGS_WHEN_POLICY_APPLIED_ON_OPERATION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.PARALLEL_FOREACH_FLATTEN_MESSAGE_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.SET_VARIABLE_WITH_NULL_VALUE_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.START_EXTENSION_COMPONENTS_WITH_ARTIFACT_CLASSLOADER_PROPERTY;

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
      " If set to true, the connection errors will be computed to trigger alerts.",
      "MULE-19020",
      "4.4.0, 4.3.1", COMPUTE_CONNECTION_ERRORS_IN_STATS_PROPERTY),

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
   * When enabled, the default error handler added by the runtime will not rollback a transaction that should not be rollback by
   * it
   *
   * @since 4.4.0-202201
   */
  DEFAULT_ERROR_HANDLER_NOT_ROLLBACK_IF_NOT_CORRESPONDING(
      "When enabled, the default error handler added by the runtime will not rollback a transaction that should not be rollback by it",
      "MULE-19919", "4.4.1, 4.3.1", DEFAULT_ERROR_HANDLER_NOT_ROLLBACK_IF_NOT_CORRESPONDING_PROPERTY),

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
      "W-10672687", "4.5.0", ENABLE_BYTE_BUDDY_OBJECT_CREATION_PROPERTY),

  /**
   * When set to true, the operation policy's error resolution is ignored so that the error mappings of the processor on which the
   * policy was applied are set successfully
   *
   * @since 4.5.0, 4.4.0-202207, 4.3.0-202207
   */
  HONOUR_ERROR_MAPPINGS_WHEN_POLICY_APPLIED_ON_OPERATION(
      "When set to true, the operation policy's error resolution is ignored so that the error mappings of the processor on which the policy was applied are set successfully",
      "W-11147961", "4.5.0", HONOUR_ERROR_MAPPINGS_WHEN_POLICY_APPLIED_ON_OPERATION_PROPERTY),

  /**
   * When enabled, the insecure attribute of the trust-store element will be honoured even when other attributes are configured.
   *
   * @since 4.5.0
   */
  HONOUR_INSECURE_TLS_CONFIGURATION(
      "When enabled, the insecure TLS configuration will be honoured even if there are fields of the TrustStore configured.",
      "W-10822938", "4.5.0", HONOUR_INSECURE_TLS_CONFIGURATION_PROPERTY);

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


