/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.i18n;

import static org.mule.runtime.internal.util.JpmsUtils.isMemberOfNamedModule;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base {@code MessageFactory} for I18n supported messages.
 *
 * @since 1.0
 */
public abstract class I18nMessageFactory {

  /**
   * Default is {@link ReloadControl.Always}.
   */
  public static final ResourceBundle.Control DEFAULT_RELOAD_CONTROL = new ReloadControl.Always();

  /**
   * This error code is used for {@link I18nMessage} instances that are not read from a resource bundles but are created only with
   * a string.
   */
  private static final int STATIC_ERROR_CODE = -1;
  private static final transient Object[] EMPTY_ARGS = new Object[] {};

  private transient Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * Do not use the default reload control to avoid loading the resource bundle upon each request. Subclasses can override to
   * provide a different default.
   */
  protected static final ResourceBundle.Control reloadControl = new ResourceBundle.Control() {

    // Avoid trying to load the java class.
    private List<String> formats = new ArrayList<>(union(FORMAT_PROPERTIES, FORMAT_CLASS));

    @Override
    public List<String> getFormats(String baseName) {
      return formats;
    };
  };

  private static <T> List<T> union(List<T> list1, List<T> list2) {
    Set<T> set = new HashSet<T>();

    set.addAll(list1);
    set.addAll(list2);

    return new ArrayList<T>(set);
  }

  /**
   * Computes the bundle's full path ({@code META-INF/org/mule/i18n/&lt;bundleName&gt;-messages.properties}) from
   * {@code bundleName}.
   *
   * @param bundleName Name of the bundle without the &quot;messages&quot; suffix and without file extension.
   */
  protected static String getBundlePath(String bundleName) {
    return "META-INF/org/mule/runtime/core/i18n/" + bundleName + "-messages";
  }

  /**
   * Factory method to create a new {@link I18nMessage} instance that is filled with the formatted message with id {@code code}
   * from the resource bundle {@code bundlePath}.
   *
   * @param bundlePath complete path to the resource bundle for lookup
   * @param code       numeric code of the message
   * @param arg
   * @see #getBundlePath(String)
   */
  protected I18nMessage createMessage(String bundlePath, int code, Object arg) {
    return createMessage(bundlePath, code, new Object[] {arg});
  }

  /**
   * Factory method to create a new {@link I18nMessage} instance that is filled with the formatted message with id {@code code}
   * from the resource bundle {@code bundlePath}.
   *
   * @param bundlePath complete path to the resource bundle for lookup
   * @param code       numeric code of the message
   * @param arg1
   * @param arg2
   * @see #getBundlePath(String)
   */
  protected I18nMessage createMessage(String bundlePath, int code, Object arg1, Object arg2) {
    return createMessage(bundlePath, code, new Object[] {arg1, arg2});
  }

  /**
   * Factory method to create a new {@link I18nMessage} instance that is filled with the formatted message with id {@code code}
   * from the resource bundle {@code bundlePath}.
   *
   * @param bundlePath complete path to the resource bundle for lookup
   * @param code       numeric code of the message
   * @param arg1
   * @param arg2
   * @param arg3
   * @see #getBundlePath(String)
   */
  protected I18nMessage createMessage(String bundlePath, int code, Object arg1, Object arg2, Object arg3) {
    return createMessage(bundlePath, code, new Object[] {arg1, arg2, arg3});
  }

  /**
   * Factory method to create a new {@link I18nMessage} instance that is filled with the formatted message with id {@code code}
   * from the resource bundle {@code bundlePath}.
   *
   * <b>Attention:</b> do not confuse this method with {@link this#createMessage}.
   *
   * @param bundlePath complete path to the resource bundle for lookup
   * @param code       numeric code of the message
   * @param arguments
   * @see #getBundlePath(String)
   */
  protected I18nMessage createMessage(String bundlePath, int code, Object... arguments) {
    String messageString = getString(bundlePath, code, arguments);
    return new I18nMessage(messageString, code, arguments);
  }

