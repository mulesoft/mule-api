/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.config.custom;

import org.mule.api.annotation.NoImplement;

@NoImplement
public interface ServiceConfigurator {

  void configure(CustomizationService customizationService);
}
