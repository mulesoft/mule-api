/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

/**
 * Contract interface for a {@link BaseDeclaration} in which
 * it's possible to add/get {@link StereotypeModel} objects
 *
 * @since 1.0
 */
public interface WithStereotypesDeclaration {

  StereotypeModel getStereotype();

  void withStereotype(StereotypeModel stereotype);

}
