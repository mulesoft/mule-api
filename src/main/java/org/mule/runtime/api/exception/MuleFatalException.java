/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.exception;

import org.mule.runtime.api.i18n.I18nMessage;

/**
 * {@code MuleFatalException} Is the runtime exception used to wrap fatal errors such as {@code VirtualMachineError}. This is to
 * avoid Reactor intercepting them in a stream and propagating outside to arbitrary threads.
 *
 * @since 1.0
 */
public final class MuleFatalException extends MuleRuntimeException {

  /**
   * Serial version
   */
  private static final long serialVersionUID = -3729492434108165916L;

  /**
   * @param message the exception message
   */
  public MuleFatalException(I18nMessage message) {
    super(message);
  }

  /**
   * @param message the exception message
   * @param cause   the exception that triggered this exception
   */
  public MuleFatalException(I18nMessage message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param cause the exception that triggered this exception
   */
  public MuleFatalException(Throwable cause) {
    super(cause);
  }
}
