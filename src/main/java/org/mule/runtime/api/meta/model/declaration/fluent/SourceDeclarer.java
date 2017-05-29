/*
/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

/**
 * Allows configuring a {@link SourceDeclaration} through a fluent API
 *
 * @since 1.0
 */
public class SourceDeclarer extends ComponentDeclarer<SourceDeclarer, SourceDeclaration> {

  /**
   * Creates a new instance
   *
   * @param declaration the {@link SourceDeclaration} to be configured
   */
  SourceDeclarer(SourceDeclaration declaration) {
    super(declaration);
  }

  /**
   * Allows to specify if the declared source emits responses
   *
   * @param hasResponse Whether the declared source emits a response
   * @return {@code this} declarer
   */
  public SourceDeclarer hasResponse(boolean hasResponse) {
    declaration.setHasResponse(hasResponse);
    return this;
  }

  /**
   * Allows to declare a callback which will listen to all the successful results of the dispatched
   * Source messages to the flow.
   *
   * @return a {@link SourceCallbackDeclarer}
   */
  public SourceCallbackDeclarer onSuccess() {
    SourceCallbackDeclaration callback = new SourceCallbackDeclaration("onSuccess");
    declaration.setSuccessCallback(callback);

    return new SourceCallbackDeclarer(callback);
  }

  /**
   * Allows to declare a callback which will listen for errors that occurred in the flow
   * processing a Source generated message.
   *
   * @return a {@link SourceCallbackDeclarer}
   */
  public SourceCallbackDeclarer onError() {
    SourceCallbackDeclaration callback = new SourceCallbackDeclaration("onError");
    declaration.setErrorCallback(callback);

    return new SourceCallbackDeclarer(callback);
  }

  /**
   * Allows to declare a callback which will listen to all the results of the dispatched
   * Source messages to the flow.
   * This callback will be called after the {@link #onSuccess()} ()} and {@link #onError()} callbacks.
   *
   * @return a {@link SourceCallbackDeclarer}
   */
  public SourceCallbackDeclarer onTerminate() {
    SourceCallbackDeclaration callback = new SourceCallbackDeclaration("onTerminate");
    declaration.setTerminateCallback(callback);

    return new SourceCallbackDeclarer(callback);
  }
}
