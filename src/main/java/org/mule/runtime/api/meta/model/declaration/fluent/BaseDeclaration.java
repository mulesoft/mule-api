/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static java.util.Collections.unmodifiableSet;
import org.mule.api.annotation.NoExtend;
import org.mule.runtime.api.meta.DescribedObject;
import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.meta.model.display.DisplayModel;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Base class for a declaration object.
 * <p>
 * By default, {@link #getDescription()} returns an empty {@link String}.
 *
 * @param <T> the concrete type for {@code this} declaration
 * @since 1.0
 */
@NoExtend
public abstract class BaseDeclaration<T extends BaseDeclaration> implements DescribedObject {

  private final Map<Class<? extends ModelProperty>, ModelProperty> modelProperties = new LinkedHashMap<>();
  private String description = "";
  private DisplayModel displayModel;

  /**
   * Returns a {@link Set} with the currently added properties. Notice that this {@link Set} is mutable and not thread-safe.
   * <p>
   * This method is to be used when you need to access all the properties. For individual access use
   * {@link #addModelProperty(ModelProperty)}} or {@link #getModelProperty(Class)} instead
   *
   * @return a {@link Set} with the current model properties. Might be empty but will never by {@code null}
   */
  public Set<ModelProperty> getModelProperties() {
    return unmodifiableSet(new LinkedHashSet<>(modelProperties.values()));
  }

  /**
   * Returns the model property registered under {@code key}
   *
   * @param propertyType the property's {@link Class}
   * @param <P>          the generic type for the response value
   * @return the associated value wrapped on an {@link Optional}
   */
  public <P extends ModelProperty> Optional<P> getModelProperty(Class<P> propertyType) {
    return Optional.ofNullable((P) modelProperties.get(propertyType));
  }

  /**
   * Adds the given {@param modelProperty}. If a property of the same {@link Class} has already been added, it will be
   * overwritten.
   *
   * @param modelProperty a {@link ModelProperty}
   * @throws IllegalArgumentException if {@code modelProperty} is {@code null{}}
   */
  public T addModelProperty(ModelProperty modelProperty) {
    if (modelProperty == null) {
      throw new IllegalArgumentException("Cannot add a null model property");
    }

    // TODO: MULE-9581 take a look at MetadataKeyBuilder

    modelProperties.put(modelProperty.getClass(), modelProperty);
    return (T) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Sets the {@link #description} for this declaration
   *
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return The {@link DisplayModel} for this declaration
   */
  public DisplayModel getDisplayModel() {
    return displayModel;
  }

  /**
   * Sets the {@link #displayModel} for this declaration
   * 
   * @param displayModel a {@link DisplayModel}
   */
  public void setDisplayModel(DisplayModel displayModel) {
    this.displayModel = displayModel;
  }
}
