/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.metadata.resolving.InputTypeResolver;
import org.mule.runtime.api.metadata.resolving.OutputTypeResolver;
import org.mule.runtime.api.metadata.resolving.TypeKeysResolver;
import java.io.Serializable;

/**
 * Like {@link MetadataCache}, this component provides the capability to store data so future requests for that data can be served
 * faster and accessed from every {@link InputTypeResolver}, {@link TypeKeysResolver} and {@link OutputTypeResolver}. It also
 * allows to evict entries in case recalculations are needed.
 * 
 * @since 1.5
 */
@NoImplement
public interface MetadataStorage extends MetadataCache {

  /**
   * Evicts the key in the storage.
   * 
   * @return true if the key was in the storage, false if not.
   */
  boolean evictEntry(Serializable key);
}
