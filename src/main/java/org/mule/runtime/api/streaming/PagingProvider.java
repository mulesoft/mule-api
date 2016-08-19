/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.streaming;

import java.io.Closeable;
import java.util.List;
import java.util.Optional;

/**
 * This interface provides functionality for consuming a data feed in pages.
 * <p>
 * Implementing this interface does not guarantee thread safeness.
 *
 * @param <C> connection type expected to handle the operations.
 * @param <T> the type of the returned pages.
 * @since 1.0
 */
public interface PagingProvider<C, T> extends Closeable {

  /**
   * Returns the next page of items. If the return value is an empty {@link List} then it means no more items are available
   *
   * @param connection The connection to be used to do the query.
   * @return a populated {@link List} of elements of type {@code Type}, An empty {@link List} if no more items are available
   */
  List<T> getPage(C connection);

  /**
   * returns the total amount of items in the non-paged result set. In some scenarios,
   * it might not be possible/convenient to actually retrieve this value, in such a cases an
   * {@link Optional#empty()} value is returned.
   *
   * @param connection The connection to be used to do the query.
   */
  Optional<Integer> getTotalResults(C connection);

}
