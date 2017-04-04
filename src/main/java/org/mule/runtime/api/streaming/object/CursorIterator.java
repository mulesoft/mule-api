/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.streaming.object;

import org.mule.runtime.api.streaming.Cursor;
import org.mule.runtime.api.streaming.Sized;

import java.util.Iterator;

/**
 * An {@link Iterator} which subscribes to the {@link Cursor} contract
 *
 * @since 1.0
 */
public interface CursorIterator<T> extends Iterator<T>, Cursor, Sized {

}
