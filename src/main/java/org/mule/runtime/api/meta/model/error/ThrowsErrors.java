/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.error;

import java.util.Set;

/**
 * Indicates that the current model can declare which {@link ErrorModel} it can throw.
 *
 * @since 1.0
 */
public interface ThrowsErrors {

  /**
   * @return a {@link Set} of {@link ErrorModel} with the possible errors that the current
   * component could throw.
   * @see ErrorModel
   */
  Set<ErrorModel> getErrorModels();
}
