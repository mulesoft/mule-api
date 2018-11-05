/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.toList;
import static org.mule.runtime.api.exception.ExceptionHelper.getExceptionInfo;
import static org.mule.runtime.api.exception.ExceptionHelper.getRootException;
import static org.mule.runtime.api.exception.ExceptionHelper.getRootMuleException;
import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;
import org.mule.runtime.api.i18n.I18nMessage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code MuleException} is the base exception type for the Mule server any other exceptions thrown by Mule code will be based on
 * this exception.
 *
 * @since 1.0
 */
public abstract class MuleException extends Exception {

  public static final String MULE_VERBOSE_EXCEPTIONS = "mule.verbose.exceptions";

  //Info keys for logging
  public static final String INFO_LOCATION_KEY = "Element";
  public static final String INFO_SOURCE_XML_KEY = "Element XML";
  public static final String INFO_ERROR_TYPE_KEY = "Error type";
  public static final String INFO_ALREADY_LOGGED_KEY = "Logged";

  public static final String MISSING_DEFAULT_VALUE = "(None)";

  //To define the information that will be included if a summary is logged instead of a verbose exception
  private static final String[] SUMMARY_LOGGING_KEYS = {INFO_ERROR_TYPE_KEY, INFO_LOCATION_KEY, INFO_SOURCE_XML_KEY};

  private static final long serialVersionUID = -4544199933449632546L;
  private static final Logger LOGGER = LoggerFactory.getLogger(MuleException.class);

  public static final String EXCEPTION_MESSAGE_DELIMITER = repeat('*', 80) + lineSeparator();
  public static final String EXCEPTION_MESSAGE_SECTION_DELIMITER = repeat('-', 80) + lineSeparator();

  /**
   * When false (default), only a summary of the root exception and trail is provided. If this flag is false, full exception
   * information is reported. Switching on DEBUG level logging with automatically set this flag to true.
   */
  public static boolean verboseExceptions = false;

  private final Map<String, Object> info = new HashMap<>();
  private String message = null;
  private I18nMessage i18nMessage;

  static {
    refreshVerboseExceptions();
  }

  /**
   * Reads the value of the {@code mule.verbose.exceptions} system property.
   */
  public static void refreshVerboseExceptions() {
    String p = System.getProperty(MULE_VERBOSE_EXCEPTIONS);
    if (p != null) {
      verboseExceptions = Boolean.parseBoolean(p) || LOGGER.isDebugEnabled();
    } else {
      verboseExceptions = LOGGER.isDebugEnabled();
    }
  }

  /**
   * @param message the exception message
   */
  public MuleException(I18nMessage message) {
    this(message, null);
  }

  /**
   * @param message the exception message
   * @param cause the exception that cause this exception to be thrown
   */
  public MuleException(I18nMessage message, Throwable cause) {
    super(null, ExceptionHelper.unwrap(cause), true, isVerboseExceptions());
    setMessage(message);
  }

  public MuleException(Throwable cause) {
    this(cause != null ? createStaticMessage(cause.getMessage()) : null, cause);
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

  private String getColonMatchingPad(String key) {
    int padSize = 22 - key.length();
    if (padSize > 0) {
      return repeat(' ', padSize);
    }
    return "";
  }

  protected MuleException() {
    this(null, null);
  }

  protected void setMessage(I18nMessage message) {
    this.message = message != null ? message.getMessage() : null;
    i18nMessage = message;
  }

  protected void setMessage(String message) {
    this.message = message;
    if (i18nMessage == null) {
      i18nMessage = createStaticMessage(message);
    }
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

  @Override
  public final String getMessage() {
    return message;
  }

  public String getDetailedMessage() {
    if (isVerboseExceptions()) {
      return getVerboseMessage();
    } else {
      return getSummaryMessage();
    }
  }

  public String getVerboseMessage() {
    StringBuilder buf = new StringBuilder(1024);
    buf.append(lineSeparator()).append(EXCEPTION_MESSAGE_DELIMITER);
    buf.append("Message               : ").append(message).append(lineSeparator());

    Map<String, Object> info = getExceptionInfo(this);
    for (String key : info.keySet().stream().sorted().collect(toList())) {
      buf.append(key);
      buf.append(getColonMatchingPad(key));
      buf.append(": ");
      buf.append((info.get(key) == null ? "null"
          : info.get(key).toString().replaceAll(lineSeparator(),
                                                lineSeparator() + repeat(' ', 24))))
          .append(lineSeparator());
    }

    // print exception stack
    buf.append(EXCEPTION_MESSAGE_SECTION_DELIMITER);
    buf.append("Root Exception stack trace:").append(lineSeparator());
    Throwable root = getRootException(this);
    StringWriter w = new StringWriter();
    PrintWriter p = new PrintWriter(w);
    root.printStackTrace(p);
    buf.append(w.toString()).append(lineSeparator());
    buf.append(EXCEPTION_MESSAGE_DELIMITER);

    return buf.toString();
  }

  public String getSummaryMessage() {
    MuleException e = getRootMuleException(this);
    if (!e.equals(this)) {
      return getMessage();
    }
    StringBuilder buf = new StringBuilder(1024);
    buf.append(lineSeparator()).append(EXCEPTION_MESSAGE_DELIMITER);
    buf.append("Message               : ").append(message).append(lineSeparator());

    for (String key : SUMMARY_LOGGING_KEYS) {
      buf.append(format("%s%s: %s", key, getColonMatchingPad(key), info.getOrDefault(key, MISSING_DEFAULT_VALUE)));
      buf.append(lineSeparator());
    }
    buf.append(lineSeparator())
        .append("  (set debug level logging or '-D" + MULE_VERBOSE_EXCEPTIONS + "=true' for everything)")
        .append(lineSeparator());
    buf.append(EXCEPTION_MESSAGE_DELIMITER);

    return buf.toString();
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
    result = (message != null ? message.hashCode() : 0);
    result = 29 * result + (i18nMessage != null ? i18nMessage.hashCode() : 0);
    return result;
  }

  public Map<String, Object> getInfo() {
    return info;
  }

  public static boolean isVerboseExceptions() {
    return verboseExceptions;
  }
}
