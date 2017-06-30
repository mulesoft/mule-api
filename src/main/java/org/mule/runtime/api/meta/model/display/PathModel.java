/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.meta.model.display;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

import java.util.List;

/**
 * A model which provides directives for parameters that points to a file or directory.
 *
 * @since 1.0
 */
public class PathModel {

  private final boolean isDirectory;
  private final List<String> fileExtensions;

  public PathModel(boolean isDirectory, boolean acceptsUrls, String[] fileExtensions) {
    this.isDirectory = isDirectory;
    this.fileExtensions = unmodifiableList(asList(fileExtensions));
  }

  /**
   * @return all the parameter supported file extensions.
   */
  public List<String> getFileExtensions() {
    return fileExtensions;
  }

  /**
   * @return whether the parameter is a reference to a directory or not.
   */
  public boolean isDirectory() {
    return isDirectory;
  }

  /**
   * @return whether the parameter accepts urls values or not.
   */
  public boolean acceptsUrls() {
    return isDirectory;
  }

  @Override
  public boolean equals(Object obj) {
    return reflectionEquals(obj, this);
  }

  @Override
  public int hashCode() {
    return reflectionHashCode(this);
  }
}
