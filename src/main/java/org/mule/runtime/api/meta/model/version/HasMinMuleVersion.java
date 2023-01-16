/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.version;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.MuleVersion;

import java.util.Optional;

/**
 * ADD JDOC
 *
 * @since 1.6
 */
@NoImplement
public interface HasMinMuleVersion {

  /**
   * ADD JDOC
   *
   * @return
   */
  Optional<MuleVersion> getMinMuleVersion();

}
