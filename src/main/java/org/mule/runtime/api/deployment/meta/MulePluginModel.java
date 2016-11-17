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
 * This object matches the mule-plugin.json element within a plugin. The describer holds information that has being
 * picked up from the JSON file (and the pom.xml when implemented). There's no extra logic, such as calculating the
 * URLs needed to feed a class loader or the {@link ExtensionModel} for the current plugin.
 * <p/>
 * The idea behind this object is to just load some bits of information that later each "loader" will consume to
 * generate things like {@link ClassLoader}, {@link ExtensionModel}, etc.
 *
 * @since 1.0
 */
public class MulePluginModel {

  final private String name;
  final private String minMuleVersion;
  final private MulePluginProperty extensionModelLoaderDescriptor;

  /**
   * Creates an immutable implementation of {@link MulePluginModel}
   *
   * @param name of the artifact
   * @param minMuleVersion minimal Mule version it runs on
   * @param extensionModelLoaderDescriptor information related to the plugin to generate an {@link ExtensionModel}
   */
  public MulePluginModel(String name, String minMuleVersion,
                         MulePluginProperty extensionModelLoaderDescriptor) {
    this.name = name;
    this.minMuleVersion = minMuleVersion;
    this.extensionModelLoaderDescriptor = extensionModelLoaderDescriptor;
  }

  public String getName() {
    return name;
  }

  public String getMinMuleVersion() {
    return minMuleVersion;
  }

  public Optional<MulePluginProperty> getExtensionModelLoaderDescriptor() {
    return ofNullable(extensionModelLoaderDescriptor);
  }
}
