/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.parameter;

import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableSet;
import org.mule.runtime.api.meta.DescribedObject;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.api.meta.NamedObject;
import org.mule.runtime.api.meta.Typed;
import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.meta.model.ElementDslModel;
import org.mule.runtime.api.meta.model.EnrichableModel;
import org.mule.runtime.api.meta.model.config.ConfigurationModel;
import org.mule.runtime.api.meta.model.display.HasDisplayModel;
import org.mule.runtime.api.meta.model.display.LayoutModel;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A parameter of a {@link ComponentModel Component} or Configuration
 * <p>
 * A parameter provides a name, a type and a default value.
 * </p>
 * It can apply either to a {@link ConfigurationModel} or a
 * {@link ComponentModel}
 *
 * @since 1.0
 */
public interface ParameterModel extends NamedObject, DescribedObject, EnrichableModel, Typed, HasDisplayModel {

  Set<String> RESERVED_NAMES = unmodifiableSet(new HashSet<>(singletonList("name")));

  /**
   * Whether or not this parameter is required. This method is exclusive with
   * {@link #getDefaultValue()} in the sense that a required parameter cannot have a default
   * value. At the same time, if the parameter has a default value, then it makes no sense
   * to consider it as required
   *
   * @return a boolean value saying if this parameter is required or not
   */
  boolean isRequired();

  /**
   * The level of support {@code this} parameter has for expressions
   *
   * @return a {@link ExpressionSupport}
   */
  ExpressionSupport getExpressionSupport();

  /**
   * The default value for this parameter. It might be an expression if
   * {@link #getExpressionSupport()} returns {@link ExpressionSupport#REQUIRED}
   * or {@link ExpressionSupport#SUPPORTED}.
   * <p>
   * This method is exclusive with {@link #isRequired()}. Check that method's comments for
   * more information on the semantics of this two methods.
   *
   * @return the default value
   */
  Object getDefaultValue();

  /**
   * @return A {@link ElementDslModel }which describes the language which
   * allows configuring this parameter
   */
  ElementDslModel getDslModel();

  /**
   * @return An {@link Optional} {@link LayoutModel} which contains directives
   * about how this parameter should be shown in the UI
   */
  Optional<LayoutModel> getLayoutModel();
}
