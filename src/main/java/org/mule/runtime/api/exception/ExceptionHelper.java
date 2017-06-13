/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import static java.lang.System.lineSeparator;

import org.mule.runtime.api.legacy.exception.ExceptionReader;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code ExceptionHelper} provides a number of helper functions that can be useful for dealing with Mule exceptions. This class
 * has 3 core functions -
 * <p/>
 * 1. ErrorCode lookup. A corresponding Mule error code can be found using for a given Mule exception 2. Additional Error
 * information such as Java doc url for a given exception can be resolved using this class 3. Error code mappings can be looked up
 * by providing the the protocol to map to and the Mule exception.
 *
 * @since 1.0
 */
public class ExceptionHelper {

  private static final String MULE_PACKAGE_REGEXP = "(?:org|com)\\.mule(?:soft)?\\.(?!mvel2)(?!el).*";

  public static final String[] DEFAULT_STACKTRACE_FILTER =
      ("org.mule.runtime.core.processor.AbstractInterceptingMessageProcessor," + "org.mule.runtime.core.processor.chain")
          .split(",");

  private static final int EXCEPTION_THRESHOLD = 3;
  private static boolean verbose = true;
  private static boolean initialised = false;

  /**
   * A list of the exception readers to use for different types of exceptions
   */
  private static List<ExceptionReader> exceptionReaders = new ArrayList<ExceptionReader>();

  /**
   * The default ExceptionReader which will be used for most types of exceptions
   */
  private static ExceptionReader defaultExceptionReader = new DefaultExceptionReader();

  /**
   * A comma-separated list of internal packages/classes which are removed from sanitized stacktraces. Matching is done via
   * string.startsWith().
   *
   * @see #fullStackTraces
   */
  public static String[] stackTraceFilter = DEFAULT_STACKTRACE_FILTER;

  /**
   * When false (default), some internal Mule entries are removed from exception stacktraces for readability.
   *
   * @see #stackTraceFilter
   */
  public static boolean fullStackTraces = false;

  static {
    initialise();
  }

  /**
   * Do not instanciate.
   */
  protected ExceptionHelper() {
    // no-op
  }

  public static <T extends Throwable> T unwrap(T t) {
    if (t instanceof InvocationTargetException) {
      return (T) ((InvocationTargetException) t).getTargetException();
    }
    return t;

  }

  /**
   * Gets an exception reader for the exception
   *
   * @param t the exception to get a reader for
   * @return either a specific reader or an instance of DefaultExceptionReader. This method never returns null;
   */
  public static ExceptionReader getExceptionReader(Throwable t) {
    for (ExceptionReader exceptionReader : exceptionReaders) {
      if (exceptionReader.getExceptionType().isInstance(t)) {
        return exceptionReader;
      }
    }
    return defaultExceptionReader;
  }

  public static MuleException getRootMuleException(Throwable t) {
    Throwable cause = t;
    MuleException exception = null;
    // Info is added to the wrapper exceptions. We add them to the root mule exception so the gotten info is
    // properly logged.
    Map muleExceptionInfo = new HashMap<>();
    while (cause != null) {
      if (cause instanceof MuleException) {
        exception = (MuleException) cause;
        muleExceptionInfo.putAll(exception.getInfo());
      }
      final Throwable tempCause = getExceptionReader(cause).getCause(cause);
      if (fullStackTraces) {
        cause = tempCause;
      } else {
        cause = ExceptionHelper.sanitize(tempCause);
      }
      // address some misbehaving exceptions, avoid endless loop
      if (t == cause) {
        break;
      }
    }
    if (exception != null) {
      exception.getInfo().putAll(muleExceptionInfo);
    }
    return exception;
  }

  public static Throwable getNonMuleException(Throwable t) {
    if (!(t instanceof MuleException)) {
      return t;
    }
    Throwable cause = t;
    while (cause != null) {
      cause = getExceptionReader(cause).getCause(cause);
      // address some misbehaving exceptions, avoid endless loop
      if (t == cause || !(cause instanceof MuleException)) {
        break;
      }
    }
    return cause instanceof MuleException ? null : cause;
  }

  public static Map<String, Object> getExceptionInfo(Throwable t) {
    Map<String, Object> info = new HashMap<>();
    Throwable cause = t;
    while (cause != null) {
      info.putAll(getExceptionReader(cause).getInfo(cause));
      cause = getExceptionReader(cause).getCause(cause);
      // address some misbehaving exceptions, avoid endless loop
      if (t == cause) {
        break;
      }
    }
    return info;
  }

