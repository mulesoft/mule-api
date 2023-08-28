/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;


import static java.util.Objects.hash;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import org.mule.metadata.api.model.ObjectType;

/**
 * A model which describes that an extension is importing an {@link #getImportedType() imported type}.
 *
 * @since 1.0
 */
public final class ImportedTypeModel {

  private final ObjectType importedType;

  /**
   * Creates a new instance
   *
   * @param importedType the type to be imported
   * @throws IllegalArgumentException if {@code originExtensionName} is blank or {@code importedType} is {@code null}
   */
  public ImportedTypeModel(ObjectType importedType) {
    checkArgument(importedType != null, "importedType cannot be null");
    this.importedType = importedType;
  }

  public ObjectType getImportedType() {
    return importedType;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ImportedTypeModel) {
      ImportedTypeModel other = (ImportedTypeModel) obj;
      return importedType.equals(other.getImportedType());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return hash(importedType);
  }

  @Override
  public String toString() {
    return "ImportedTypeModel {" + importedType.toString() + "}";
  }
}
