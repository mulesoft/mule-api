/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.util.classloader;

import static java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE;

import java.lang.StackWalker.StackFrame;
import java.util.Arrays;

public class CallerClassUtils {

  private CallerClassUtils() {
    // Nothing to do
  }

  public static ClassLoader getCallerClassClassLoader() {
    StackWalker walker = StackWalker.getInstance(RETAIN_CLASS_REFERENCE);
    return walker.walk(s -> s.map(StackFrame::getDeclaringClass)
        .map(Class::getClassLoader)
        .filter(classLoader -> classLoader != null && classLoader != CallerClassUtils.class.getClassLoader())
        .findFirst())
        .orElseThrow(() -> new RuntimeException("Class loader not found"));
  }

}
