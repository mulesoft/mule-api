/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.deployment.meta;

import static org.mule.runtime.api.util.Preconditions.checkArgument;
import org.mule.runtime.api.meta.model.ExtensionModel;

/**
 * Base class for creating models for Mule artifacts from JSON describer files.
 * <p/>
 * The idea behind this object is to just load some bits of information that later each "loader" will consume to
 * generate things like {@link ClassLoader}, {@link ExtensionModel}, etc.
 *
 * @since 1.0
 */
public abstract class AbstractMuleArtifactModel {

  private final String name;
  private final String minMuleVersion;
  private final MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor;
  private final MuleArtifactLoaderDescriptor bundleDescriptorLoader;

  /**
   * Creates a new model
   *
   * @param name name of the artifact
   * @param minMuleVersion minimum Mule Runtime version that requires to work correctly.
   * @param classLoaderModelLoaderDescriptor describes how to create the class loader for the artifact.
   * @param bundleDescriptorLoader indicates how to load the bundle descriptor.
   */
  protected AbstractMuleArtifactModel(
                                      String name, String minMuleVersion,
                                      MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                                      MuleArtifactLoaderDescriptor bundleDescriptorLoader) {
    checkArgument(classLoaderModelLoaderDescriptor != null, "classLoaderModelLoaderDescriptor cannot be null");
    checkArgument(bundleDescriptorLoader != null, "bundleDescriptorLoader cannot be null");
    this.minMuleVersion = minMuleVersion;
    this.name = name;
    this.classLoaderModelLoaderDescriptor = classLoaderModelLoaderDescriptor;
    this.bundleDescriptorLoader = bundleDescriptorLoader;
  }

  public String getName() {
    return name;
  }

  public String getMinMuleVersion() {
    return minMuleVersion;
  }

  public MuleArtifactLoaderDescriptor getBundleDescriptorLoader() {
    return bundleDescriptorLoader;
  }

  public MuleArtifactLoaderDescriptor getClassLoaderModelLoaderDescriptor() {
    return classLoaderModelLoaderDescriptor;
  }
}
