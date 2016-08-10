/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

/**
 * Immutable implementation of {@link ComponentId} for identifying Message Sources
 *
 * @since 1.0
 */
public class SourceId implements ComponentId {

  private static final String SOURCE_ID = "-1";
  private final String flowName;

  public SourceId(String flowName) {
    this.flowName = flowName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getFlowName() {
    return flowName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getComponentPath() {
    return SOURCE_ID;
  }

  @Override
  public String toString() {
    return "SourceId{" +
        "flowName='" + flowName + '\'' +
        ", executablePath=" + SOURCE_ID +
        '}';
  }
}
