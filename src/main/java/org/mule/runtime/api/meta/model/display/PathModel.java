/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.meta.model.display;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.mule.runtime.api.meta.model.display.PathModel.Type.DIRECTORY;

import org.mule.api.annotation.NoImplement;

import java.util.List;

/**
 * A model which provides directives for parameters that points to a file or directory.
 *
 * @since 1.0
 */
@NoImplement
public class PathModel {

  /**
   * Classifies the generic location where the path is allowed to point
   */
  public enum Location {

    /**
     * The path points to a resource embedded into the Mule application
     */
    EMBEDDED,

    /**
     * The path points to a resource outside of the mule application
     */
    EXTERNAL,

    /**
     * The path can be any of the above
     */
    ANY

  }

  private final Type type;
  private final List<String> fileExtensions;
  private final boolean acceptsUrls;
  private final Location location;

  public PathModel(Type type, boolean acceptsUrls, Location location, String[] fileExtensions) {
    this.type = type;
    this.acceptsUrls = acceptsUrls;
    this.location = location;
    this.fileExtensions = unmodifiableList(asList(fileExtensions));
  }

  /**
   * @return all the parameter supported file extensions.
   */
  public List<String> getFileExtensions() {
    return type == DIRECTORY ? emptyList() : fileExtensions;
  }

  /**
   * @return whether the parameter is a reference to a directory or not.
   */
  public Type getType() {
    return type;
  }

  /**
   * @return whether the parameter accepts urls values or not.
   */
  public boolean acceptsUrls() {
    return acceptsUrls;
  }

  /**
   * @return a classifier for the path's generic {@link Location}
   */
  public Location getLocation() {
    return location;
  }

  @Override
  public boolean equals(Object obj) {
    return reflectionEquals(obj, this);
  }

  @Override
  public int hashCode() {
    return reflectionHashCode(this);
  }

  public enum Type {

    /**
     * indicates that the path value only accepts directory paths.
     */
    DIRECTORY,

    /**
     * indicates that the path value only accepts specific file paths.
     */
    FILE,

    /**
     * indicates that the path value accepts both file or directory paths.
     */
    ANY
  }
}
