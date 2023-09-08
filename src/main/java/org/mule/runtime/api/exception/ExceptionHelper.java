/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import static java.lang.Math.min;
import static java.lang.String.format;
import static java.lang.System.arraycopy;
import static java.lang.System.lineSeparator;
import static java.lang.Thread.currentThread;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mule.runtime.api.exception.MuleException.MULE_VERBOSE_EXCEPTIONS;

import org.mule.runtime.api.legacy.exception.ExceptionReader;
import org.mule.runtime.api.util.collection.SmallMap;
import org.mule.runtime.internal.exception.SuppressedMuleException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

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
  private static final int ALREADY_FILTERED_INDICATOR = -3;

  public static final String[] DEFAULT_STACKTRACE_FILTER = new String[] {
      "org.mule.runtime.core.privileged.processor.AbstractInterceptingMessageProcessor",
      "org.mule.runtime.core.privileged.processor.chain",
      "org.mule.runtime.core.internal.processor.chain",
      "reactor.core"};

  private static final int EXCEPTION_THRESHOLD = 3;
  private static boolean verbose = true;
  private static boolean initialised = false;

  /**
   * A list of the exception readers to use for different types of exceptions
   */
  private static List<ExceptionReader> globalExceptionReaders = new CopyOnWriteArrayList<>();

  /**
   * A list of the exception readers and the classloader that loaded it to use for different types of exceptions
   */
  private static List<ExceptionReader> exceptionReaders = new CopyOnWriteArrayList<>();

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

  static {
    initialise();
  }

  /**
   * Do not instantiate.
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
   * Gets an exception reader for the exception. The currentThread's {@link ClassLoader} is used to determine the appropriate
   * reader, querying only those registered by the same or an ancestor classloader.
   *
   * @param t the exception to get a reader for
   * @return either a specific reader or an instance of DefaultExceptionReader. This method never returns null;
   */
  public static ExceptionReader getExceptionReader(Throwable t) {
    for (ExceptionReader exceptionReader : globalExceptionReaders) {
      if (exceptionReader.getExceptionType().isInstance(t)) {
        return exceptionReader;
      }
    }

    if (!exceptionReaders.isEmpty()) {
      ClassLoader tccl = currentThread().getContextClassLoader();

      for (ExceptionReader exceptionReader : exceptionReaders) {
        ClassLoader currentCl = tccl;

        // The ExceptionReader is registered with its plugin classloader, but the TCCL when looking it up is the one for the
        // application.
        while (currentCl != null && currentCl != ExceptionHelper.class.getClassLoader()
            && currentCl != exceptionReader.getClass().getClassLoader()) {
          currentCl = currentCl.getParent();
        }

        if (currentCl == exceptionReader.getClass().getClassLoader()
            && exceptionReader.getExceptionType().isInstance(t)) {
          return exceptionReader;
        }
      }
    }

    return defaultExceptionReader;
  }

  public static MuleException getRootMuleException(Throwable t) {
    Throwable cause = t;
    MuleException exception = null;
    MuleException suppressedMuleException = null;
    // Info is added to the wrapper exceptions. We add them to the root mule exception so the gotten info is properly logged.
    Map<String, Object> muleExceptionInfo = new SmallMap<>();

    while (cause != null && cause != suppressedMuleException) {

      if (cause instanceof MuleException) {
        muleExceptionInfo.putAll(((MuleException) cause).getInfo());
        if (cause instanceof SuppressedMuleException) {
          suppressedMuleException = ((SuppressedMuleException) cause).getSuppressedException();
        } else {
          exception = (MuleException) cause;
        }
      }

      final Throwable tempCause = getExceptionReader(cause).getCause(cause);
      if (verbose) {
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
      exception.addAllInfo(muleExceptionInfo);
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

    return verbose ? root : sanitize(root);
  }

  public static String getExceptionStack(Throwable t) {
    MuleException rootMule = getRootMuleException(t);
    StringBuilder buf = new StringBuilder();

    ExceptionReader rootMuleReader = getExceptionReader(rootMule);
    buf.append(rootMuleReader.getMessage(rootMule)).append(" (").append(rootMule.getClass().getName()).append(")")
        .append(lineSeparator());

    if (verbose) {
      Throwable root = getRootException(t);
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

    String stackTraceFilterString = System.getProperty("mule.stacktrace.filter");
    if (stackTraceFilterString != null) {
      stackTraceFilter = stackTraceFilterString.split(",");
    }

    registerGlobalExceptionReader(new NamingExceptionReader());
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
    List<StackTraceElement> newTrace = new ArrayList<>();

    String currentlyMatchedPrefix = null;
    int currentlyMatchedPrefixCount = 0;

    for (StackTraceElement stackTraceElement : trace) {
      Optional<String> matchedPrefix = matchedMuleInternalClassPrefix(stackTraceElement);
      if (!matchedPrefix.isPresent()) {
        if (currentlyMatchedPrefix != null) {
          newTrace.add(createFilteredStackEntry(currentlyMatchedPrefix, currentlyMatchedPrefixCount));
          currentlyMatchedPrefix = null;
          currentlyMatchedPrefixCount = 0;
        }
        newTrace.add(stackTraceElement);
      } else {
        if (currentlyMatchedPrefix != null) {
          if (currentlyMatchedPrefix.equals(matchedPrefix.get())) {
            currentlyMatchedPrefixCount++;
            continue;
          }

          newTrace.add(createFilteredStackEntry(currentlyMatchedPrefix, currentlyMatchedPrefixCount));
        }
        currentlyMatchedPrefix = matchedPrefix.get();
        currentlyMatchedPrefixCount = 1;
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

  private static StackTraceElement createFilteredStackEntry(String currentlyMatchedPrefix, int currentlyMatchedPrefixCount) {
    return new StackTraceElement(currentlyMatchedPrefix,
                                 format("* (%d elements filtered from stack; set debug level logging or '-D"
                                     + MULE_VERBOSE_EXCEPTIONS + "=true' for everything)", currentlyMatchedPrefixCount),
                                 null, ALREADY_FILTERED_INDICATOR);
  }


  /**
   * Removes some internal Mule entries from the stacktrace. Modifies the passed-in throwable stacktrace.
   */
  public static Throwable summarise(Throwable t, int depth) {
    t = sanitize(t);
    StackTraceElement[] trace = t.getStackTrace();

    int newStackDepth = min(trace.length, depth);
    StackTraceElement[] newTrace = new StackTraceElement[newStackDepth];

    arraycopy(trace, 0, newTrace, 0, newStackDepth);
    t.setStackTrace(newTrace);

    return t;
  }

  private static Optional<String> matchedMuleInternalClassPrefix(StackTraceElement stackTraceElement) {
    if (stackTraceElement.getLineNumber() == ALREADY_FILTERED_INDICATOR) {
      return empty();
    }

    /*
     * Sacrifice the code quality for the sake of keeping things simple - the alternative would be to pass MuleContext into every
     * exception constructor.
     */
    for (String mulePackage : stackTraceFilter) {
      if (stackTraceElement.getClassName().startsWith(mulePackage)) {
        return of(mulePackage);
      }
    }
    return empty();
  }

  /**
   * @param t
   * @return t and its recursive causes, ordered from outer to inner.
   */
  public static List<Throwable> getExceptionsAsList(Throwable t) {
    List<Throwable> exceptions = new ArrayList<>(4);
    Throwable cause = t;
    MuleException suppressedCause = null;
    while (cause != null && cause != suppressedCause) {
      if (cause instanceof SuppressedMuleException) {
        suppressedCause = ((SuppressedMuleException) cause).getSuppressedException();
      } else {
        exceptions.add(cause);
      }
      cause = getExceptionReader(cause).getCause(cause);
      // address some misbehaving exceptions, avoid endless loop
      if (t == cause) {
        break;
      }
    }
    return exceptions;
  }

  /**
   * Registers an exception reader on the Mule Runtime.
   * <p>
   * Only the Runtime itself may register global readers.
   *
   * @param reader the reader to register.
   */
  public static void registerGlobalExceptionReader(ExceptionReader reader) {
    if (reader.getClass().getClassLoader() != ExceptionHelper.class.getClassLoader()) {
      throw new IllegalArgumentException("Only the Runtime itself may register global readers.");
    }
    globalExceptionReaders.add(reader);
  }

  /**
   * Registers an exception reader on the Mule Runtime to be used only by the artifact it belongs to
   *
   * @param reader the reader to register.
   */
  public static void registerExceptionReader(ExceptionReader reader) {
    for (ExceptionReader exceptionReader : globalExceptionReaders) {
      if (exceptionReader.getExceptionType().equals(reader.getExceptionType())) {
        throw new IllegalArgumentException(format("There's a globalExceptionReader already registerd for '%s': %s",
                                                  reader.getExceptionType(), reader.toString()));
      }
    }

    exceptionReaders.add(reader);
  }

  /**
   * Unregisters an exception reader from the Mule Runtime
   *
   * @param reader the reader to register.
   * @return {@code true} if the passed {@code reader} is registered
   */
  public static boolean unregisterExceptionReader(ExceptionReader reader) {
    return exceptionReaders.remove(reader);
  }

}
