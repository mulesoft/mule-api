/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.meta;

public interface MuleArtifactModelBuilder<T extends MuleArtifactModelBuilder<T, M>, M extends AbstractMuleArtifactModel> {

  T setName(String name);

  T setRequiredProduct(Product product);

  String getName();

  Product getRequiredProduct();

  T setMinMuleVersion(String muleVersion);

  String getMinMuleVersion();

  T withBundleDescriptorLoader(MuleArtifactLoaderDescriptor bundleDescriptorLoader);

  T withClassLoaderModelDescriptorLoader(MuleArtifactLoaderDescriptor classLoaderModelDescriptorLoader);

  MuleArtifactLoaderDescriptor getBundleDescriptorLoader();

  MuleArtifactLoaderDescriptor getClassLoaderModelDescriptorLoader();

  M build();

}
