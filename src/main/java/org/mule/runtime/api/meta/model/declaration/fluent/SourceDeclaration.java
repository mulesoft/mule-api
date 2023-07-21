/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static java.util.Optional.ofNullable;
import org.mule.runtime.api.meta.model.source.SourceModel;

import java.util.List;
import java.util.Optional;

/**
 * A declaration object for a {@link SourceModel}. It contains raw, unvalidated data which is used to declare the structure of a
 * {@link SourceModel}
 *
 * @since 1.0
 */
public class SourceDeclaration extends ExecutableComponentDeclaration<SourceDeclaration> {

  private boolean hasResponse = false;
  private boolean runsOnPrimaryNodeOnly = false;
  private SourceCallbackDeclaration successCallback = null;
  private SourceCallbackDeclaration errorCallback = null;
  private SourceCallbackDeclaration backPressureCallback = null;
  private SourceCallbackDeclaration terminateCallback = null;

  /**
   * {@inheritDoc}
   */
  SourceDeclaration(String name) {
    super(name);
  }

  /**
   * Returns all the parameter declarations declared on all groups, including the ones declared on the success and error callbacks
   *
   * @return a flattened list of all the parameters in this declaration
   */
  @Override
  public List<ParameterDeclaration> getAllParameters() {
    List<ParameterDeclaration> all = super.getAllParameters();
    if (successCallback != null) {
      all.addAll(successCallback.getAllParameters());
    }

    if (errorCallback != null) {
      all.addAll(errorCallback.getAllParameters());
    }

    return all;
  }

  /**
   * @return Whether the declared source emits a response
   */
  public boolean hasResponse() {
    return hasResponse;
  }

  /**
   * @param hasResponse Whether the declared source emits a response
   */
  public void setHasResponse(boolean hasResponse) {
    this.hasResponse = hasResponse;
  }

  /**
   * @return the success {@link SourceCallbackDeclaration} if provided.
   */
  public Optional<SourceCallbackDeclaration> getSuccessCallback() {
    return ofNullable(successCallback);
  }

  /**
   * Sets the success {@link SourceCallbackDeclaration}
   * 
   * @param successCallback a callback declaration or {@code null}
   */
  public void setSuccessCallback(SourceCallbackDeclaration successCallback) {
    this.successCallback = successCallback;
  }

  /**
   * @return the error {@link SourceCallbackDeclaration} if provided.
   */
  public Optional<SourceCallbackDeclaration> getErrorCallback() {
    return ofNullable(errorCallback);
  }

  /**
   * @return the back pressure {@link SourceCallbackDeclaration} if provided
   * @since 1.1
   */
  public Optional<SourceCallbackDeclaration> getBackPressureCallback() {
    return ofNullable(backPressureCallback);
  }

  /**
   * Sets the error {@link SourceCallbackDeclaration}
   * 
   * @param errorCallback a callback declaration or {@code null}
   */
  public void setErrorCallback(SourceCallbackDeclaration errorCallback) {
    this.errorCallback = errorCallback;
  }

  /**
   * @return the terminate {@link SourceCallbackDeclaration} if provided.
   */
  public Optional<SourceCallbackDeclaration> getTerminateCallback() {
    return ofNullable(terminateCallback);
  }

  /**
   * Sets the terminate {@link SourceCallbackDeclaration}
   * 
   * @param terminateCallback a callback declaration or {@code null}
   */
  public void setTerminateCallback(SourceCallbackDeclaration terminateCallback) {
    this.terminateCallback = terminateCallback;
  }

  public boolean isHasResponse() {
    return hasResponse;
  }

  /**
   * @return whether the declared source should only run in the primary node when in cluster mode
   */
  public boolean isRunsOnPrimaryNodeOnly() {
    return runsOnPrimaryNodeOnly;
  }

  /**
   * Sets whether the declared source should only run in the primary node when in cluster mode
   * 
   * @param runsOnPrimaryNodeOnly whether to only run on the primary node
   */
  public void setRunsOnPrimaryNodeOnly(boolean runsOnPrimaryNodeOnly) {
    this.runsOnPrimaryNodeOnly = runsOnPrimaryNodeOnly;
  }

  /**
   * Sets the back pressure {@link SourceCallbackDeclaration}
   * 
   * @param backPressureCallback a callback declaration or {@code null}
   * @since 1.1
   */
  public void setBackPressureCallback(SourceCallbackDeclaration backPressureCallback) {
    this.backPressureCallback = backPressureCallback;
  }
}
