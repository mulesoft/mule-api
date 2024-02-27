/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.nested;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.ComponentModel;

/**
 * Represents a {@link NestableElementModel} that makes reference to a chain of {@link ComponentModel}s. This chain can have any
 * number of components of the allowed stereotypes.
 * <p>
 * For this type of nested element, both {@link #getMinOccurs()} and {@link #getMaxOccurs()} will never be greater than 1.
 *
 * @since 1.0
 */
@NoImplement
public interface NestedChainModel extends NestedComponentModel, HasChainExecutionOccurrence {

}
