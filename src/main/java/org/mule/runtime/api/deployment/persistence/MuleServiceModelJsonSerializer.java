/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.deployment.persistence;

import org.mule.runtime.api.deployment.meta.MuleServiceModel;

/**
 * {@link MuleServiceModel} json serializer.
 *
 * @since 1.0
 */
public final class MuleServiceModelJsonSerializer extends AbstractMuleArtifactModelJsonSerializer<MuleServiceModel> {

  @Override
  protected Class<MuleServiceModel> getParameterizedClass() {
    return MuleServiceModel.class;
  }

}
