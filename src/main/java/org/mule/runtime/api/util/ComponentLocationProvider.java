/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static com.github.benmanes.caffeine.cache.Caffeine.newBuilder;
import static org.mule.runtime.api.component.Component.Annotations.NAME_ANNOTATION_KEY;
import static org.mule.runtime.api.component.Component.Annotations.SOURCE_ELEMENT_ANNOTATION_KEY;

import org.mule.api.annotation.NoExtend;
import org.mule.runtime.api.component.Component;

import com.github.benmanes.caffeine.cache.Cache;

/**
 * Provides a standard way to generate a log entry or message that references an element in a flow.
 *
 * @since 4.0
 */
@NoExtend
public class ComponentLocationProvider {

  private static final Cache<Object, String> processorRepresentation = newBuilder().build();

  /**
   * Generates a representation of a flow element to be logged in a standard way.
   *
   * @param appId
   * @param processorPath
   * @param element
   * @return
   */
  public static String resolveProcessorRepresentation(String appId, String processorPath, Object element) {
    return processorRepresentation.get(element, c -> {
      StringBuilder stringBuilder = new StringBuilder();

      stringBuilder.append(processorPath)
          .append(" @ ")
          .append(appId);

      String sourceFile = getSourceFile((Component) c);
      if (sourceFile != null) {
        stringBuilder.append(":")
            .append(sourceFile)
            .append(":")
            .append(getSourceFileLine((Component) c));
      }

      String docName = getDocName(c);
      if (docName != null) {
        stringBuilder.append(" (")
            .append(docName)
            .append(")");
      }

      return stringBuilder.toString();
    });

  }

  /**
   * @param element the element to get the {@code doc:name} from.
   * @return the {@code doc:name} attribute value of the element.
   */
  public static String getDocName(Object element) {
    if (element instanceof Component) {
      Object docName = ((Component) element).getAnnotation(NAME_ANNOTATION_KEY);
      return docName != null ? docName.toString() : null;
    } else {
      return null;
    }
  }

  protected static String getSourceFile(Component component) {
    return component.getLocation() != null ? component.getLocation().getFileName().orElse("unknown") : "internal";
  }

  protected static Integer getSourceFileLine(Component component) {
    return component.getLocation() != null ? component.getLocation().getLineInFile().orElse(-1) : -1;
  }

  /**
   * @param component the component object from the configuration
   * @return the source code associated to the component. It may be null if the component was not created from a configuration
   *         file.
   */
  public static String getSourceCode(Component component) {
    return (String) component.getAnnotation(SOURCE_ELEMENT_ANNOTATION_KEY);
  }

}
