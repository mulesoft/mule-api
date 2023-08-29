/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;


import static java.util.stream.Collectors.toCollection;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.api.model.ObjectType;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A model which describes that a given {@link #getBaseType() base type} is extended by a {@link Set} of {@link #getSubTypes()
 * sub-types}. In all cases, the types are described through the {@link MetadataType} model.
 *
 * @since 1.0
 */
public final class SubTypesModel {

  private final ObjectType baseType;
  private final Set<ObjectType> subTypes;

  /**
   * Creates a new instance
   *
   * @param baseType the type that is extended
   * @param subTypes the extending types
   * @throws IllegalArgumentException if baseType is {@code null} or subTypes is {@code null} or empty
   */
  public SubTypesModel(MetadataType baseType, Set<MetadataType> subTypes) {
    checkArgument(baseType != null, "baseType cannot be null");
    checkArgument(baseType instanceof ObjectType, "Only ObjectTypes can be extended");

    checkArgument(subTypes != null && !subTypes.isEmpty(), "subTypes cannot be null nor empty");
    checkArgument(subTypes.stream().allMatch(s -> s instanceof ObjectType),
                  "subTypes of an ObjectType can only be instances of ObjectType");

    this.baseType = (ObjectType) baseType;
    this.subTypes = subTypes.stream().map(s -> (ObjectType) s).collect(toCollection(LinkedHashSet::new));
  }

  public ObjectType getBaseType() {
    return baseType;
  }

  public Set<ObjectType> getSubTypes() {
    return subTypes;
  }

  @Override
  public String toString() {
    return "SubTypesModel {" + baseType.toString() + " <- " + subTypes.toString() + "}";
  }

}
