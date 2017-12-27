/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.notification.NotificationModel;
import org.mule.runtime.api.meta.model.operation.OperationModel;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A declaration object for a {@link OperationModel}. It contains raw, unvalidated
 * data which is used to declare the structure of a {@link OperationModel}
 *
 * @since 1.0
 */
public abstract class ExecutableComponentDeclaration<T extends ExecutableComponentDeclaration>
    extends ComponentDeclaration<T> implements WithOutputDeclaration {

  private boolean transactional = false;
  private boolean requiresConnection = false;
  private boolean supportsStreaming = false;
  private OutputDeclaration outputContent;
  private OutputDeclaration outputAttributes;
  private Set<NotificationModel> notificationModels = new LinkedHashSet<>();

  /**
   * {@inheritDoc}
   */
  ExecutableComponentDeclaration(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OutputDeclaration getOutput() {
    return outputContent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOutput(OutputDeclaration content) {
    this.outputContent = content;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OutputDeclaration getOutputAttributes() {
    return outputAttributes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOutputAttributes(OutputDeclaration outputAttributes) {
    this.outputAttributes = outputAttributes;
  }

  public boolean isTransactional() {
    return transactional;
  }

  public void setTransactional(boolean transactional) {
    this.transactional = transactional;
  }

  public boolean isRequiresConnection() {
    return requiresConnection;
  }

  public void setRequiresConnection(boolean requiresConnection) {
    this.requiresConnection = requiresConnection;
  }

  public boolean isSupportsStreaming() {
    return supportsStreaming;
  }

  public void setSupportsStreaming(boolean supportsStreaming) {
    this.supportsStreaming = supportsStreaming;
  }

  public void addNotificationModel(NotificationModel notificationModel) {
    notificationModels.add(notificationModel);
  }

  public Set<NotificationModel> getNotificationModels() {
    return notificationModels;
  }

}