  /**
   * Factory method to create a new {@link I18nMessage} instance that is filled with the formatted message with id {@code code}
   * from the resource bundle {@code bundlePath}.
   *
   * @param bundlePath complete path to the resource bundle for lookup
   * @param code       numeric code of the message
   */
  protected I18nMessage createMessage(String bundlePath, int code) {
    String messageString = getString(bundlePath, code, null);
    return new I18nMessage(messageString, code, EMPTY_ARGS);
  }

  /**
   * Factory method to create a {@link I18nMessage} instance that is not read from a resource bundle.
   *
   * @param message Message's message text
   * @return a Messsage instance that has an error code of -1 and no arguments.
   */
  public static I18nMessage createStaticMessage(String message) {
    return new I18nMessage(message, STATIC_ERROR_CODE, EMPTY_ARGS);
  }

  /**
   * Factory method to create a {@link I18nMessage} instance that is not read from a resource bundle.
   *
   * @param message   Static message text that may contain format specifiers
   * @param arguments Arguments referenced by the format specifiers in the message string.
   * @return a Messsage instance that has an error code of -1 and no arguments.
   */
  public static I18nMessage createStaticMessage(String message, Object... arguments) {
    return new I18nMessage(String.format(message, arguments), STATIC_ERROR_CODE, EMPTY_ARGS);
  }

  /**
   * Factory method to read the message with code {@code code} from the resource bundle.
   *
   * @param bundlePath complete path to the resource bundle for lookup
   * @param code       numeric code of the message
   * @return formatted error message as {@link String}
   */
  protected String getString(String bundlePath, int code) {
    return getString(bundlePath, code, null);
  }

  /**
   * Factory method to read the message with code {@code code} from the resource bundle.
   *
   * @param bundlePath complete path to the resource bundle for lookup
   * @param code       numeric code of the message
   * @param arg
   * @return formatted error message as {@link String}
   */
  protected String getString(String bundlePath, int code, Object arg) {
    Object[] arguments = new Object[] {arg};
    return getString(bundlePath, code, arguments);
  }

  /**
   * Factory method to read the message with code {@code code} from the resource bundle.
   *
   * @param bundlePath complete path to the resource bundle for lookup
   * @param code       numeric code of the message
   * @param arg1
   * @param arg2
   * @return formatted error message as {@link String}
   */
  protected String getString(String bundlePath, int code, Object arg1, Object arg2) {
    Object[] arguments = new Object[] {arg1, arg2};
    return getString(bundlePath, code, arguments);
  }

  protected String getString(String bundlePath, int code, Object[] args) {
    // We will throw a MissingResourceException if the bundle name is invalid
    // This happens if the code references a bundle name that just doesn't exist
    ResourceBundle bundle = getBundle(bundlePath);

    try {
      String m = bundle.getString(String.valueOf(code));
      if (m == null) {
        logger.error("Failed to find message for id " + code + " in resource bundle " + bundlePath);
        return "";
      }

      return MessageFormat.format(m, args);
    } catch (MissingResourceException e) {
      logger.error("Failed to find message for id " + code + " in resource bundle " + bundlePath);
      return "";
    }
  }

  /**
   * @throws MissingResourceException if resource is missing
   */
  protected ResourceBundle getBundle(String bundlePath) {
    Locale locale = Locale.getDefault();
    if (logger.isTraceEnabled()) {
      logger.trace("Loading resource bundle: " + bundlePath + " for locale " + locale);
    }

    if (isMemberOfNamedModule(I18nMessageFactory.class)) {
      return ResourceBundle.getBundle(bundlePath, locale, getClassLoader());
    } else {
      return ResourceBundle.getBundle(bundlePath, locale, getClassLoader(), getReloadControl());
    }
  }

  /**
   * Override this method to return the classloader for the bundle/module which contains the needed resource files.
   */
  protected ClassLoader getClassLoader() {
    return getClass().getClassLoader();
  }

  /**
   * Subclasses should override to customize the bundle reload control. Implementations must save the instance in a field for
   * stateful reload control. Return null to fallback to default JVM behavior (permanent cache).
   *
   * Note: this method has no use when running on the module-path, as {@link ResourceBundle.Control} is not used for named
   * modules.
   *
   * @see #DEFAULT_RELOAD_CONTROL
   */
  protected ResourceBundle.Control getReloadControl() {
    return reloadControl;
  }
}


