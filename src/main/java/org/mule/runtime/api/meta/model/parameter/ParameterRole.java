/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.parameter;

/**
 * Represents the possible roles that each parameter plays in the context of a {@link ParameterizedModel}
 *
 * @since 1.0
 */
public enum ParameterRole {

  /**
   * Indicates that the parameter is only configuring the component's behaviour and doesn't represent data that is being sent to
   * another endpoint
   */
  BEHAVIOUR,

  /**
   * Indicates that this parameter holds information to be considered content to be sent to a different system or endpoint. It
   * makes sense to use this when the operation has many content parameters but this one is not the {@link #PRIMARY_CONTENT}
   */
  CONTENT,

  /**
   * Indicates that this parameter holds the main piece of content to be sent to another system or endpoint. This differentiation
   * becomes specially important when the component has many content parameters.
   * <p>
   * If the component only has one content parameter, then this role will be used to mark such parameter
   */
  PRIMARY_CONTENT
}