  public static Throwable getRootException(Throwable t) {
    Throwable cause = t;
    Throwable root = null;
    while (cause != null) {
      root = cause;
      cause = getExceptionReader(cause).getCause(cause);
      // address some misbehaving exceptions, avoid endless loop
      if (t == cause) {
        break;
      }
    }

    return fullStackTraces ? root : sanitize(root);
  }

  public static String getExceptionStack(Throwable t) {
    Throwable root = getRootException(t);
    MuleException rootMule = getRootMuleException(t);

    StringBuilder buf = new StringBuilder();

    ExceptionReader rootMuleReader = getExceptionReader(rootMule);
    buf.append(rootMuleReader.getMessage(rootMule)).append(" (").append(rootMule.getClass().getName()).append(")")
        .append(lineSeparator());

    if (verbose) {
      int processedElements = 0;
      int processedMuleElements = 1;
      for (StackTraceElement stackTraceElement : root.getStackTrace()) {
        if (processedMuleElements > EXCEPTION_THRESHOLD) {
          break;
        }

        ++processedElements;
        if (stackTraceElement.getClassName().matches(MULE_PACKAGE_REGEXP)) {
          ++processedMuleElements;
        }

        buf.append("  ").append(stackTraceElement.getClassName()).append(".").append(stackTraceElement.getMethodName())
            .append("(").append(stackTraceElement.getFileName()).append(":").append(stackTraceElement.getLineNumber()).append(")")
            .append(lineSeparator());
      }

      if (root.getStackTrace().length - processedElements > 0) {
        buf.append("  (").append(root.getStackTrace().length - processedElements).append(" more...)")
            .append(lineSeparator());
      }
    }

    return buf.toString();
  }

  private static void initialise() {
    if (initialised) {
      return;
    }

    String fullStackTracesString = System.getProperty("mule.stacktrace.full");
    if (fullStackTracesString != null) {
      fullStackTraces = false;
    }
    String stackTraceFilterString = System.getProperty("mule.stacktrace.filter");
    if (stackTraceFilterString != null) {
      stackTraceFilter = stackTraceFilterString.split(",");
    }

    registerExceptionReader(new MuleExceptionReader());
    registerExceptionReader(new NamingExceptionReader());
    initialised = true;
  }

  /**
   * Removes some internal Mule entries from the stacktrace. Modifies the passed-in throwable stacktrace.
   */
  public static Throwable sanitize(Throwable t) {
    if (t == null) {
      return null;
    }
    StackTraceElement[] trace = t.getStackTrace();
    List<StackTraceElement> newTrace = new ArrayList<StackTraceElement>();
    for (StackTraceElement stackTraceElement : trace) {
      if (!isMuleInternalClass(stackTraceElement.getClassName())) {
        newTrace.add(stackTraceElement);
      }
    }

    StackTraceElement[] clean = new StackTraceElement[newTrace.size()];
    newTrace.toArray(clean);
    t.setStackTrace(clean);

    Throwable cause = t.getCause();
    while (cause != null) {
      sanitize(cause);
      cause = cause.getCause();
    }

    return t;
  }


  /**
   * Removes some internal Mule entries from the stacktrace. Modifies the passed-in throwable stacktrace.
   */
  public static Throwable summarise(Throwable t, int depth) {
    t = sanitize(t);
    StackTraceElement[] trace = t.getStackTrace();

    int newStackDepth = Math.min(trace.length, depth);
    StackTraceElement[] newTrace = new StackTraceElement[newStackDepth];

    System.arraycopy(trace, 0, newTrace, 0, newStackDepth);
    t.setStackTrace(newTrace);

    return t;
  }

  private static boolean isMuleInternalClass(String className) {
    /*
     * Sacrifice the code quality for the sake of keeping things simple - the alternative would be to pass MuleContext into every
     * exception constructor.
     */
    for (String mulePackage : stackTraceFilter) {
      if (className.startsWith(mulePackage)) {
        return true;
      }
    }
    return false;
  }

  public static List<Throwable> getExceptionsAsList(Throwable t) {
    List<Throwable> exceptions = new ArrayList<Throwable>();
    Throwable cause = t;
    while (cause != null) {
      exceptions.add(0, cause);
      cause = getExceptionReader(cause).getCause(cause);
      // address some misbehaving exceptions, avoid endless loop
      if (t == cause) {
        break;
      }
    }
    return exceptions;
  }

  /**
   * Registers an exception reader with Mule
   *
   * @param reader the reader to register.
   */
  public static void registerExceptionReader(ExceptionReader reader) {
    exceptionReaders.add(reader);
  }

}
