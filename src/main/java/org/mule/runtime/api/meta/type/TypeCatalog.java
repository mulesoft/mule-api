/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.type;

import org.mule.api.annotation.NoImplement;
import org.mule.metadata.api.annotation.TypeIdAnnotation;
import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.api.model.ObjectType;
import org.mule.runtime.api.dsl.DslResolvingContext;
import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.internal.meta.type.DefaultTypeCatalog;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * Container for all the {@link ObjectType types} declared in a given {@link DslResolvingContext}, defined by the {@link Set} of
 * {@link ExtensionModel}s that coexist in.
 * <p>
 * A {@link TypeCatalog} provides access to all the declared types, along with their type it mapping, storing the relation of a
 * given type and its declared subtypes across all the {@link ExtensionModel} available in the context.
 *
 * @since 1.0
 */
@NoImplement
public interface TypeCatalog {

  static TypeCatalog getDefault(Set<ExtensionModel> extensions) {
    return new DefaultTypeCatalog(extensions);
  }

  /**
   * @return the {@link ObjectType} with the given {@link TypeIdAnnotation typeId} if one is present in {@code this}
   *         {@link TypeCatalog}, or {@link Optional#empty} otherwise.
   */
  Optional<ObjectType> getType(String typeId);

  /**
   * @return an immutable {@link Set} with all the {@link ObjectType} defined in {@code this} {@link TypeCatalog}
   */
  Collection<ObjectType> getTypes();

  /**
   * Returns a {@link Set} with all the declared {@link ObjectType} subtypes for the indicated {@link ObjectType} {@code type}.
   * <p>
   * Lookup will be performed first by {@link TypeIdAnnotation typeId}, defaulting to {@link ObjectType type} comparison if no
   * {@link TypeIdAnnotation typeId} was found
   *
   * @param type the {@link ObjectType} for which to retrieve its declared subTypes
   * @return a {@link Set} with all the declared subtypes for the indicated {@link ObjectType}
   */
  Set<ObjectType> getSubTypes(ObjectType type);

  /**
   * Returns a {@link Set} with all the declared {@link ObjectType} that are considered super types from the given
   * {@link ObjectType} {@code type}.
   * <p>
   * The lookup will be performed by looking through all the mappings that contains the given {@code type} as subtype in
   * {@code this} {@link TypeCatalog} and storing the base type and again looking the super type of the found base type.
   *
   * @param type {@link ObjectType} to look for their super types
   * @return a {@link Set} with all the declared supertypes for the indicated {@link ObjectType}
   */
  Set<ObjectType> getSuperTypes(ObjectType type);

  /**
   * @return a {@link Collection} with all the types that are extended by another type
   */
  Collection<ObjectType> getAllBaseTypes();

  /**
   * @return a {@link Collection} with all the types which extend another type, in no particular order
   */
  Collection<ObjectType> getAllSubTypes();

  /**
   * Type comparison will be performed first by {@link TypeIdAnnotation typeId} in the context of subTypes mapping. If a
   * {@link TypeIdAnnotation typeId} is available for the given {@code type}, the lookup will be performed by
   * {@link TypeIdAnnotation#getValue()} disregarding {@link MetadataType} equality in its full extent, which includes type
   * generics and interfaces implementations, and defaulting to {@link MetadataType#equals} comparison if no
   * {@link TypeIdAnnotation typeId} was found
   *
   * @param type the {@link MetadataType} for which to retrieve its declared subTypes
   * @return <tt>true</tt> if this map contains a mapping for the specified key {@link MetadataType type}
   */
  boolean containsBaseType(ObjectType type);

  /**
   * @return a {@link Collection} with all the types declared by the extension with name {@code extensionName}
   */
  Collection<ObjectType> getExtensionTypes(String extensionName);

  /**
   * @return an {@link Optional} with the extension name that contributed with this given type
   */
  Optional<String> getDeclaringExtension(String typeId);

}
