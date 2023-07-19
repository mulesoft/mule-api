/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.lifecycle;

import static java.lang.String.format;
import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

import org.mule.runtime.api.component.Component;
import org.mule.runtime.api.exception.LocatedMuleException;
import org.mule.runtime.api.i18n.I18nMessage;
import org.mule.runtime.api.meta.NamedObject;

/**
 * {@code LifecycleException} is the base exception thrown when an error occurs during a {@link Lifecycle} phase.
 *
 * @since 1.0
 */
public class LifecycleException extends LocatedMuleException {

  /** Serial version */
  private static final long serialVersionUID = 8445060869503979540L;

  private transient Object component;

  /**
   * @param message   the exception message
   * @param component the object that failed during a lifecycle method call
   */
  public LifecycleException(I18nMessage message, Object component) {
    super(message, component);
    this.component = component;
  }

  /**
   * @param message   the exception message
   * @param cause     the exception that cause this exception to be thrown
   * @param component the object that failed during a lifecycle method call
   */
  public LifecycleException(I18nMessage message, Throwable cause, Object component) {
    super(message, cause, component);
    this.component = component;
  }

  /**
   * @param cause     the exception that cause this exception to be thrown
   * @param component the object that failed during a lifecycle method call
   */
  public LifecycleException(Throwable cause, Object component) {
    super(createStaticMessage(cause.getMessage()), cause, component);
    this.component = component;
  }

  public Object getComponent() {
    return component;
  }

  @Override
  protected String resolveProcessorPath(Object component) {
    if (component instanceof Component) {
      return super.resolveProcessorPath(component);
    } else {
      return format("%s @ %s",
                    (component == null
                        ? "null"
                        : (component instanceof NamedObject
                            ? ((NamedObject) component).getName()
                            : component.toString())),
                    "app");
    }
  }

}
