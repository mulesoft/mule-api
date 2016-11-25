/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.error;

import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.meta.model.operation.OperationModel;

import java.util.Optional;

/**
 * A model which represents a possible error that could be thrown
 * by the component represented by the {@link ComponentModel}.
 *
 * @since 1.0
 */
public interface ErrorModel {

  /**
   * Gets the type of the error.
   * @return The type of the error
   */
  String getType();

  /**
   * Gets the namespace of error.
   * This namespace represent the origin or who declares this error, so it could be the namespace of an
   * extension or the {@code MULE} namespace.
   *
   * @return The namespace of the error
   */
  String getNamespace();

  /**
   * @return The {@link ErrorModel} parent of the current {@link ErrorModel} from which it inherits from.
   */
  Optional<ErrorModel> getParent();
}
