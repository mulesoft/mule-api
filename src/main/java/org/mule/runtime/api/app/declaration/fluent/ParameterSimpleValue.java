/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.MULTILINE;
import static java.util.regex.Pattern.compile;
import static org.apache.commons.lang3.StringUtils.removeEnd;
import static org.apache.commons.lang3.StringUtils.removeStart;
import org.mule.runtime.api.app.declaration.ParameterElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterValue;
import org.mule.runtime.api.app.declaration.ParameterValueVisitor;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents the configured simple value of a given {@link ParameterElementDeclaration}.
 *
 * @since 1.0
 */
public final class ParameterSimpleValue implements ParameterValue {

  private static final Pattern CDATA = compile("^<!\\[CDATA\\[(.+)\\]\\]>$", DOTALL | MULTILINE);
  private final String text;
  private final boolean isCData;

  private ParameterSimpleValue(String text, boolean asCData) {
    this.text = text;
    this.isCData = asCData;
  }

  public static ParameterValue of(String text) {
    return isCData(text) ? cdata(text) : plain(text);
  }

  private static boolean isCData(String text) {
    return CDATA.matcher(text).matches();
  }

  public static ParameterValue plain(String text) {
    return new ParameterSimpleValue(text, false);
  }

  public static ParameterValue cdata(String text) {
    text = removeEnd(removeStart(text, "<![CDATA["), "]]>");
    return new ParameterSimpleValue(text, true);
  }

  public String getValue() {
    return text;
  }

  public boolean isCData() {
    return isCData;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(ParameterValueVisitor valueVisitor) {
    valueVisitor.visitSimpleValue(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ParameterSimpleValue that = (ParameterSimpleValue) o;
    return isCData ? that.isCData : !that.isCData && StringUtils.equals(text, that.text);
  }

  @Override
  public int hashCode() {
    return (isCData ? 31 : 0) + (text != null ? text.hashCode() : 0);
  }

  @Override
  public String toString() {
    return text;
  }

}
