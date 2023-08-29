/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static org.mule.runtime.api.component.Component.Annotations.NAME_ANNOTATION_KEY;

import org.mule.api.annotation.NoExtend;
import org.mule.runtime.api.component.Component;

/**
 * Provides a standard way to generate a log entry or message that references an element in a flow.
 *
 * @since 4.0
 */
@NoExtend
public class ComponentLocationProvider {

  /**
   * Generates a representation of a flow element to be logged in a standard way.
   *
   * @param appId
   * @param processorPath
   * @param element
   * @return
   * @deprecated Use {@link Component#getRepresentation()} instead.
   */
  @Deprecated
  public static String resolveProcessorRepresentation(String appId, String processorPath, Object element) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(processorPath)
        .append(" @ ")
        .append(appId);

    String sourceFile = getSourceFile((Component) element);
    if (sourceFile != null) {
      stringBuilder.append(":")
          .append(sourceFile)
          .append(":")
          .append(getSourceFileLine((Component) element));
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

  protected static int getSourceFileLine(Component component) {
    return component.getLocation() != null ? component.getLocation().getLine().orElse(-1) : -1;
  }

  /**
   * @param component the component object from the configuration
   * @return the source code associated to the component. It may be null if the component was not created from a configuration
   *         file.
   */
  public static String getSourceCode(Component component) {
    return component.getDslSource();
  }

}
