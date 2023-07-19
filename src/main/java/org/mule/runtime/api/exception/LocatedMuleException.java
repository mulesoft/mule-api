/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.exception;

import static java.lang.String.format;

import org.mule.runtime.api.component.Component;
import org.mule.runtime.api.i18n.I18nMessage;
import org.mule.runtime.api.meta.NameableObject;

/**
 * {@code LocatedMuleException} is a general exception that adds context location about the Exception (i.e.: where it occurred in
 * the application).
 *
 * @since 1.0
 */
public class LocatedMuleException extends MuleException {

  /**
   * Serial version
   */
  private static final long serialVersionUID = 5100364571184418132L;

  /**
   * @param component the object that failed during a lifecycle method call
   */
  public LocatedMuleException(Object component) {
    super();
    setLocation(component);
  }

  /**
   * @param message   the exception message
   * @param component the object that failed during a lifecycle method call
   */
  public LocatedMuleException(I18nMessage message, Object component) {
    super(message);
    setLocation(component);
  }

  /**
   * @param message   the exception message
   * @param cause     the exception that cause this exception to be thrown
   * @param component the object that failed during a lifecycle method call
   */
  public LocatedMuleException(I18nMessage message, Throwable cause, Object component) {
    super(message, cause);
    setLocation(component);
  }

  /**
   * @param cause     the exception that cause this exception to be thrown
   * @param component the object that failed during a lifecycle method call
   */
  public LocatedMuleException(Throwable cause, Object component) {
    super(cause);
    setLocation(component);
  }


  protected void setLocation(Object component) {
    if (component != null) {
      addInfo(INFO_LOCATION_KEY, resolveProcessorPath(component));
    }
  }

  protected String resolveProcessorPath(Object component) {
    return ((Component) component).getRepresentation();
  }

  public static String toString(Object obj) {
    if (obj == null) {
      return "";
    }

    String str = obj.getClass().getName();
    if (obj instanceof NameableObject) {
      str += format(" '%s'", ((NameableObject) obj).getName());
    }
    return str;
  }

}
