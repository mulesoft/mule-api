/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.meta;

import static java.util.Optional.ofNullable;

import java.util.Optional;

/**
 * Model for the license entitlement configuration.
 *
 * @since 1.0
 */
public final class LicenseModel {

  private final String requiredEntitlement;
  private final String provider;
  private final boolean allowsEvaluation;

  /**
   * Creates an immutable implementation of {@link LicenseModel}
   *
   * @param requiredEntitlement the required entitlement. May be null.
   * @param provider            the provider of the license.
   * @param allowsEvaluation    true if it allows execution with an evaluation license, false otherwise.
   */
  public LicenseModel(String requiredEntitlement, String provider, boolean allowsEvaluation) {
    this.requiredEntitlement = requiredEntitlement;
    this.provider = provider;
    this.allowsEvaluation = allowsEvaluation;
  }

  /**
   * @return the required entitlement.
   */
  public Optional<String> getRequiredEntitlement() {
    return ofNullable(requiredEntitlement);
  }

  /**
   * @return the provider of the license.
   */
  public String getProvider() {
    return provider;
  }

  /**
   * @return true if the artifact can be run using the evaluation license, false otherwise.
   */
  public boolean isAllowsEvaluation() {
    return allowsEvaluation;
  }
}
