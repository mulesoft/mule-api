/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.error;

import org.mule.api.annotation.NoImplement;

import java.util.Set;

/**
 * Indicates that the current model can declare which {@link ErrorModel} it can throw.
 *
 * @since 1.0
 */
@NoImplement
public interface ThrowsErrors {

  /**
   * @return a {@link Set} of {@link ErrorModel} with the possible errors that the current component could throw.
   * @see ErrorModel
   */
  Set<ErrorModel> getErrorModels();
}
