/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.config.custom;

import org.mule.api.annotation.NoImplement;

/**
 * 
 * @deprecated Since 1.5, use org.mule.runtime.api.config.custom.ServiceConfigurator instead.
 */
@Deprecated
@NoImplement
public interface ServiceConfigurator {

  void configure(CustomizationService customizationService);
}
