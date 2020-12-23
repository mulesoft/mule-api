/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.config;

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
}
