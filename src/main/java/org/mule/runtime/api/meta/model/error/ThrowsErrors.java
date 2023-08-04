/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
