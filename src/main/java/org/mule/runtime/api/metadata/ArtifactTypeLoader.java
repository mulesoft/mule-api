/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;
import org.mule.metadata.api.TypeLoader;

/**
 * A {@link TypeLoader} that can load all the types of an artifact.
 *
 * @since 1.5
 */
@NoImplement
public interface ArtifactTypeLoader extends TypeLoader {
}
