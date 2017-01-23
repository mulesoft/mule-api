/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import org.mule.runtime.api.app.declaration.fluent.ParameterListValue;
import org.mule.runtime.api.app.declaration.fluent.ParameterObjectValue;

/**
 * Used in {@link ParameterValue#accept(ParameterValueVisitor)} as a visitor pattern.
 *
 * @since 1.0
 */
public interface ParameterValueVisitor {

  default void visitSimpleValue(String value) {
    // do nothing
  }

  default void visitListValue(ParameterListValue list) {
    // do nothing
  }

  default void visitObjectValue(ParameterObjectValue objectValue) {
    // do nothing
  }
}
