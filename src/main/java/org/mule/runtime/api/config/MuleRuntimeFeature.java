/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.config;

import static java.util.Optional.ofNullable;
import static org.mule.runtime.api.util.MuleSystemProperties.BATCH_FIXED_AGGREGATOR_TRANSACTION_RECORD_BUFFER_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.COMPUTE_CONNECTION_ERRORS_IN_STATS_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ENABLE_PROFILING_SERVICE_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ENABLE_POLICY_ISOLATION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.HANDLE_SPLITTER_EXCEPTION_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.HONOUR_RESERVED_PROPERTIES_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.SET_VARIABLE_WITH_NULL_VALUE_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.TO_STRING_TRANSFORMER_TRANSFORM_ITERATOR_ELEMENTS_PROPERTY;
import static org.mule.runtime.api.util.MuleSystemProperties.ENTITY_RESOLVER_FAIL_ON_FIRST_ERROR_PROPERTY;

import java.util.Optional;

/**
 * <p>
 * List of {@link Feature}s that will be enabled or disabled per application depending on the deployment context.
 * </p>
 *
 * <p>
 * When some Mule runtime feature needs to be flagged, it should be added here as a new enum constant. Each entry must have a
 * meaningful, client oriented name, and must provide values for all the {@link Feature} fields.
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
 *       * @since 4.4.0, 4.3.0-202103
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
      "When enabled, reserved properties such as 'app.name' can't be overridden by global properties (overrides will be ignored).",
      "MULE-17659", "4.4.0, 4.3.1", HONOUR_RESERVED_PROPERTIES_PROPERTY),

  /**
   * When enabled, DataWeave will correctly handle split exceptions, avoiding some serialization issues.
   *
   * @since 4.4.0
   */
  HANDLE_SPLITTER_EXCEPTION(
      "When enabled, DataWeave will correctly handle split exceptions, avoiding some serialization issues. For more information see DW-383.",
      "MULE-19197",
      "4.4.0", HANDLE_SPLITTER_EXCEPTION_PROPERTY),

  /**
   * When enabled, fixed batch aggregators will only commit when a full block is processed. See EE-7443 for more information.
   *
   * @since 4.4.0, 4.3.0-202103, 4.2.2-202103"
   */
  BATCH_FIXED_AGGREGATOR_TRANSACTION_RECORD_BUFFER(
      "When enabled, fixed batch aggregators will only commit when a full block is processed. See EE-7443 for more information.",
      "MULE-19218",
      "4.4.0, 4.3.1, 4.2.3", BATCH_FIXED_AGGREGATOR_TRANSACTION_RECORD_BUFFER_PROPERTY),

  /**
   * When enabled, Connection errors will be computed as part of alerts triggering.
   *
   * @since 4.4.0, 4.3.0-202103
   */
  COMPUTE_CONNECTION_ERRORS_IN_STATS(
      "When enabled, Connection errors will be computed as part of alerts triggering.",
      "MULE-19020",
      "4.4.0, 4.3.1", COMPUTE_CONNECTION_ERRORS_IN_STATS_PROPERTY),

  /**
   * When enabled, managed cursor iterators transformed to Strings will show the representation of the elements instead of the
   * generic 'org.mule.runtime.core.internal.streaming.object.ManagedCursorIteratorProvider$ManagedCursorIterator@######'.
   *
   * @since 4.4.0
   */
  TO_STRING_TRANSFORMER_TRANSFORM_ITERATOR_ELEMENTS(
      "When enabled, managed cursor iterators transformed to Strings will show the representation of the elements instead of the generic 'org.mule.runtime.core.internal.streaming.object.ManagedCursorIteratorProvider$ManagedCursorIterator@######'.",
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
      "When enabled, extensions imported by a policy will be managed in complete isolation from the extensions imported by the application that is being applied to, and validations will prevent the usage of explicit configurations declared by the application as part of the policy initialization.",
      "MULE-19226",
      "4.4.0, 4.3.1", ENABLE_POLICY_ISOLATION_PROPERTY),

  /**
   * When enabled, the Mule XML DSL parser will fail when deploying an application that declares a schema that cannot be located.
   * Otherwise, it will only fail if such application makes use of the namespace that such schema defines instead of just
   * declaring it.
   *
   * @since 4.4.0
   */
  ENTITY_RESOLVER_FAIL_ON_FIRST_ERROR(
      "When enabled, the Mule XML DSL parser will fail when deploying an application that declares a schema that cannot be located. Otherwise, it will only fail if such application makes use of the namespace that such schema defines instead of just declaring it.",
      "EE-7827",
      "4.4.0",
      ENTITY_RESOLVER_FAIL_ON_FIRST_ERROR_PROPERTY),

  /**
   * When enabled, profiling data will be emitted.
   *
   * @since 4.4.0
   */
  ENABLE_PROFILING_SERVICE(
      "When enabled, profiling data will be emitted",
      "MULE-19588",
      "4.5.0",
      ENABLE_PROFILING_SERVICE_PROPERTY),

  SET_VARIABLE_WITH_NULL_VALUE(
      "When enabled, the set variable will create a variable even if the value is null",
      "MULE-19443",
      "4.4.0",
      SET_VARIABLE_WITH_NULL_VALUE_PROPERTY);

  private final String description;
  private final String issueId;
  private final String enabledByDefaultSince;
  private final String overridingSystemPropertyName;

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


