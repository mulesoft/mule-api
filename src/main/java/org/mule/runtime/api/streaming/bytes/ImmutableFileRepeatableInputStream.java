/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.streaming.bytes;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * An {@link InputStream} implementation that allows obtaining a repeatable stream from the contents of an immutable file that
 * already exists in the local file system.
 * <p>
 * The Mule Runtime is capable of generating larger than memory repeatable streams by buffering into a temporal file in the local
 * file system. The problem with that approach is that when the contents of the original stream are already present in the file
 * system, the temporary file is still created, consuming unnecessary disk space and IO time.
 * <p>
 * This class allows to generate an {@link InputStream} from a local file that the repeatable streaming engine will buffer from
 * instead of generating a new one.
 * <p>
 * Immutability is a <b>MANDATORY PRECONDITION</b> for using this class. The file <b>WILL NOT</b> be modified by this class or the
 * Mule streaming framework in any way. But at the same time, the file's immutability needs to be guaranteed, at least for the
 * duration of this stream's lifecycle. This means that while the file is in use by instances of this class, no other process or
 * thread can modify the file's content. Failing to meet this condition will result in dirty reads and content corruption. If this
 * condition cannot be assured, then use a regular {@link FileInputStream} instead.
 * <p>
 * Also keep in mind that the purpose of this class is to optimize repeatable streaming resources on certain cases. However, in
 * the context of a Mule application, the user can always decide to disable repeatable streaming, in which case, this stream will
 * not be treated in a repeatable manner. The user can also configure an in-memory streaming strategy, in which case the behavior
 * will be similar to using a {@link FileInputStream}.
 * <p>
 * Finally, notice that this stream is not auto closeable. {@link #close()} method needs to be invoked explicitly.
 *
 * @since 1.3.0
 */
public final class ImmutableFileRepeatableInputStream
    extends org.mule.sdk.api.streaming.bytes.ImmutableFileRepeatableInputStream {

  public ImmutableFileRepeatableInputStream(File file, boolean autoDelete) {
    super(file, autoDelete);
  }

}
