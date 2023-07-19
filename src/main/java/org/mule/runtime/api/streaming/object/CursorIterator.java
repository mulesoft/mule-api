/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.streaming.object;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.streaming.Cursor;
import org.mule.runtime.api.streaming.HasSize;

import java.util.Iterator;

/**
 * An {@link Iterator} which subscribes to the {@link Cursor} contract
 *
 * @since 1.0
 */
@NoImplement
public interface CursorIterator<T> extends Iterator<T>, Cursor, HasSize {

}
