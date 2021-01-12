/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.config;

import java.util.Optional;

/**
 * A feature that can be enabled or disabled according to a certain configuration
 * 
 * @since 4.4.0 4.3.1 4.2.3
 */
public interface Feature {

  /**
   * Brief description of the feature.
   * 
   * @return The feature description
   */
  String getDescription();

  /**
   * The issue that caused this feature was added. For instance <code>MULE-1234</code>
   * 
   * @return Issue that motivated the feature.
   */
  String getIssueId();


  /**
   * A comma-separated list of versions since this feature exists.
   * 
   * @return A comma-separated list of versions, ex <code>"4.4.0, 4.3.0"</code>
   */
  String getSince();

  /**
   * <p>
   * System Property name to be used to decide whether the feature has to be enabled or disabled for the whole Runtime instance
   * without considering any other condition related to the deployment context. The feature will be enabled according to the value
   * provided, by parsing it as a boolean.
   * </p>
   * 
   * <p>
   * For instance if {@link #getOverridingSystemPropertyName()}:
   * </p>
   * <ol>
   * <li>Is set to a non-null value and there is a System Property defined with this name, then the feature will be enabled or
   * disabled depending on the result of parse the property value.
   * <li>Is not set <strong>or</strong> there isn't a System Property defined with a the value assigned to it, then the proper
   * feature flagging criteria will be applied according to how it was configured.</li>
   * </ol>
   * 
   * <p>
   * In other words:
   * </p>
   * 
   * <ol>
   * <li>If {@link #getOverridingSystemPropertyName} is set to <code>some.meaningful.name</code>
   * <ol>
   * <li>If the System Property was set (<code>System.getProperty("some.meaningful.name">)</code> <strong>is not null</strong>),
   * then the feature will be enabled only when <code>Boolean.getBoolean("some.meaningful.name")</code> returns
   * <code>true</code></li>
   * <li>If the System Property was not set (<code>System.getProperty("some.meaningful.name">)</code> <strong>is null</strong>),
   * then the feature flagging configuration will be applied to decide whether the feature is enabled or disabled.</li>
   * </ol>
   * </li>
   * <li>If {@link #getOverridingSystemPropertyName()} is not set, then the feature flagging configuration will be applied to
   * decide whether the feature is enabled or disabled.</li></li>
   * </ol>
   * 
   *
   * @return The name of a System Property to configure the feature.
   */
  Optional<String> getOverridingSystemPropertyName();
}
