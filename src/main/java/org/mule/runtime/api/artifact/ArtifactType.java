/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.artifact;

/**
 * Provides values for different types of artifacts that a Mule container may be able to handle.
 * 
 * @since 1.9
 */
public enum ArtifactType {

  APP("app", true),

  DOMAIN("domain", true),

  PLUGIN("plugin", false),

  POLICY("policy", true),

  MULE_EXTENSION("muleExtension", false),

  SERVICE("service", false),

  SERVER_PLUGIN("serverPlugin", false);

  private final String artifactTypeAsString;
  private final boolean deployable;

  ArtifactType(String artifactTypeAsString, boolean deployable) {
    this.artifactTypeAsString = artifactTypeAsString;
    this.deployable = deployable;
  }

  public String getArtifactTypeAsString() {
    return artifactTypeAsString;
  }

  /**
   * @return {@code true} if artifacts of this type are deployable units to a Mule container
   */
  public boolean isDeployable() {
    return deployable;
  }

}
