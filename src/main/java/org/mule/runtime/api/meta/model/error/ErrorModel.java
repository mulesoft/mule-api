/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.error;

import org.mule.runtime.api.error.Errors;
import org.mule.runtime.api.meta.model.operation.OperationModel;

import java.util.Optional;

/**
 * A model which represents a error possible error that could be thrown
 * by the Operation represented by the {@link OperationModel}.
 * </p>
 * This model declares:
 * <ul>
 * <li><b>Type</b>: The kind of the error to thrown</li>
 * <li><b>Namespace</b>: The origin of this error type</li>
 * <li><b>Parent</b>: The error from which the current error inherits</li>
 * </ul>
 *
 * @since 1.0
 */
public interface ErrorModel {

  /**
   * Gets the type of the error.
   * This error could be one of the {@link Errors}
   *
   * @return The type of the error
   */
  String getType();

  /**
   * Gets the namespace of error.
   * This namespace represent the origin or how declares this error.
   *
   * @return The namespace of the error
   */
  String getNamespace();

  /**
   * @return The {@link ErrorModel} parent of the current {@link ErrorModel} from which it inherits from.
   */
  Optional<ErrorModel> getParent();
}
