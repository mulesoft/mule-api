/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.deployment.meta;

import static java.util.Optional.ofNullable;

import org.mule.runtime.api.meta.model.ExtensionModel;

import java.util.Optional;

/**
 * Base class for creating models for Mule artifacts from json describer files.
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

  /**
   * Creates a new model
   *
   * @param name name of the artifact
   * @param minMuleVersion minimum Mule Runtime version that requires to work correctly.
   * @param classLoaderModelLoaderDescriptor describes how to create the class loader for the artifact.
   */
  protected AbstractMuleArtifactModel(
                                      String name, String minMuleVersion,
                                      MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor) {
    this.minMuleVersion = minMuleVersion;
    this.classLoaderModelLoaderDescriptor = classLoaderModelLoaderDescriptor;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getMinMuleVersion() {
    return minMuleVersion;
  }

  public Optional<MuleArtifactLoaderDescriptor> getClassLoaderModelLoaderDescriptor() {
    return ofNullable(classLoaderModelLoaderDescriptor);
  }
}
