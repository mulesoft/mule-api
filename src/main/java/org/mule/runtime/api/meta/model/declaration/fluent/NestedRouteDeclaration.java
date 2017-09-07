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

import org.mule.runtime.api.meta.model.nested.NestedRouteModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A declaration object for a {@link NestedRouteModel}. It contains raw, unvalidated
 * data which is used to declare the structure of a {@link NestedRouteModel}
 *
 * @since 1.0
 */
public class NestedRouteDeclaration extends NestableElementDeclaration<NestedRouteDeclaration>
    implements WithParametersDeclaration, WithNestedComponentsDeclaration<NestedRouteDeclaration> {

  private int minOccurs = 0;
  private Integer maxOccurs;
  private final List<NestableElementDeclaration> nestedComponents = new LinkedList<>();
  private final Map<String, ParameterGroupDeclaration> parameterGroups = new LinkedHashMap<>();

  /**
   * Creates a new instance
   *
   * @param name the name of the component being declared
   */
  NestedRouteDeclaration(String name) {
    super(name);
  }

  public int getMinOccurs() {
    return minOccurs;
  }

  public void setMinOccurs(int minOccurs) {
    this.minOccurs = minOccurs;
    super.setRequired(minOccurs > 0);
  }

  public Integer getMaxOccurs() {
    return maxOccurs;
  }

  public void setMaxOccurs(Integer maxOccurs) {
    this.maxOccurs = maxOccurs;
  }

  @Override
  public void setRequired(boolean required) {
    super.setRequired(required);
    if (!required) {
      minOccurs = 0;
    }
  }

  @Override
  public boolean isRequired() {
    return minOccurs > 0;
  }

  public List<ParameterGroupDeclaration> getParameterGroups() {
    return unmodifiableList(new ArrayList<>(parameterGroups.values()));
  }

  public ParameterGroupDeclaration getParameterGroup(String groupName) {
    checkArgument(!isBlank(groupName), "groupName cannot be blank");

    return parameterGroups.computeIfAbsent(groupName, ParameterGroupDeclaration::new);
  }

  public List<ParameterDeclaration> getAllParameters() {
    return parameterGroups.values().stream().flatMap(g -> g.getParameters().stream()).collect(toList());
  }

  public List<NestableElementDeclaration> getNestedComponents() {
    return nestedComponents;
  }

  public NestedRouteDeclaration addNestedComponent(NestableElementDeclaration nestedComponentDeclaration) {
    nestedComponents.add(nestedComponentDeclaration);
    return this;
  }
}
