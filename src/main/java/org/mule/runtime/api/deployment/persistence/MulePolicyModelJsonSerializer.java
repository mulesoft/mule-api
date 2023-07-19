/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.deployment.persistence;

import org.mule.runtime.api.deployment.meta.MulePolicyModel;

/**
 * Serializer capable of marshalling an {@link MulePolicyModel} instance to {@code JSON} format and back
 *
 * @since 1.0
 */
public final class MulePolicyModelJsonSerializer extends AbstractMuleArtifactModelJsonSerializer<MulePolicyModel> {

  @Override
  protected Class getParameterizedClass() {
    return MulePolicyModel.class;
  }
}
