/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;


import static java.util.Objects.hash;
import org.mule.metadata.api.model.MetadataType;

import org.apache.commons.lang3.StringUtils;

/**
 * A model which describes that an extension is importing an
 * {@link #getImportedType() imported type} from an extension of
 * a given {@link #getOriginExtensionName() name}
 *
 * @since 1.0
 */
public final class ImportedTypeModel {

  private final String originExtensionName;
  private final MetadataType importedType;

  /**
   * Creates a new instance
   *
   * @param originExtensionName the name of the extension which originally defines the type
   * @param importedType        the type to be imported
   * @throws IllegalArgumentException if {@code originExtensionName} is blank or {@code importedType} is {@code null}
   */
  public ImportedTypeModel(String originExtensionName, MetadataType importedType) {
    if (StringUtils.isBlank(originExtensionName)) {
      throw new IllegalArgumentException("originExtensionName cannot be blank");
    }

    if (importedType == null) {
      throw new IllegalArgumentException("importedType cannot be null");
    }

    this.originExtensionName = originExtensionName;
    this.importedType = importedType;
  }

  public String getOriginExtensionName() {
    return originExtensionName;
  }

  public MetadataType getImportedType() {
    return importedType;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ImportedTypeModel) {
      ImportedTypeModel other = (ImportedTypeModel) obj;
      return originExtensionName.equals(other.getOriginExtensionName()) && importedType.equals(other.getImportedType());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return hash(importedType, originExtensionName);
  }
}
