/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link NamedDeclaration} which adds
 * a {@link List} of {@link ParameterDeclaration}
 *
 * @param <T> the concrete type for {@code this} declaration
 * @since 1.0
 */
public abstract class ParameterizedDeclaration<T extends ParameterizedDeclaration> extends NamedDeclaration<T> {

  private final Map<String, ParameterGroupDeclaration> parameterGroups = new HashMap<>();

  /**
   * {@inheritDoc}
   */
  ParameterizedDeclaration(String name) {
    super(name);
  }

  /**
   * @return an unmodifiable {@link List} with the {@link ParameterGroupDeclaration declarations}
   * registered through {@link #addParameterGroup(ParameterGroupDeclaration)}
   */
  public List<ParameterGroupDeclaration> getParameterGroups() {
    return unmodifiableList(new ArrayList<>(parameterGroups.values()));
  }

  /**
   * @param groupName the name of the group which declaration you seek
   * @return the {@link ParameterGroupDeclaration} of the given {@code groupName}.
   */
  public ParameterGroupDeclaration getParameterGroup(String groupName) {
    checkArgument(!isBlank(groupName), "groupName cannot be blank");

    return parameterGroups.computeIfAbsent(groupName, k -> {
      ParameterGroupDeclaration declaration = new ParameterGroupDeclaration(groupName);
      addParameterGroup(declaration);

      return declaration;
    });
  }

  /**
   * Adds a {@link ParameterDeclaration}
   *
   * @param parameterGroup a not {@code null} {@link ParameterDeclaration}
   * @return this declaration
   * @throws {@link IllegalArgumentException} if {@code parameter} is {@code null}
   */
  public T addParameterGroup(ParameterGroupDeclaration parameterGroup) {
    if (parameterGroup == null) {
      throw new IllegalArgumentException("Can't add a null parameter group");
    }

    parameterGroups.put(parameterGroup.getName(), parameterGroup);
    return (T) this;
  }

  /**
   * Returns all the parameter declarations declared on all groups.
   *
   * @return a flattened list of all the parameters in this declaration
   */
  public List<ParameterDeclaration> getAllParameters() {
    return parameterGroups.values().stream().flatMap(g -> g.getParameters().stream()).collect(toList());
  }
}
