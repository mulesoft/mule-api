/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isAllBlank;
import static org.apache.commons.lang3.StringUtils.removeAll;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents the Namespace for a given module
 *
 * @since 1.0
 */
public final class ModuleNamespace {

  private String[] elements;

  public ModuleNamespace(String... elements) {
    checkArgument(elements != null && elements.length > 0,
                  "Invalid namespace, at least one namespace identifier is required");
    this.elements = normalize(elements);
  }

  /**
   * Returns the array of elements that forms the namespace name
   *
   * @return The array of elements
   */
  public String[] getElements() {
    return elements;
  }

  @Override
  public String toString() {
    return stream(elements)
        .reduce((identity, accumulator) -> identity + "::" + accumulator)
        .orElse("");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ModuleNamespace that = (ModuleNamespace) o;

    return Arrays.equals(elements, that.elements);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(elements);
  }

  private String[] normalize(String... parts) {
    List<String> normalized = new ArrayList<>(parts.length);
    for (int i = 0; i < parts.length; i++) {
      String part = parts[i];
      if (isAllBlank(part)) {
        continue;
      }

      part = cleanUpPart(part);
      if (i + 1 < parts.length) {
        normalized.add(asPackagePart(part));
      } else {
        // Last part is the module identifier
        normalized.add(asModulePart(part));
      }
    }

    return normalized.toArray(new String[] {});
  }

  private String asModulePart(String part) {
    return stream(part.split(SPACE))
        .filter(StringUtils::isNotBlank)
        .map(StringUtils::capitalize)
        .collect(joining());
  }

  private String asPackagePart(String part) {
    return removeAll(part, SPACE).toLowerCase();
  }

  private String cleanUpPart(String part) {
    return part.replaceAll("[^\\w]", SPACE)
        .replaceAll("[\\.\\-_\\ ]", SPACE);
  }

}
