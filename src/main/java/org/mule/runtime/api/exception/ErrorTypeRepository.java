/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.api.message.ErrorType;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@NoImplement
public interface ErrorTypeRepository {

  /**
   * Adds and returns an {@link ErrorType} for a given identifier with the given parent that will be fully visible, meaning it
   * will be available for use in on-error components.
   *
   * @param errorTypeIdentifier the {@link ComponentIdentifier} for the error
   * @param parentErrorType     the {@link ErrorType} that will act as parent
   * @return the created {@link ErrorType}
   */
  ErrorType addErrorType(ComponentIdentifier errorTypeIdentifier, ErrorType parentErrorType);

  /**
   * Adds and returns an {@link ErrorType} for a given identifier with the given parent that will be only used internally, meaning
   * it won't be available for use in on-error components.
   *
   * @param errorTypeIdentifier the {@link ComponentIdentifier} for the error
   * @param parentErrorType     the {@link ErrorType} that will act as parent
   * @return the created {@link ErrorType}
   */
  ErrorType addInternalErrorType(ComponentIdentifier errorTypeIdentifier, ErrorType parentErrorType);

  /**
   * Looks up the specified error's type and returns it if found and available for general use (error handling).
   *
   * @param errorTypeComponentIdentifier the {@link ComponentIdentifier} for the error
   * @return an {@link Optional} with the corresponding {@link ErrorType} or an empty one
   */
  Optional<ErrorType> lookupErrorType(ComponentIdentifier errorTypeComponentIdentifier);

  /**
   * Returns the specified error's type if present. Unlike {@link #lookupErrorType(ComponentIdentifier)}, this will return the
   * {@link ErrorType} even if it's not available for general use (error handling).
   *
   * @param errorTypeIdentifier the {@link ComponentIdentifier} for the error
   * @return an {@link Optional} with the corresponding {@link ErrorType} or an empty one
   */
  Optional<ErrorType> getErrorType(ComponentIdentifier errorTypeIdentifier);

  /**
   * Returns the collection of current error namespaces.
   *
   * @return a collection of all present namespaces
   */
  Collection<String> getErrorNamespaces();

  /**
   * Gets the {@link ErrorType} instance for ANY error type.
   *
   * @return the ANY error type
   */
  ErrorType getAnyErrorType();

  /**
   * Gets the {@link ErrorType} instance for SOURCE error type.
   *
   * @return the SOURCE error type
   */
  ErrorType getSourceErrorType();

  /**
   * Gets the {@link ErrorType} instance for SOURCE_RESPONSE error type.
   *
   * @return the SOURCE_RESPONSE error type
   */
  ErrorType getSourceResponseErrorType();

  /**
   * Gets the {@link ErrorType} instance for CRITICAL error type.
   *
   * @return the CRITICAL error type
   */
  ErrorType getCriticalErrorType();

  /**
   * Gets the {@link ErrorType} instances for handleable errors available in this repository.
   *
   * @return all the available error types.
   * @since 1.4
   */
  Set<ErrorType> getErrorTypes();

  /**
   * Gets the {@link ErrorType} instances for un-handleable errors available in this repository.
   *
   * @return all the available error types.
   * @since 1.4
   */
  Set<ErrorType> getInternalErrorTypes();
}
