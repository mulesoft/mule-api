/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.i18n;

import org.mule.api.annotation.NoExtend;

import java.io.Serializable;

@NoExtend
public class I18nMessage implements Serializable {

  /**
   * Serial version
   */
  private static final long serialVersionUID = -6109760447384477924L;

  private final String message;
  private int code = 0;
  private final Object[] args;
  private I18nMessage nextMessage;

  protected I18nMessage(String message, int code, Object... args) {
    super();
    this.message = message;
    this.code = code;
    this.args = args;
  }

  public int getCode() {
    return code;
  }

  public Object[] getArgs() {
    return args;
  }

  public String getMessage() {
    if (nextMessage != null) {
      return message + ". " + nextMessage.getMessage();
    } else {
      return message != null ? message : "null";
    }
  }

  public I18nMessage setNextMessage(I18nMessage nextMessage) {
    this.nextMessage = nextMessage;
    return this;
  }

  public I18nMessage getNextMessage() {
    return nextMessage;
  }

  @Override
  public String toString() {
    return this.getMessage();
  }
}
