/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.streaming.bytes;

import org.mule.api.annotation.NoExtend;
import org.mule.runtime.api.streaming.Cursor;

import java.io.InputStream;

/**
 * An {@link InputStream} which subscribes to the {@link Cursor} contract
 *
 * @since 1.0
 */
@NoExtend
public abstract class CursorStream extends InputStream implements Cursor {

}
