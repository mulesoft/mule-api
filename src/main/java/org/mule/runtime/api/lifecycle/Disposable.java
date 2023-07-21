/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.lifecycle;

/**
 * {@code Disposable} is a lifecycle interface that gets called at the dispose lifecycle stage of the implementing service as the
 * service is being destroyed.
 *
 * @since 1.0
 */
public interface Disposable {

  String PHASE_NAME = "dispose";

  /**
   * A lifecycle method where implementor should free up any resources. If an exception is thrown it should just be logged and
   * processing should continue. This method should not throw Runtime exceptions.
   */
  void dispose();
}
