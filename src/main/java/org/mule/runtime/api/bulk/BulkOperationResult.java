/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.bulk;

import org.mule.runtime.api.bulk.BulkItem.BulkItemBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to provide item level information about a bulk operation. This master entity represents the bulk operation
 * as a whole, while the detail entity {@link BulkItem} represents the operation status for each individual data piece. The
 * {@link #items} list defines a contract in which the ordering of those items needs to match the ordering of the original
 * objects. For example, if the bulk operation consisted of 10 person objects in which number X corresponded to the person 'John
 * Doe', then the Xth item in the {@link #items} list must reference to the result of procesing the same 'John Doe'
 *
 * @since 1.0
 */
public final class BulkOperationResult<T> implements Serializable {

  private static final long serialVersionUID = 8039267004891928585L;

  private final Serializable id;
  private final boolean successful;
  private final List<BulkItem<T>> items;
  private final Map<String, Serializable> customProperties;

  private BulkOperationResult(Serializable id,
                              boolean successful,
                              List<BulkItem<T>> items,
                              Map<String, Serializable> customProperties) {
    this.id = id;
    this.successful = successful;
    this.items = items;
    if (customProperties != null) {
      this.customProperties = new HashMap<>(customProperties);
    } else {
      this.customProperties = null;
    }
  }

  /**
   * @return The operation id
   */
  public Serializable getId() {
    return id;
  }

  /**
   * @return Whether or not the operation was successful. Should be {@code true} if and only if all the child {@link BulkItem}
   * entities were also successful
   */
  public boolean isSuccessful() {
    return successful;
  }

  /**
   * @return An ordered list of {@link BulkItem}, one per each item in the original operation, no matter if the record was successful or
   * not
   */
  public List<BulkItem<T>> getItems() {
    return items;
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

  public static <T> BulkOperationResultBuilder<T> builder() {
    return new BulkOperationResultBuilder<T>();
  }

  public static class BulkOperationResultBuilder<T> extends AbstractBulkBuilder {

    private Serializable id;
    private boolean successful = true;
    private List<BulkItemBuilder<T>> items = new ArrayList<>();

    private BulkOperationResultBuilder() {}

    public BulkOperationResultBuilder<T> setSuccessful(boolean successful) {
      this.successful = successful;
      return this;
    }

    public BulkOperationResultBuilder<T> setId(Serializable id) {
      this.id = id;
      return this;
    }

    public BulkOperationResultBuilder<T> addItem(BulkItemBuilder<T> recordResultBuilder) {
      this.items.add(recordResultBuilder);
      return this;
    }

    public BulkOperationResultBuilder<T> addCustomProperty(String key, Serializable value) {
      this.customProperty(key, value);
      return this;
    }

    public BulkOperationResult<T> build() {
      if (this.items.isEmpty()) {
        throw new IllegalStateException("A BulkOperationResult must have at least one BulkItem. Please add a result an try again");
      }

      List<BulkItem<T>> items = new ArrayList<>(this.items.size());
      for (BulkItemBuilder<T> recordBuilder : this.items) {
        BulkItem<T> record = recordBuilder.build();
        items.add(record);
        if (!record.isSuccessful()) {
          this.successful = false;
        }
      }

      return new BulkOperationResult<T>(this.id, this.successful, items, this.getCustomProperties());
    }
  }
}
