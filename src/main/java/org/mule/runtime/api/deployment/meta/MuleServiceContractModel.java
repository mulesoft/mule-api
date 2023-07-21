/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.deployment.meta;

import static org.mule.runtime.api.util.Preconditions.checkArgument;

import org.mule.api.annotation.NoExtend;
import org.mule.runtime.api.service.Service;
import org.mule.runtime.api.service.ServiceProvider;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

/**
 * Describes the {@link Service} contracts that are fulfilled by the owning {@link MuleServiceModel}
 *
 * @since 1.2
 */
@NoExtend
public class MuleServiceContractModel {

  private final String serviceProviderClassName;
  private final String contractClassName;

  /**
   * Creates a new instance
   *
   * @param serviceProviderClassName The classname for the {@link ServiceProvider} that instantiates the contract implementation
   * @param contractClassName        the classname of the {@link Service} contract
   */
  public MuleServiceContractModel(String serviceProviderClassName, String contractClassName) {
    checkArgument(!StringUtils.isBlank(serviceProviderClassName), "serviceProviderClassName cannot be blank");
    checkArgument(!StringUtils.isBlank(contractClassName), "satisfiedServiceClassName cannot be blank");

    this.serviceProviderClassName = serviceProviderClassName;
    this.contractClassName = contractClassName;
  }

  /**
   * @return The classname for the {@link ServiceProvider} that instantiates the contract implementation
   */
  public String getServiceProviderClassName() {
    return serviceProviderClassName;
  }

  /**
   * @return The classname of the {@link Service} contract
   */
  public String getContractClassName() {
    return contractClassName;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MuleServiceContractModel) {
      MuleServiceContractModel other = (MuleServiceContractModel) obj;
      return Objects.equals(serviceProviderClassName, other.getServiceProviderClassName()) &&
          Objects.equals(contractClassName, other.getContractClassName());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return serviceProviderClassName.hashCode() * contractClassName.hashCode() * 11;
  }
}
