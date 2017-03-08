/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.operation;

import org.mule.runtime.api.meta.model.Stereotype;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;

import java.util.Optional;
import java.util.Set;

/**
 * A Route is a chain of components which don't exist directly on a Mule flow,
 * but are contained inside another owning component.
 * <p>
 * For example:
 * <p>
 * <ul>
 * <li>The processors inside the {@code &lt;foreach&gt;} component are part of a route</li>
 * <li>The processors inside the {@code &lt;when&gt;} block inside a choice router.</li>
 * </ul>
 * <p>
 * Each of the above constitute a {@code Route}. Several instances of those routes can exists
 * inside the same component instance. For example, the {@code when} route can be used many times
 * inside a choice router. On the other hand, the {@code otherwise} route inside the same router can
 * only exist up to one time, while it is also allowed to not exist at all. This {@link #getMinOccurs()}
 * and {@link #getMaxOccurs()} methods model that.
 * <p>
 * Finally, some scope have restrictions regarding which components can be used with them. For example,
 * {@code validation:all} only allows other validators to be placed inside. That's what the {@link #getAllowedStereotypes()}
 * method represents.
 *
 * @since 1.0
 */
public interface RouteModel extends ParameterizedModel {

  /**
   * Represents the minimum amount of times that this route can be used inside the owning component.
   *
   * @return An int greater or equal to zero
   */
  int getMinOccurs();

  /**
   * {@link Optional} value which represents the maximum amount of times that this route can be used inside the owning
   * component.
   *
   * @return If present, a number greater or equal to zero.
   */
  Optional<Integer> getMaxOccurs();

  /**
   * {@link Optional} {@link Set} of {@link Stereotype stereotypes} to which the components placed inside the route
   * <b>MUST</b> conform to.
   * <p>
   * If the value is empty, it means that no restriction applies.
   *
   * @return If present, a non empty set
   */
  Optional<Set<Stereotype>> getAllowedStereotypes();

}
