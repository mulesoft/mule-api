/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.deployment.persistence;

import org.mule.runtime.api.deployment.meta.MulePluginModel;

/**
 * Serializer capable of marshalling an {@link MulePluginModel} instance to {@code JSON} format and back
 *
 * @since 1.0
 */
public final class MulePluginModelJsonSerializer extends AbstractMuleArtifactModelJsonSerializer<MulePluginModel> {

  @Override
  protected Class<MulePluginModel> getParameterizedClass() {
    return MulePluginModel.class;
  }
}
