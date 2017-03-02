/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.Stereotype;
import org.mule.runtime.api.meta.model.operation.RouteModel;

import java.util.HashSet;
import java.util.Set;

/**
 * A declaration object for a {@link RouteModel}. It contains raw, unvalidated
 * data which is used to declare the structure of a {@link RouteModel}
 *
 * @since 1.0
 */
public class RouteDeclaration extends ParameterizedDeclaration<RouteDeclaration> {

  private int minOccurs;
  private Integer maxOccurs;
  private Set<Stereotype> allowedStereotypes = new HashSet<>();

  /**
   * {@inheritDoc}
   */
  public RouteDeclaration(String name) {
    super(name);
  }

  public int getMinOccurs() {
    return minOccurs;
  }

  public void setMinOccurs(int minOccurs) {
    this.minOccurs = minOccurs;
  }

  public Integer getMaxOccurs() {
    return maxOccurs;
  }

  public void setMaxOccurs(Integer maxOccurs) {
    this.maxOccurs = maxOccurs;
  }

  public Set<Stereotype> getAllowedStereotypes() {
    return allowedStereotypes;
  }

  public RouteDeclaration addAllowedStereotype(Stereotype stereotype) {
    allowedStereotypes.add(stereotype);
    return this;
  }
}
