/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.lifecycle;

import org.mule.runtime.api.i18n.I18nMessage;

/**
 * <code>CreateException</code> is thrown when creating an object inside Mule wasn't possible due to inconsistent internal state
 * or wrong input.
 *
 * @since 1.0
 */
public final class CreateException extends LifecycleException {

  /** Serial version */
  private static final long serialVersionUID = -5070464873918600823L;

  /**
   * @param message   the exception message
   * @param component the component that failed during a lifecycle method call
   */
  public CreateException(I18nMessage message, Object component) {
    super(message, component);
  }

  /**
   * @param message   the exception message
   * @param cause     the exception that cause this exception to be thrown
   * @param component the component that failed during a lifecycle method call
   */
  public CreateException(I18nMessage message, Throwable cause, Object component) {
    super(message, cause, component);
  }

  /**
   * @param cause     the exception that cause this exception to be thrown
   * @param component the component that failed during a lifecycle method call
   */
  public CreateException(Throwable cause, Object component) {
    super(cause, component);
  }
}
