/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import org.mule.runtime.api.meta.DescribedObject;
import org.mule.runtime.api.meta.Typed;

/**
 * Represents the output of a {@link ComponentModel Component}
 *
 * @since 1.0
 */
public interface OutputModel extends DescribedObject, EnrichableModel, Typed {

}
