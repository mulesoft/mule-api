/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.internal.util;

import static java.lang.Math.min;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * Uses a custom {@link OutputStream} to only count sizes when asked to write a {@link String}, eventually getting it's whole
 * {@link Charset} dependent byte size. This is essentially the same as the size obtained via {@link String#getBytes(Charset)} but
 * avoids the allocation of the entire byte array.
 *
 * @since 1.0
 */
public class StringByteSizeCalculator {

  private static final int WRITE_CHUNK = 8 * 1024;

  public long count(String payload, Charset charset) {
    CountingOutputStream countingStream = new CountingOutputStream();

    try (Writer writer = new OutputStreamWriter(countingStream, charset)) {
      for (int i = 0; i < payload.length(); i += WRITE_CHUNK) {
        int end = min(payload.length(), i + WRITE_CHUNK);
        writer.write(payload, i, end - i);
      }

      writer.flush();
    } catch (IOException e) {
      // Do nothing, our OS should not produce failures
    }

    return countingStream.getSize();
  }

  private class CountingOutputStream extends OutputStream {

    private long size = 0L;

    @Override
    public void write(int b) {
      ++size;
    }

    @Override
    public void write(byte[] b) {
      size += b.length;
    }

    @Override
    public void write(byte[] b, int offset, int len) {
      size += len;
    }

    public long getSize() {
      return size;
    }

  }

}
