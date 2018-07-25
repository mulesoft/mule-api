/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.meta;

import static org.mule.runtime.api.util.Preconditions.checkArgument;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class MuleServiceContractModel {

  private final String serviceProviderClassName;
  private final String contractClassName;

  public MuleServiceContractModel(String serviceProviderClassName, String contractClassName) {
    checkArgument(!StringUtils.isBlank(serviceProviderClassName), "serviceProviderClassName cannot be blank");
    checkArgument(!StringUtils.isBlank(contractClassName), "satisfiedServiceClassName cannot be blank");

    this.serviceProviderClassName = serviceProviderClassName;
    this.contractClassName = contractClassName;
  }

  public String getServiceProviderClassName() {
    return serviceProviderClassName;
  }

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
