/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.meta.model.parameter.ParameterGroupModel.DEFAULT_GROUP_NAME;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@link NamedDeclaration} which adds a {@link List} of {@link ParameterDeclaration}
 *
 * @param <T> the concrete type for {@code this} declaration
 * @since 1.0
 */
public abstract class ParameterizedDeclaration<T extends ParameterizedDeclaration> extends NamedDeclaration<T>
    implements WithParametersDeclaration, WithSemanticTermsDeclaration {

  private final Map<String, ParameterGroupDeclaration> parameterGroups = new LinkedHashMap<>();
  private final Set<String> semanticTerms = new LinkedHashSet<>();

  /**
   * {@inheritDoc}
   */
  ParameterizedDeclaration(String name) {
    super(name);
  }

  /**
   * @return an unmodifiable {@link List} with the {@link ParameterGroupDeclaration declarations}
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

    return parameterGroups.computeIfAbsent(groupName, ParameterGroupDeclaration::new);
  }

  /**
   * @return the default {@link ParameterGroupDeclaration}, "General".
   *
   * @since 1.2
   */
  public ParameterGroupDeclaration getDefaultParameterGroup() {
    return parameterGroups.computeIfAbsent(DEFAULT_GROUP_NAME, ParameterGroupDeclaration::new);
  }

  /**
   * Returns all the parameter declarations declared on all groups.
   *
   * @return a flattened list of all the parameters in this declaration
   */
  public List<ParameterDeclaration> getAllParameters() {
    return parameterGroups.values().stream().flatMap(g -> g.getParameters().stream()).collect(toList());
  }

  /**
   * {@inheritDoc}
   *
   * @since 1.4.0
   */
  @Override
  public void addSemanticTerm(String semanticTerm) {
    checkArgument(!isBlank(semanticTerm), "Semantic term cannot be blank");
    semanticTerms.add(semanticTerm);
  }

  /**
   * {@inheritDoc}
   *
   * @since 1.4.0
   */
  @Override
  public Set<String> getSemanticTerms() {
    return semanticTerms;
  }
}
