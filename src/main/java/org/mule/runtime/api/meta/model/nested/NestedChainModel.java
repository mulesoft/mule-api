/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
public interface NestedChainModel extends NestedComponentModel {

}
