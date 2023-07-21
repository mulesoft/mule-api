/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.lifecycle;

/**
 * {@code DefaultLifecyclePhase} adds lifecycle methods {@code start}, {@code stop} and {@code dispose}.
 *
 * @since 1.0
 */
public interface Lifecycle extends Initialisable, Startable, Stoppable, Disposable {
  // empty
}
