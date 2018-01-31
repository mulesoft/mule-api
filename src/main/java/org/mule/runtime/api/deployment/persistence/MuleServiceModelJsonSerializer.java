/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
