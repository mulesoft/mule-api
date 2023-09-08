/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoExtend;
import org.mule.runtime.api.el.BindingContext;

/**
 * Representation of a function's parameter, including it's name, type and optionally, a default value.
 *
 * @since 1.0
 */
@NoExtend
public class FunctionParameter {

  private String name;
  private DataType type;
  private DefaultValueResolver defaultValueResolver;

  public FunctionParameter(String name, DataType type,
                           DefaultValueResolver defaultValueResolver) {
    this(name, type);
    this.defaultValueResolver = defaultValueResolver;
  }

  public FunctionParameter(String name, DataType type) {
    this.name = name;
    this.type = type;
  }

  /**
   * @return the name of this parameter
   */
  public String getName() {
    return name;
  }

  /**
   * @return the {@link DataType} of the parameter
   */
  public DataType getType() {
    return type;
  }

  /**
   * @return the {@link DefaultValueResolver} for this parameter or {@code null} if it doesn't have a default value
   */
  public DefaultValueResolver getDefaultValueResolver() {
    return defaultValueResolver;
  }

  /**
   * Resolves a {@link FunctionParameter} default value.
   */
  public interface DefaultValueResolver {

    /**
     * Returns the default value for a {@link FunctionParameter} given the current {@link BindingContext}, allowing to reference
     * variables as defaults.
     *
     * @param context the current {@link BindingContext} to consider
     * @return the default value
     */
    Object getDefaultValue(BindingContext context);

  }

}
