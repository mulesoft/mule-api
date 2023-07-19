/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.store;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.Optional;

/**
 * Immutable object which contains settings parameters regarding how should a particular {@link ObjectStore} instance behave.
 *
 * @since 1.0
 */
public class ObjectStoreSettings {

  public static final long DEFAULT_EXPIRATION_INTERVAL = MINUTES.toMillis(1);


  /**
   * A Builder for creating {@link ObjectStoreSettings}
   *
   * @since 1.0
   */
  public static class Builder {

    private final ObjectStoreSettings product = new ObjectStoreSettings();

    private Builder() {}

    /**
     * Sets whether the store should be persistent or transient.
     * <p>
     * If not invoked, the described {@link ObjectStore} will be persistent by default.
     *
     * @param persistent {@code true} if the store should be persistent
     * @return {@code this} builder
     */
    public Builder persistent(boolean persistent) {
      product.persistent = persistent;
      return this;
    }

    /**
     * Sets the max number of entries allowed. Exceeding entries will be removed when expiration thread runs
     *
     * @param maxEntries the number of entries allowed
     * @return {@code this} builder
     */
    public Builder maxEntries(Integer maxEntries) {
      product.maxEntries = maxEntries;
      return this;
    }

    /**
     * Sets the entry timeout in milliseconds.
     *
     * @param entryTtl timeout in milliseconds
     * @return {@code this} builder
     */
    public Builder entryTtl(Long entryTtl) {
      product.entryTTL = entryTtl;
      return this;
    }

    /**
     * Sets how frequently should the expiration thread run.
     * <p>
     * If not set, it will default to {@link #DEFAULT_EXPIRATION_INTERVAL}, but will only be used by the runtime if
     * {@link #entryTtl(Long)} or {@link #maxEntries(Integer)} was also invoked.
     * <p>
     * Setting this to a value lower or equal than zero is also equivalent to disabling expiration.
     *
     * @param expirationInterval interval in milliseconds. Greater than zero
     * @return {@code this} builder
     */
    public Builder expirationInterval(Long expirationInterval) {
      product.expirationInterval = expirationInterval;
      return this;
    }

    /**
     * Returns the built settings object. {@code this} instance should be discarded and no longer used after invoking this method
     *
     * @return the built {@link ObjectStoreSettings} object
     */
    public ObjectStoreSettings build() {
      return product;
    }
  }

  /**
   * @return a new {@link Builder}
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Creates a new {@link ObjectStoreSettings} instance which describes a transient store with no expiration or boundaries.
   *
   * @return a new {@link ObjectStoreSettings}
   */
  public static ObjectStoreSettings unmanagedTransient() {
    return builder().persistent(false).build();
  }

  /**
   * Creates a new {@link ObjectStoreSettings} instance which describes a persistent store with no expiration or boundaries.
   *
   * @return a new {@link ObjectStoreSettings}
   */
  public static ObjectStoreSettings unmanagedPersistent() {
    return builder().persistent(true).build();
  }

  private boolean persistent = true;
  private Integer maxEntries = null;
  private Long entryTTL;
  private long expirationInterval = DEFAULT_EXPIRATION_INTERVAL;

  private ObjectStoreSettings() {}

  /**
   * Returns Whether the store is persistent or transient. If not set through {@link Builder#persistent(boolean)}, then it will
   * default to {@code true}
   */
  public boolean isPersistent() {
    return persistent;
  }

  /**
   * Returns the max number of entries allowed. Exceeding entries will be removed when expiration thread runs. If absent, then the
   * described {@link ObjectStore} will have no size boundaries.
   */
  public Optional<Integer> getMaxEntries() {
    return ofNullable(maxEntries);
  }

  /**
   * The entry timeout in milliseconds. If absent, then the described {@link ObjectStore} will have no time boundaries.
   */
  public Optional<Long> getEntryTTL() {
    return ofNullable(entryTTL);
  }

  /**
   * How frequently (in milliseconds) should the expiration thread run. If not set through
   * {@link Builder#expirationInterval(Long)}, it will default to {@link #DEFAULT_EXPIRATION_INTERVAL}
   */
  public long getExpirationInterval() {
    return expirationInterval;
  }
}
