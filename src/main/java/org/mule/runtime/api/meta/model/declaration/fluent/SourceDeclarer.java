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
   * Allows to declare a callback which will listen for the value produced
   * by the source owner when each generated message is processed correctly
   *
   * @return a {@link SourceCallbackDeclarer}
   */
  public SourceCallbackDeclarer onSuccess() {
    SourceCallbackDeclaration callback = new SourceCallbackDeclaration("onSuccess");
    declaration.setSuccessCallback(callback);

    return new SourceCallbackDeclarer(callback);
  }

  /**
   * Allows to declare a callback which will listen for errors thrown by
   * by the source owner when it fails to process any of the generated messages
   *
   * @return a {@link SourceCallbackDeclarer}
   */
  public SourceCallbackDeclarer onError() {
    SourceCallbackDeclaration callback = new SourceCallbackDeclaration("onError");
    declaration.setErrorCallback(callback);

    return new SourceCallbackDeclarer(callback);
  }
}
