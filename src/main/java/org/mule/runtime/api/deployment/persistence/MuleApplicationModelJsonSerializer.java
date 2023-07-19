/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.deployment.persistence;

import org.mule.runtime.api.deployment.meta.MuleApplicationModel;

/**
 * {@link MuleApplicationModel} json serializer.
 *
 * @since 1.0
 */
public final class MuleApplicationModelJsonSerializer extends AbstractMuleArtifactModelJsonSerializer<MuleApplicationModel> {

  @Override
  protected Class<MuleApplicationModel> getParameterizedClass() {
    return MuleApplicationModel.class;
  }

}
