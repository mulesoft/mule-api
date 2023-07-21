/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.stereotype;

import org.mule.api.annotation.NoImplement;

import java.util.Optional;

/**
 * A model which represents an stereotype qualifier for the {@link HasStereotypeModel} that is categorized with this stereotype.
 * An stereotype is a widely held but fixed and oversimplified image or idea of the owning model. Examples would be
 * {@code validator}, {@code source}, {@code processor}, etc.
 *
 * @since 1.0
 */
@NoImplement
public interface StereotypeModel {

  /**
   * Gets the name of the stereotype.
   *
   * @return The type of the error
   */
  String getType();

  /**
   * Gets the namespace of error. This namespace represent the origin or who declares this error, so it could be the namespace of
   * an extension or the {@code MULE} namespace.
   *
   * @return The namespace of the error
   */
  String getNamespace();

  /**
   * @return The {@link StereotypeModel} parent of the current {@link StereotypeModel} from which it inherits from.
   */
  Optional<StereotypeModel> getParent();

  /**
   * @return {@code true} if {@code this} stereotype or any of its ancestors is equal to the provided {@code other}
   *         {@link StereotypeModel}, {@code false} otherwise.
   */
  boolean isAssignableTo(StereotypeModel other);
}
