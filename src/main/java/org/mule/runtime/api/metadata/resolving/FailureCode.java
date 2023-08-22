/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.resolving;

/**
 * Standard error codes to describe common errors that may occur during Metadata fetching operations
 *
 * @since 1.0
 */
public final class FailureCode {

  public static final FailureCode NO_DYNAMIC_TYPE_AVAILABLE = new FailureCode("NO_DYNAMIC_TYPE_AVAILABLE");
  public static final FailureCode NO_DYNAMIC_KEY_AVAILABLE = new FailureCode("NO_DYNAMIC_KEY_AVAILABLE");
  public static final FailureCode INVALID_CONFIGURATION = new FailureCode("INVALID_CONFIGURATION");
  public static final FailureCode RESOURCE_UNAVAILABLE = new FailureCode("RESOURCE_UNAVAILABLE");
  public static final FailureCode INVALID_METADATA_KEY = new FailureCode("INVALID_METADATA_KEY");
  public static final FailureCode INVALID_CREDENTIALS = new FailureCode("INVALID_CREDENTIALS");
  public static final FailureCode CONNECTION_FAILURE = new FailureCode("CONNECTION_FAILURE");
  public static final FailureCode NOT_AUTHORIZED = new FailureCode("NOT_AUTHORIZED");
  public static final FailureCode COMPONENT_NOT_FOUND = new FailureCode("COMPONENT_NOT_FOUND");
  public static final FailureCode APPLICATION_NOT_FOUND = new FailureCode("APPLICATION_NOT_FOUND");
  public static final FailureCode UNKNOWN = new FailureCode("UNKNOWN");
  public static final FailureCode NONE = new FailureCode("NO_ERROR");
  public static final FailureCode NO_DYNAMIC_METADATA_AVAILABLE = new FailureCode("NO_DYNAMIC_METADATA_AVAILABLE");

  private String name;

  public FailureCode(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof FailureCode && this.getName().equals(((FailureCode) obj).getName());
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return getName();
  }
}
