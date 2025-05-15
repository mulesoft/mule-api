/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import static java.util.Collections.emptyMap;

import org.mule.runtime.api.legacy.exception.ExceptionReader;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Name;
import javax.naming.NamingException;

class NamingExceptionReader implements ExceptionReader {

  /**
   * Displayed when no remaining or resolved name found.
   */
  protected static final String MISSING_NAME_DISPLAY_VALUE = "<none>";

  @Override
  public String getMessage(Throwable t) {
    return (t instanceof NamingException ? ((NamingException) t).toString(true) : "<unknown>");
  }

  @Override
  public Throwable getCause(Throwable t) {
    return (t instanceof NamingException ? ((NamingException) t).getCause() : null);
  }

  @Override
  public Class<?> getExceptionType() {
    return NamingException.class;
  }

  /**
   * Returns a map of the non-stanard information stored on the exception
   *
   * @param t the exception to extract the information from
   * @return a map of the non-stanard information stored on the exception
   */
  @Override
  public Map<String, Object> getInfo(Throwable t) {
    if (t instanceof NamingException) {
      NamingException e = (NamingException) t;

      Map<String, Object> info = new HashMap<String, Object>();
      final Name remainingName = e.getRemainingName();
      final Name resolvedName = e.getResolvedName();
      info.put("Remaining Name", remainingName == null ? MISSING_NAME_DISPLAY_VALUE : remainingName.toString());
      info.put("Resolved Name", resolvedName == null ? MISSING_NAME_DISPLAY_VALUE : resolvedName.toString());
      return info;
    } else {
      return emptyMap();
    }
  }
}
