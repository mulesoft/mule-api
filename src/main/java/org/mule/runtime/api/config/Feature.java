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
 * @since 4.4.0 4.3.1
 */
public interface Feature {

  /**
   * Brief description of the feature.
   * 
   * @return The feature description
   */
  String getDescription();

  /**
   * The issue that caused this feature was added.  For example <code>MULE-1234</code>
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
   * Name of a System Property to be used if the feature has to be enabled or disabled for the whole Runtime instance doesn't
   * matter any other condition related to the deployment context.
   * </p>
   * 
   * <p>
   * For example if it is set with <code>mule.enable.my.feature</code> and there is a System Property with this name, then its
   * value will be parsed as a <code>Boolean.getValue("mule.enable.my.feature")</code> to decide whether the feature is enabled.
   * If there isn't a System Property with this name, then the proper feature flagging criteria will be applied according to how
   * it was configured.
   * </p>
   * 
   * @return The name of a System Property to configure the feature.
   */
  Optional<String> getOverridingSystemPropertyName();
}
