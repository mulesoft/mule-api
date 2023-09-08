/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.DescribedObject;
import org.mule.runtime.api.meta.Typed;

/**
 * Represents the output of a {@link ComponentModel Component}
 *
 * @since 1.0
 */
@NoImplement
public interface OutputModel extends DescribedObject, EnrichableModel, Typed {

}
