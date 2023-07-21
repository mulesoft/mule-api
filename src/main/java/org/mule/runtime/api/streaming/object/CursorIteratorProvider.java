/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.streaming.object;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.streaming.CursorProvider;

/**
 * Specialization of {@link CursorProvider} which yields instances of {@link CursorIterator}
 *
 * @see CursorIterator
 * @see CursorProvider
 * @since 1.0
 */
@NoImplement
public interface CursorIteratorProvider extends CursorProvider<CursorIterator> {

}
