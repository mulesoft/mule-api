/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

/**
 * Exception type for representing a failure due to object not found.
 *
 * @since 4.0
 */
public final class ObjectNotFoundException extends MuleRuntimeException {

  /**
   * {@inheritDoc}
   */
  public ObjectNotFoundException(String componentIdentifier) {
    super(createStaticMessage("It was not possible to find an object for identifier "
        + componentIdentifier));
  }
}
