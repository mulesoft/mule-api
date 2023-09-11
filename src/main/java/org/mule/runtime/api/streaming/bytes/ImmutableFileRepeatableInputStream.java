/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.streaming.bytes;

import static org.mule.runtime.api.util.Preconditions.checkArgument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public final class ImmutableFileRepeatableInputStream extends InputStream {

  private final InputStream delegate;
  private final File file;
  private final boolean autoDelete;

  /**
   * Creates a new instance
   *
   * @param file       the File that contains the stream's contents
   * @param autoDelete if {@code true}, the file will be deleted when {@link #close()} is invoked.
   * @throws IllegalArgumentException if the file cannot be found
   */
  public ImmutableFileRepeatableInputStream(File file, boolean autoDelete) {
    checkArgument(file != null, "File cannot be null");
    try {
      delegate = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + file.getAbsolutePath() + " does not exists", e);
    }
    this.file = file;
    this.autoDelete = autoDelete;
  }

  /**
   * @return The {@link File} backing this stream
   */
  public File getFile() {
    return file;
  }

  /**
   * @return Whether the file will be deleted upon {@link #close()} or not.
   */
  public boolean isAutoDelete() {
    return autoDelete;
  }

  @Override
  public void close() throws IOException {
    delegate.close();
    if (autoDelete) {
      file.delete();
    }
  }

  @Override
  public int read() throws IOException {
    return delegate.read();
  }

  @Override
  public int read(byte[] b) throws IOException {
    return delegate.read(b);
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    return delegate.read(b, off, len);
  }

  @Override
  public long skip(long n) throws IOException {
    return delegate.skip(n);
  }

  @Override
  public int available() throws IOException {
    return delegate.available();
  }

  @Override
  public void mark(int readlimit) {
    delegate.mark(readlimit);
  }

  @Override
  public void reset() throws IOException {
    delegate.reset();
  }

  @Override
  public boolean markSupported() {
    return delegate.markSupported();
  }
}
