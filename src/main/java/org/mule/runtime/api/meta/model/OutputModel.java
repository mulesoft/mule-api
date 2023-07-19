/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
