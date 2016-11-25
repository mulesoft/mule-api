/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.error.ErrorModel;
import org.mule.runtime.api.meta.model.operation.OperationModel;

import java.util.HashSet;
import java.util.Set;

/**
 * A declaration object for a {@link OperationModel}. It contains raw, unvalidated
 * data which is used to declare the structure of a {@link OperationModel}
 *
 * @since 1.0
 */
public class OperationDeclaration extends ComponentDeclaration<OperationDeclaration> {

  private Set<ErrorModel> errorModels = new HashSet<>();

  /**
   * {@inheritDoc}
   */
  OperationDeclaration(String name) {
    super(name);
  }

  public void addErrorType(ErrorModel errorModel) {
    errorModels.add(errorModel);
  }

  public Set<ErrorModel> getErrorTypes() {
    return errorModels;
  }
}
