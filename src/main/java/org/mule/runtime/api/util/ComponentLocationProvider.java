/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import org.mule.runtime.api.meta.AnnotatedObject;

import javax.xml.namespace.QName;

/**
 * Provides a standard way to generate a log entry or message that references an element in a flow.
 *
 * @since 4.0
 */
public class ComponentLocationProvider {

  protected static final QName NAME_ANNOTATION_KEY = new QName("http://www.mulesoft.org/schema/mule/documentation", "name");
  protected static final QName SOURCE_FILE_ANNOTATION_KEY =
      new QName("http://www.mulesoft.org/schema/mule/documentation", "sourceFileName");
  protected static final QName SOURCE_FILE_LINE_ANNOTATION_KEY =
      new QName("http://www.mulesoft.org/schema/mule/documentation", "sourceFileLine");

  /**
   * Generates a representation of a flow element to be logged in a standard way.
   *
   * @param appId
   * @param processorPath
   * @param element
   * @return
   */
  public static String resolveProcessorRepresentation(String appId, String processorPath, Object element) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(processorPath)
        .append(" @ ")
        .append(appId);

    String sourceFile = getSourceFile((AnnotatedObject) element);
    if (sourceFile != null) {
      stringBuilder.append(":")
          .append(sourceFile)
          .append(":")
          .append(getSourceFileLine((AnnotatedObject) element));
    }

    String docName = getDocName(element);
    if (docName != null) {
      stringBuilder.append(" (")
          .append(docName)
          .append(")");
    }

    return stringBuilder.toString();
  }

  /**
   * @param element the element to get the {@code doc:name} from.
   * @return the {@code doc:name} attribute value of the element.
   */
  public static String getDocName(Object element) {
    if (element instanceof AnnotatedObject) {
      Object docName = ((AnnotatedObject) element).getAnnotation(NAME_ANNOTATION_KEY);
      return docName != null ? docName.toString() : null;
    } else {
      return null;
    }
  }

  protected static String getSourceFile(AnnotatedObject element) {
    return (String) element.getAnnotation(SOURCE_FILE_ANNOTATION_KEY);
  }

  protected static Integer getSourceFileLine(AnnotatedObject element) {
    return (Integer) element.getAnnotation(SOURCE_FILE_LINE_ANNOTATION_KEY);
  }

}
