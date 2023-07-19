/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.display;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.hash;
import static org.mule.runtime.api.meta.model.display.PathModel.Type.DIRECTORY;
import org.mule.api.annotation.NoExtend;

import java.util.List;
import java.util.Objects;

/**
 * A model which provides directives for parameters that points to a file or directory.
 *
 * @since 1.0
 */
@NoExtend
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
    if (obj == null || !(obj instanceof PathModel)) {
      return false;
    }

    PathModel that = (PathModel) obj;
    return Objects.equals(type, that.type)
        && Objects.equals(fileExtensions, that.fileExtensions)
        && Objects.equals(acceptsUrls, that.acceptsUrls)
        && Objects.equals(location, that.location);
  }

  @Override
  public int hashCode() {
    return hash(type, fileExtensions, acceptsUrls, location);
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
