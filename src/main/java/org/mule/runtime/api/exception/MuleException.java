/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import static org.apache.commons.lang3.SystemUtils.LINE_SEPARATOR;
import org.mule.runtime.api.i18n.I18nMessage;
import org.mule.runtime.api.i18n.I18nMessageFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code MuleException} is the base exception type for the Mule server any other exceptions thrown by Mule code will be
 * based on this exception.
 *
 * @since 1.0
 */
public abstract class MuleException extends Exception {

  public static final String INFO_LOCATION_KEY = "Element";
  public static final String INFO_SOURCE_XML_KEY = "Element XML";

  private static final long serialVersionUID = -4544199933449632546L;
  private static final Logger logger = LoggerFactory.getLogger(MuleException.class);

  private static final String EXCEPTION_MESSAGE_DELIMITER = repeat('*', 80) + LINE_SEPARATOR;
  private static final String EXCEPTION_MESSAGE_SECTION_DELIMITER = repeat('-', 80) + LINE_SEPARATOR;

  /**
   * When false (default), only a summary of the root exception and trail is provided. If this flag is false, full exception
   * information is reported. Switching on DEBUG level logging with automatically set this flag to true.
   */
  public static boolean verboseExceptions = false;

  private final Map info = new HashMap();
  private int errorCode = -1;
  private String message = null;
  private I18nMessage i18nMessage;

  static {
    String p = System.getProperty("mule.verbose.exceptions");
    if (p != null) {
      verboseExceptions = Boolean.parseBoolean(p);
    }
  }

  /**
   * @param message the exception message
   */
  public MuleException(I18nMessage message) {
    super();
    setMessage(message);
  }

  /**
   * @param message the exception message
   * @param cause the exception that cause this exception to be thrown
   */
  public MuleException(I18nMessage message, Throwable cause) {
    super(ExceptionHelper.unwrap(cause));
    setMessage(message);
  }

  public MuleException(Throwable cause) {
    super(ExceptionHelper.unwrap(cause));
    if (cause != null) {
      setMessage(I18nMessageFactory.createStaticMessage(cause.getMessage() + " (" + cause.getClass().getName() + ")"));
    } else {
      initialise();
    }
  }

  private static String repeat(char c, int len) {
    String str = String.valueOf(c);
    if (str == null) {
      return null;
    } else if (len <= 0) {
      return "";
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < len; i++) {
        stringBuilder.append(str);
      }
      return stringBuilder.toString();
    }
  }

  protected MuleException() {
    super();
    initialise();
  }

  protected void setMessage(I18nMessage message) {
    initialise();
    this.message = message.getMessage();
    i18nMessage = message;
  }

  protected void setMessage(String message) {
    initialise();
    this.message = message;
    if (i18nMessage == null) {
      i18nMessage = I18nMessageFactory.createStaticMessage(message);
    }
  }

  public int getExceptionCode() {
    return errorCode;
  }

  public I18nMessage getI18nMessage() {
    return i18nMessage;
  }

  public int getMessageCode() {
    return (i18nMessage == null ? 0 : i18nMessage.getCode());
  }

  public void addInfo(String name, Object info) {
    this.info.put(name, info);
  }

  protected void appendMessage(String s) {
    message += s;
  }

  protected void prependMessage(String s) {
    message = message + ". " + s;
  }

  protected void setExceptionCode(int code) {
    errorCode = code;
  }

  @Override
  public final String getMessage() {
    return message;
  }

  protected void initialise() {
    setExceptionCode(-1);
  }

  public String getDetailedMessage() {
    if (isVerboseExceptions()) {
      return getVerboseMessage();
    } else {
      return getSummaryMessage();
    }
  }

  public String getVerboseMessage() {
    MuleException e = ExceptionHelper.getRootMuleException(this);
    if (!e.equals(this)) {
      return getMessage();
    }
    StringBuilder buf = new StringBuilder(1024);
    buf.append(LINE_SEPARATOR).append(EXCEPTION_MESSAGE_DELIMITER);
    buf.append("Message               : ").append(message).append(LINE_SEPARATOR);

    Map info = ExceptionHelper.getExceptionInfo(this);
    for (Map.Entry entry : (Set<Map.Entry>) info.entrySet()) {
      String s = (String) entry.getKey();
      int pad = 22 - s.length();
      buf.append(s);
      if (pad > 0) {
        buf.append(repeat(' ', pad));
      }
      buf.append(": ");
      buf.append((entry.getValue() == null ? "null"
          : entry.getValue().toString().replaceAll(LINE_SEPARATOR,
                                                   LINE_SEPARATOR + repeat(' ', 24))))
          .append(LINE_SEPARATOR);
    }

    // print exception stack
    buf.append(EXCEPTION_MESSAGE_SECTION_DELIMITER);
    buf.append("Root Exception stack trace:").append(LINE_SEPARATOR);
    Throwable root = ExceptionHelper.getRootException(this);
    StringWriter w = new StringWriter();
    PrintWriter p = new PrintWriter(w);
    root.printStackTrace(p);
    buf.append(w.toString()).append(LINE_SEPARATOR);
    buf.append(EXCEPTION_MESSAGE_DELIMITER);

    return buf.toString();
  }

  public String getSummaryMessage() {
    MuleException e = ExceptionHelper.getRootMuleException(this);
    if (!e.equals(this)) {
      return getMessage();
    }
    StringBuilder buf = new StringBuilder(1024);
    buf.append(LINE_SEPARATOR).append(EXCEPTION_MESSAGE_DELIMITER);
    buf.append("Message               : ").append(message).append(LINE_SEPARATOR);
    appendSummaryMessage(buf);

    buf.append(LINE_SEPARATOR)
        .append("  (set debug level logging or '-Dmule.verbose.exceptions=true' for everything)")
        .append(LINE_SEPARATOR);
    buf.append(EXCEPTION_MESSAGE_DELIMITER);

    return buf.toString();
  }

  /**
   * Template method so when {@code #getSummaryMessage()} is called, specific implementation can add content to the summary. By
   * default, the location data will be added.
   *
   * @param builder {@link StringBuilder} to use for appending additional summary info.
   */
  protected void appendSummaryMessage(StringBuilder builder) {
    Map exceptionInfo = org.mule.runtime.api.exception.ExceptionHelper.getExceptionInfo(this);
    builder.append("Element               : ")
      .append(exceptionInfo.get(INFO_LOCATION_KEY))
      .append(LINE_SEPARATOR);
    Object sourceXml = exceptionInfo.get(INFO_SOURCE_XML_KEY);
    if (sourceXml != null) {
      builder.append("Element XML           : ")
        .append(sourceXml)
        .append(LINE_SEPARATOR);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MuleException)) {
      return false;
    }

    final MuleException exception = (MuleException) o;

    if (errorCode != exception.errorCode) {
      return false;
    }
    if (i18nMessage != null ? !i18nMessage.equals(exception.i18nMessage) : exception.i18nMessage != null) {
      return false;
    }
    if (message != null ? !message.equals(exception.message) : exception.message != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result;
    result = errorCode;
    result = 29 * result + (message != null ? message.hashCode() : 0);
    result = 29 * result + (i18nMessage != null ? i18nMessage.hashCode() : 0);
    return result;
  }

  public Map getInfo() {
    return info;
  }

  public static boolean isVerboseExceptions() {
    return verboseExceptions || logger.isDebugEnabled();
  }
}
