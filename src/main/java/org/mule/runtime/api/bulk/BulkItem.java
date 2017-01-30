/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.bulk;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents an individual data piece in the context of a bulk operation
 *
 * @since 1.0
 */
public final class BulkItem<T> implements Serializable {

  private static final long serialVersionUID = -2402370691833265593L;

  private final Serializable id;
  private final boolean successful;
  private final String message;
  private final String statusCode;
  private final Exception exception;
  private final T payload;
  private final Map<String, Serializable> customProperties;

  private BulkItem(Serializable id,
                   boolean successful,
                   String message,
                   String statusCode,
                   Exception exception,
                   T payload,
                   Map<String, Serializable> customProperties) {
    this.id = id;
    this.successful = successful;
    this.message = message;
    this.statusCode = statusCode;
    this.exception = exception;
    this.payload = payload;
    if (customProperties != null) {
      this.customProperties = new HashMap<>(customProperties);
    } else {
      this.customProperties = null;
    }
  }

  /**
   * The item id
   */
  public Serializable getId() {
    return id;
  }

  /**
   * Whether or not it was successful. Notice that this should be {@code false} if {@link #exception} is not
   * {@code null}, however there might not be an exception but the item could still not be successful for other reasons.
   */
  public boolean isSuccessful() {
    return successful;
  }

  /**
   * Message to add context on this item. Could be an error description, a warning or simply some info related to the operation
   */
  public String getMessage() {
    return message;
  }

  /**
   * An optional status code
   */
  public String getStatusCode() {
    return statusCode;
  }

  /**
   * An exception if the item was failed
   */
  public Exception getException() {
    return exception;
  }

  /**
   * The actual data this entity represents
   */
  public T getPayload() {
    return payload;
  }

  /**
   * A custom property stored under the given key
   *
   * @param key the key of the custom property
   * @return a {@link Serializable} value
   */
  public Serializable getCustomProperty(String key) {
    return this.customProperties != null ? this.customProperties.get(key) : null;
  }

  public static <T> BulkItemBuilder<T> builder() {
    return new BulkItemBuilder<>();
  }

  public static class BulkItemBuilder<T> extends AbstractBulkBuilder {

    private Serializable id;
    private boolean successful = true;
    private String message;
    private String statusCode;
    private Exception exception;
    private T payload;

    public BulkItemBuilder<T> setRecordId(Serializable recordId) {
      this.id = recordId;
      return this;
    }

    public BulkItemBuilder<T> setSuccessful(boolean successful) {
      this.successful = successful;
      return this;
    }

    public BulkItemBuilder<T> setMessage(String message) {
      this.message = message;
      return this;
    }

    public BulkItemBuilder<T> setStatusCode(String statusCode) {
      this.statusCode = statusCode;
      return this;
    }

    public BulkItemBuilder<T> setException(Exception exception) {
      this.exception = exception;
      this.successful = false;
      return this;
    }

    public BulkItemBuilder<T> setPayload(T payload) {
      this.payload = payload;
      return this;
    }

    public BulkItemBuilder<T> addCustomProperty(String key, Serializable value) {
      this.customProperty(key, value);
      return this;
    }

    protected BulkItem<T> build() {
      return new BulkItem<T>(id, successful, message, statusCode, exception, payload, this.getCustomProperties());
    }
  }

}
