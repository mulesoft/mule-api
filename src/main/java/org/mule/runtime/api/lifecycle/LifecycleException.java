/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.lifecycle;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;
import org.mule.runtime.api.exception.LocatedMuleException;
import org.mule.runtime.api.i18n.I18nMessage;

/**
 * {@code LifecycleException} is the base exception thrown when an error occurs
 * during a {@link Lifecycle} phase.
 *
 * @since 1.0
 */
public class LifecycleException extends LocatedMuleException {

  /** Serial version */
  private static final long serialVersionUID = 2909614055858287394L;

  private transient Object component;

  /**
   * @param message the exception message
   * @param component the object that failed during a lifecycle method call
   */
  public LifecycleException(I18nMessage message, Object component) {
    super(message, component);
    this.component = component;
  }

  /**
   * @param message the exception message
   * @param cause the exception that cause this exception to be thrown
   * @param component the object that failed during a lifecycle method call
   */
  public LifecycleException(I18nMessage message, Throwable cause, Object component) {
    super(message, cause, component);
    this.component = component;
  }

  /**
   * @param cause the exception that cause this exception to be thrown
   * @param component the object that failed during a lifecycle method call
   */
  public LifecycleException(Throwable cause, Object component) {
    super(createStaticMessage(cause.getMessage()), cause, component);
    this.component = component;
  }

  public Object getComponent() {
    return component;
  }
}
