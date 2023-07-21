/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.deployment.persistence;

import org.mule.runtime.api.deployment.meta.MuleServerPluginModel;

/**
 * Serializer capable of marshalling an {@link MuleServerPluginModel} instance to {@code JSON} format and back
 *
 * @since 1.0
 */
public final class MuleServerPluginModelJsonSerializer extends AbstractMuleArtifactModelJsonSerializer<MuleServerPluginModel> {

  @Override
  protected Class<MuleServerPluginModel> getParameterizedClass() {
    return MuleServerPluginModel.class;
  }
}
