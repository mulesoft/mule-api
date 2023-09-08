/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.toList;
import static org.mule.runtime.api.exception.ExceptionHelper.getRootException;
import static org.mule.runtime.api.exception.ExceptionHelper.getRootMuleException;
import static org.mule.runtime.api.exception.MuleExceptionInfo.FLOW_STACK_INFO_KEY;
import static org.mule.runtime.api.exception.MuleExceptionInfo.INFO_CAUSED_BY_KEY;
import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

import org.mule.runtime.api.i18n.I18nMessage;
import org.mule.runtime.api.message.ErrorType;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code MuleException} is the base exception type for the Mule server any other exceptions thrown by Mule code will be based on
 * this exception.
 *
 * @since 1.0
 */
public abstract class MuleException extends Exception {

  private static final long serialVersionUID = 4553533142751195715L;

  public static final String MULE_VERBOSE_EXCEPTIONS = "mule.verbose.exceptions";

  // Info keys for logging
  /**
   * @deprecated this is a property in an object now, no more need for a key
   */
  @Deprecated
  public static final String INFO_ALREADY_LOGGED_KEY = "Logged";

  public static final String INFO_ERROR_TYPE_KEY = MuleExceptionInfo.INFO_ERROR_TYPE_KEY;
  public static final String INFO_LOCATION_KEY = MuleExceptionInfo.INFO_LOCATION_KEY;
  public static final String INFO_SOURCE_XML_KEY = MuleExceptionInfo.INFO_SOURCE_DSL_KEY;
  public static final String MISSING_DEFAULT_VALUE = MuleExceptionInfo.MISSING_DEFAULT_VALUE;

  private static final Logger LOGGER = LoggerFactory.getLogger(MuleException.class);

  public static final String EXCEPTION_MESSAGE_DELIMITER = repeat('*', 80) + lineSeparator();
  public static final String EXCEPTION_MESSAGE_SECTION_DELIMITER = repeat('-', 80) + lineSeparator();

  /**
   * When false (default), only a summary of the root exception and trail is provided. If this flag is false, full exception
   * information is reported. Switching on DEBUG level logging with automatically set this flag to true.
   */
  public static boolean verboseExceptions = false;

  private final MuleExceptionInfo exceptionInfo = new MuleExceptionInfo();
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
   * @param cause   the exception that cause this exception to be thrown
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
    if (INFO_ERROR_TYPE_KEY.equals(name)) {
      this.exceptionInfo.setErrorType((ErrorType) info);
    } else if (INFO_LOCATION_KEY.equals(name)) {
      this.exceptionInfo.setLocation(Objects.toString(info));
    } else if (INFO_SOURCE_XML_KEY.equals(name)) {
      this.exceptionInfo.setDslSource(Objects.toString(info));
    } else if (FLOW_STACK_INFO_KEY.equals(name)) {
      this.exceptionInfo.setFlowStack((Serializable) info);
    } else if (INFO_CAUSED_BY_KEY.equals(name)) {
      this.exceptionInfo.setSuppressedCauses((List<MuleException>) info);
    } else {
      this.exceptionInfo.putAdditionalEntry(name, info);
    }
  }

  public void addAllInfo(Map<String, Object> info) {
    // The received Map needs to be iterated due to MuleExceptionInfo design,
    // where some of the values are not stored in a Map.
    for (Map.Entry<String, Object> entry : info.entrySet()) {
      addInfo(entry.getKey(), entry.getValue());
    }
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
    // Info about the root mule exception is obtained and logged
    MuleException rootMuleException = getRootMuleException(this);
    rootMuleException.getExceptionInfo().addToSummaryMessage(buf);
    // For verbose messages, additional entries from suppressed MuleExceptions and any other Throwable in the causes list are
    // logged
    rootMuleException.addAllInfo(ExceptionHelper.getExceptionInfo(this));
    Map<String, Object> additionalInfo = rootMuleException.getAdditionalInfo();
    for (String key : additionalInfo.keySet().stream().sorted().collect(toList())) {
      buf.append(key);
      buf.append(getColonMatchingPad(key));
      buf.append(": ");
      buf.append((additionalInfo.get(key) == null ? "null"
          : additionalInfo.get(key).toString().replaceAll(lineSeparator(),
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
    if (!Objects.equals(e, this)) {
      return getMessage();
    }
    StringBuilder buf = new StringBuilder(1024);
    buf.append(lineSeparator()).append(EXCEPTION_MESSAGE_DELIMITER);
    buf.append("Message               : ").append(message).append(lineSeparator());

    exceptionInfo.addToSummaryMessage(buf);
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
    return exceptionInfo.asMap();
  }

  public Map<String, Object> getAdditionalInfo() {
    return exceptionInfo.getAdditionalEntries();
  }

  public MuleExceptionInfo getExceptionInfo() {
    return exceptionInfo;
  }

  public static boolean isVerboseExceptions() {
    return verboseExceptions;
  }
}
