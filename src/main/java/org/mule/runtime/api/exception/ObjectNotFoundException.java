/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
