/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.deployment.persistence;

import org.mule.runtime.api.deployment.meta.MuleApplicationModel;
import org.mule.runtime.api.deployment.meta.MuleDomainModel;

/**
 * {@link MuleApplicationModel} json serializer.
 *
 * @since 1.0
 */
public final class MuleDomainModelJsonSerializer extends AbstractMuleArtifactModelJsonSerializer<MuleDomainModel> {

  @Override
  protected Class<MuleDomainModel> getParameterizedClass() {
    return MuleDomainModel.class;
  }

}
