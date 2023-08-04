/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.notification.NotificationModel;

/**
 * Base class for {@link Declarer declarers} which allow to construct an {@link OperationDeclaration}
 *
 * @param <T> the generic type of the concrete declarer
 * @param <D> the generic type of the produced {@link OperationDeclaration}
 * @since 1.0
 */
public abstract class ExecutableComponentDeclarer<T extends ExecutableComponentDeclarer, D extends ExecutableComponentDeclaration>
    extends ComponentDeclarer<T, D> {

  /**
   * {@inheritDoc}
   */
  ExecutableComponentDeclarer(D declaration) {
    super(declaration);
  }

  /**
   * Declares element output
   *
   * @return a new {@link OutputDeclarer}
   */
  public OutputDeclarer withOutput() {
    OutputDeclaration outputPayload = new OutputDeclaration();
    declaration.setOutput(outputPayload);
    return new OutputDeclarer<>(outputPayload);
  }

  /**
   * Declares element output
   *
   * @return a new {@link OutputDeclarer}
   */
  public OutputDeclarer withOutputAttributes() {
    OutputDeclaration outputAttributes = new OutputDeclaration();
    declaration.setOutputAttributes(outputAttributes);
    return new OutputDeclarer<>(outputAttributes);
  }

  /**
   * Specifies if this component has the ability to execute while joining a transaction
   *
   * @param transactional whether the component is transactional or not
   * @return {@code this} declarer
   */
  public T transactional(boolean transactional) {
    declaration.setTransactional(transactional);
    return (T) this;
  }

  /**
   * Specifies if this component requires a connection in order to perform its task
   *
   * @param requiresConnection whether the component requires a connection or not
   * @return {@code this} declarer
   */
  public T requiresConnection(boolean requiresConnection) {
    declaration.setRequiresConnection(requiresConnection);
    return (T) this;
  }

  /**
   * Specifies if this component supports streaming. Notice that supporting streaming doesn't necessarily mean that streaming will
   * be performed when the component is actually executed.
   *
   * @param supportsStreaming whether the component supports streaming or not
   * @return {@code this} declarer
   */
  public T supportsStreaming(boolean supportsStreaming) {
    declaration.setSupportsStreaming(supportsStreaming);
    return (T) this;
  }

  /**
   * Adds a {@link NotificationModel} to indicate that the current component fires the added notification.
   *
   * @param notification {@link NotificationModel} to add to the {@link OperationDeclaration}
   * @return {@code this} declarer
   * @since 1.1
   */
  public T withNotificationModel(NotificationModel notification) {
    declaration.addNotificationModel(notification);
    return (T) this;
  }

}
