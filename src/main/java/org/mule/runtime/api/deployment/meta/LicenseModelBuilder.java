/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.meta;

/**
 * A builder to create instances of {@link LicenseModel}.
 *
 * @since 1.0
 */
public final class LicenseModelBuilder {

  private String requiredEntitlement;
  private String provider;
  private boolean allowsEvaluationLicense = true;

  /**
   * @param requiredEntitlement the required entitlement in the license
   * @return {@code this} builder
   */
  public LicenseModelBuilder setRequiredEntitlement(String requiredEntitlement) {
    this.requiredEntitlement = requiredEntitlement;
    return this;
  }

  /**
   * @param provider the provider of the license
   * @return {@code this} builder
   */
  public LicenseModelBuilder setProvider(String provider) {
    this.provider = provider;
    return this;
  }

  /**
   * @param allowsEvaluationLicense true if the extension allows to be run using the evaluation license, false otherwise
   * @return {@code this} builder
   */
  public LicenseModelBuilder setAllowsEvaluationLicense(boolean allowsEvaluationLicense) {
    this.allowsEvaluationLicense = allowsEvaluationLicense;
    return this;
  }

  /**
   * Creates and returns a new {@link MuleArtifactLoaderDescriptor} according to the values set
   *
   * @return a {@link MuleArtifactLoaderDescriptor}
   */
  public LicenseModel build() {
    return new LicenseModel(requiredEntitlement, provider, allowsEvaluationLicense);
  }
}
