/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.memory.provider.type;

import org.mule.api.annotation.Experimental;
import org.mule.runtime.api.memory.provider.ByteBufferProvider;


/**
 * Represents the pooling strategy that a {@link ByteBufferProvider} will use.
 *
 * @since 4.5.0
 */
@Experimental
public enum ByteBufferPoolStrategy {

  /**
   * Default thread pool based byte buffer provider. The pool contains buffers of an incremental size and allocates chunks of
   * them.
   */
  CHUNKED_BUFFERS_POOL,

  /**
   * Pool strategy based on the DataWeave one. The pool saves released DirectByteBuffers of a fixed size to be reused.
   */
  FIXED_BUFFERS_POOL
}
