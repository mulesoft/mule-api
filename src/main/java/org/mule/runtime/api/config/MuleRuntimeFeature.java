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
      SET_VARIABLE_WITH_NULL_VALUE_PROPERTY);

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


