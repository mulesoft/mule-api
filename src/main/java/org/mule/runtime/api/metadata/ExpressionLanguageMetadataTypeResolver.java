/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;


import org.mule.metadata.api.model.MetadataType;

/**
 * Resolves the metadata for a specific expression language.
 */
public interface ExpressionLanguageMetadataTypeResolver {

  /**
   * Returns the expected {@link MuleEventMetadataType} for the given expression.
   * @param expression The scripting text.
   * @return The expected input
   */
  MuleEventMetadataType getExpectedEventType(String expression);

  /**
   * Returns the result type expression when invoked with the given {@link MuleEventMetadataType}.
   * @param input The input type
   * @param expression The scripting text.
   * @return The return type of the expression.
   */
  MetadataType getOutputType(MuleEventMetadataType input, String expression);

}
