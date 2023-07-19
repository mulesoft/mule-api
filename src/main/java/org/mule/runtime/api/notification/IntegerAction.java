/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

import static java.lang.String.valueOf;
import static org.mule.runtime.internal.dsl.DslConstants.CORE_PREFIX;
import org.mule.runtime.api.notification.Notification.Action;

/**
 * Adapter of Mule 3 notification actions, modeled as integers, to the new Mule 4 mechanism.
 * 
 * @since 4.0
 */
public final class IntegerAction implements Action {

  private static final String CORE_NAMESPACE = CORE_PREFIX.toUpperCase();

  private int actionId;

  public IntegerAction(int actionId) {
    this.actionId = actionId;
  }

  /**
   * @deprecated use {@link #getIdentifier()}
   */
  @Deprecated
  public int getActionId() {
    return actionId;
  }

  @Override
  public String getNamespace() {
    return CORE_NAMESPACE;
  }

  @Override
  public String getIdentifier() {
    return valueOf(actionId);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + actionId;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    IntegerAction other = (IntegerAction) obj;
    if (actionId != other.actionId)
      return false;
    return true;
  }

}
