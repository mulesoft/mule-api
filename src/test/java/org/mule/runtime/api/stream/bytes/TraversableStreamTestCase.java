/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.stream.bytes;

import static java.lang.Math.toIntExact;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import org.mule.runtime.api.util.func.UnsafeRunnable;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TraversableStreamTestCase extends AbstractByteStreamingTestCase {

  @Parameterized.Parameters(name = "{0}")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
        {"In Memory", KB_256, MB_1},
        {"File Store", MB_2, KB_256}
    });
  }

  private final int halfDataLength;
  private final int bufferSize;

  private TraversableStream stream;
  private ExecutorService executorService = newFixedThreadPool(2);
  private CountDownLatch controlLatch;
  private CountDownLatch mainThreadLatch;

  public TraversableStreamTestCase(String name, int dataSize, int bufferSize) {
    super(dataSize);
    this.bufferSize = bufferSize;
    halfDataLength = data.length() / 2;
    stream = new TraversableStream(data.getBytes(), bufferSize);
    resetLatches();
  }

  @After
  public void after() {
    executorService.shutdownNow();
  }

  @Test
  public void readFullyWithImplicitCursor() throws IOException {
    assertThat(IOUtils.toString(stream), equalTo(data));
  }

  @Test
  public void readFullyByteByByteWithImplicitCursor() throws IOException {
    for (int i = 0; i < data.length(); i++) {
      assertThat((char) stream.read(), equalTo(data.charAt(i)));
    }
  }

  @Test
  public void partialReadOnImplicitCursor() throws Exception {
    byte[] dest = new byte[halfDataLength];

    stream.read(dest, 0, halfDataLength);
    assertThat(toString(dest), equalTo(data.substring(0, halfDataLength)));
  }

  @Test
  public void partialReadWithOffsetOnImplicitCursor() throws Exception {
    byte[] dest = new byte[halfDataLength + 2];

    dest[0] = "!".getBytes()[0];
    dest[1] = dest[0];

    stream.read(dest, 2, halfDataLength);
    assertThat(toString(dest), equalTo("!!" + data.substring(0, halfDataLength)));
  }

  @Test
  public void randomSeekWithOneOpenCursor() throws Exception {
    CursorStream cursor = stream.openCursor();
    try {

      // read fully
      assertThat(IOUtils.toString(cursor), equalTo(data));

      // go back and read first 10 bytes
      seekAndAssert(cursor, 0, 10);

      // move to the middle and read the rest
      seekAndAssert(cursor, halfDataLength, halfDataLength);
    } finally {
      cursor.close();
    }
  }

  @Test
  public void twoOpenCursorsConsumingTheStreamInSingleThread() throws Exception {
    CursorStream cursor1 = stream.openCursor();
    CursorStream cursor2 = stream.openCursor();

    try {
      seekAndAssert(cursor1, 0, data.length());
      seekAndAssert(cursor2, 0, data.length());
    } finally {
      close(cursor1, cursor2);
    }
  }

  @Test
  public void twoOpenCursorsReadingOppositeEndsOfTheStreamInSingleThread() throws Exception {
    CursorStream cursor1 = stream.openCursor();
    CursorStream cursor2 = stream.openCursor();

    try {
      seekAndAssert(cursor1, 0, data.length() / 2);
      seekAndAssert(cursor2, halfDataLength, halfDataLength);
    } finally {
      close(cursor1, cursor2);
    }
  }

  @Test
  public void twoOpenCursorsConsumingTheStreamConcurrently() throws Exception {
    CursorStream cursor1 = stream.openCursor();
    CursorStream cursor2 = stream.openCursor();

    try {

      doAsync(() -> seekAndAssert(cursor1, 0, data.length()),
              () -> seekAndAssert(cursor2, 0, data.length()));

    } finally {
      close(cursor1, cursor2);
    }
  }

  @Test
  public void twoOpenCursorsReadingOppositeEndsOfTheStreamConcurrently() throws Exception {
    CursorStream cursor1 = stream.openCursor();
    CursorStream cursor2 = stream.openCursor();

    try {

      doAsync(() -> seekAndAssert(cursor1, 0, data.length() / 2),
              () -> seekAndAssert(cursor2, halfDataLength, halfDataLength));

    } finally {
      close(cursor1, cursor2);
    }
  }

  @Test
  public void getPosition() throws Exception {
    CursorStream cursor = stream.openCursor();
    try {
      assertThat(cursor.getPosition(), is(0L));

      cursor.seek(10);
      assertThat(cursor.getPosition(), is(10L));

      cursor.resetStream();
      assertThat(cursor.getPosition(), is(0L));
    } finally {
      close(cursor);
    }
  }

  @Test
  public void isClosed() throws Exception {
    CursorStream cursor = stream.openCursor();
    try {
      assertThat(cursor.isClosed(), is(false));
      IOUtils.toString(cursor);
      assertThat(cursor.isClosed(), is(false));
    } finally {
      close(cursor);
      assertThat(cursor.isClosed(), is(true));
    }
  }

  @Test
  public void getSliceWhichStartsInCurrentSegmentButEndsInTheNext() throws Exception {
    if (data.length() < bufferSize) {
      // this test only makes sense for off heap streams
      return;
    }

    final int position = bufferSize - 10;
    final int len = bufferSize / 2;
    byte[] dest = new byte[len];

    CursorStream cursor = stream.openCursor();
    try {
      cursor.seek(position);
      assertThat(cursor.read(dest, 0, len), is(len));
      assertThat(toString(dest), equalTo(data.substring(position, position + len)));
    } finally {
      close(cursor);
    }
  }

  private void doAsync(UnsafeRunnable task1, UnsafeRunnable task2) throws Exception {
    resetLatches();
    Future future1 = doAsync(() -> {
      controlLatch.await();
      task1.run();
      mainThreadLatch.countDown();
    });

    Future future2 = doAsync(() -> {
      controlLatch.countDown();
      task2.run();
      mainThreadLatch.countDown();
    });

    awaitMainThreadLatch();
    assertThat(future1.get(), is(nullValue()));
    assertThat(future2.get(), is(nullValue()));
  }

  private Future doAsync(UnsafeRunnable task) {
    return executorService.submit(() -> {
      try {
        task.run();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void awaitMainThreadLatch() throws InterruptedException {
    mainThreadLatch.await(1, SECONDS);
  }


  private void seekAndAssert(CursorStream cursor, long position, int length) throws Exception {
    byte[] randomBytes = new byte[length];
    cursor.seek(position);
    cursor.read(randomBytes, 0, length);
    assertThat(toString(randomBytes), equalTo(data.substring(toIntExact(position), toIntExact(position + length))));
  }

  private void resetLatches() {
    controlLatch = new CountDownLatch(1);
    mainThreadLatch = new CountDownLatch(2);
  }

  private void close(CursorStream... streams) {
    for (CursorStream stream : streams) {
      try {
        stream.close();
      } catch (Exception e) {
        // no - op
      }
    }
  }
}
