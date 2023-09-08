/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
