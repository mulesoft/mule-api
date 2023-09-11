/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.meta.type;

import static java.lang.Boolean.getBoolean;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.mule.metadata.api.utils.MetadataTypeUtils.getTypeId;
import static org.mule.runtime.api.util.MuleSystemProperties.SYSTEM_PROPERTY_PREFIX;

import org.mule.metadata.api.annotation.TypeIdAnnotation;
import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.api.model.ObjectType;
import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.api.meta.model.SubTypesModel;
import org.mule.runtime.api.meta.type.TypeCatalog;
import org.mule.runtime.api.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


/**
 * Default implementation of a {@link TypeCatalog}
 *
 * @since 1.0
 */
public final class DefaultTypeCatalog implements TypeCatalog {

  private static final boolean VALIDATE_DUPICATE_IDS = getBoolean(SYSTEM_PROPERTY_PREFIX + "typeCatalog.validateDuplicateIds");

  private final List<SubTypesMappingContainer> mappings = new LinkedList<>();

  // Map<ExtensionName, Map<TypeId, ObjectType>
  private final Map<String, Map<String, ObjectType>> types = new LinkedHashMap<>();

  // Map<TypeId, ExtensionName>
  private final Map<String, String> extensionTypesInvertedIndex = new LinkedHashMap<>();


  public DefaultTypeCatalog(Set<ExtensionModel> extensions) {
    extensions.forEach(e -> {
      Set<SubTypesModel> subTypes = e.getSubTypes();
      if (!subTypes.isEmpty()) {
        mappings.add(new SubTypesMappingContainer(subTypes));
      }

      e.getTypes().forEach(t -> getTypeId(t).ifPresent(id -> {
        if (types.containsKey(e.getName())) {
          types.get(e.getName()).put(id, t);
        } else {
          Map<String, ObjectType> extensionTypesMap = new LinkedHashMap<>();
          extensionTypesMap.put(id, t);
          types.put(e.getName(), extensionTypesMap);
        }
        if (VALIDATE_DUPICATE_IDS && extensionTypesInvertedIndex.containsKey(id)) {
          throw new IllegalStateException("Type '" + id + "' is defined in extensions '"
              + extensionTypesInvertedIndex.get(id) + "' and '" + e.getName() + "'.");
        }
        extensionTypesInvertedIndex.put(id, e.getName());
      }));
    });
  }

  @Override
  public Optional<ObjectType> getType(String typeId) {
    String extensionName = extensionTypesInvertedIndex.get(typeId);
    if (extensionName == null) {
      return empty();
    }
    return ofNullable(types.get(extensionName).get(typeId));
  }

  @Override
  public Collection<ObjectType> getTypes() {
    List<ObjectType> values = new ArrayList<>();
    for (Map<String, ObjectType> extensionTypeMap : types.values()) {
      extensionTypeMap.values().forEach(values::add);
    }
    return unmodifiableCollection(values);
  }

  @Override
  public Collection<ObjectType> getExtensionTypes(String extensionName) {
    if (types.containsKey(extensionName)) {
      return unmodifiableCollection(types.get(extensionName).values());
    }
    return emptyList();
  }

  @Override
  public Optional<String> getDeclaringExtension(String typeId) {
    return ofNullable(extensionTypesInvertedIndex.get(typeId));
  }

  @Override
  public Set<ObjectType> getSubTypes(ObjectType type) {
    return mappings.stream()
        .map(m -> m.getSubTypes(type))
        .flatMap(Collection::stream)
        .collect(toCollection(LinkedHashSet::new));
  }

  @Override
  public Set<ObjectType> getSuperTypes(ObjectType type) {
    return mappings.stream()
        .map(m -> m.getSuperTypes(type))
        .flatMap(Collection::stream)
        .collect(toCollection(LinkedHashSet::new));
  }

  @Override
  public Collection<ObjectType> getAllBaseTypes() {
    return mappings.stream().flatMap(c -> c.getAllBaseTypes().stream()).collect(toCollection(LinkedList::new));
  }

  @Override
  public Collection<ObjectType> getAllSubTypes() {
    return mappings.stream().flatMap(c -> c.getAllSubTypes().stream()).collect(toCollection(LinkedList::new));
  }

  @Override
  public boolean containsBaseType(ObjectType type) {
    return mappings.stream().anyMatch(c -> c.containsBaseType(type));
  }

  /**
   * Immutable container for type mapping, storing the relation of a given type and its declared subtypes
   *
   * @since 1.0
   */
  private static class SubTypesMappingContainer {

    private final Map<ObjectType, Set<ObjectType>> subTypesMapping;
    private final Map<String, Set<ObjectType>> subTypesById;

    SubTypesMappingContainer(Collection<SubTypesModel> subTypes) {
      subTypesMapping = toSubTypesMap(subTypes);
      this.subTypesById = subTypesMapping.entrySet().stream()
          .map(entry -> new Pair<>(getTypeId(entry.getKey()).orElse(null), entry.getValue()))
          .filter(p -> p.getFirst() != null)
          .collect(toMap(Pair::getFirst, Pair::getSecond, (k, v) -> k, LinkedHashMap::new));
    }

    /**
     * Returns a {@link List} with all the declared {@link MetadataType} subtypes for the indicated {@link MetadataType}
     * {@code type}.
     * <p>
     * Lookup will be performed first by {@link TypeIdAnnotation typeId}, defaulting to {@link MetadataType type} comparison if no
     * {@link TypeIdAnnotation typeId} was found
     *
     * @param type the {@link MetadataType} for which to retrieve its declared subTypes
     * @return a {@link List} with all the declared subtypes for the indicated {@link MetadataType}
     */
    Collection<ObjectType> getSubTypes(MetadataType type) {
      Collection<ObjectType> subTypes = getTypeId(type).map(subTypesById::get).orElse(subTypesMapping.get(type));
      return subTypes != null ? unmodifiableCollection(subTypes) : emptyList();
    }

    /**
     * Returns a {@link List} with all the declared {@link MetadataType} that are considered super types from the given
     * {@link MetadataType} {@code type}.
     * <p>
     * The lookup will be performed by looking recursively all the mappings that contains the given {@code type} as subtype and
     * storing the base type and again looking the super type of the found base type.
     *
     * @param type {@link MetadataType} to look for their super types
     * @return a {@link List} with all the declared supertypes for the indicated {@link MetadataType}
     */
    List<ObjectType> getSuperTypes(MetadataType type) {
      final List<ObjectType> types = new LinkedList<>();

      subTypesMapping.entrySet().stream()
          .filter(entry -> entry.getValue().contains(type))
          .forEach(entry -> {
            types.add(entry.getKey());
            types.addAll(getSuperTypes(entry.getKey()));
          });

      return unmodifiableList(types);
    }

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
    boolean containsBaseType(ObjectType type) {
      return getTypeId(type).map(subTypesById::get).orElse(subTypesMapping.get(type)) != null;
    }

    /**
     * @return a {@link List} with all the types which extend another type, in no particular order
     */
    List<ObjectType> getAllSubTypes() {
      return subTypesMapping.values().stream().flatMap(Collection::stream).collect(toList());
    }

    /**
     * @return a {@link List} with all the types which are extended by another type
     */
    Set<ObjectType> getAllBaseTypes() {
      return subTypesMapping.keySet();
    }

    private Map<ObjectType, Set<ObjectType>> toSubTypesMap(Collection<SubTypesModel> subTypes) {
      return subTypes.stream().collect(toMap(SubTypesModel::getBaseType, SubTypesModel::getSubTypes));
    }
  }

}
