/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.streaming;

import static java.nio.charset.Charset.defaultCharset;
import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.rules.ExpectedException.none;

import org.mule.runtime.api.streaming.bytes.ImmutableFileRepeatableInputStream;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class ImmutableFileRepeatableInputStreamTestCase {

  public static final String CONTENT = "Hello World!";

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Rule
  public ExpectedException expectedException = none();

  private File file;

  @Before
  public void before() throws Exception {
    file = temporaryFolder.newFile();
    writeStringToFile(file, CONTENT, defaultCharset());
  }

  @Test
  public void readFile() throws Exception {
    ImmutableFileRepeatableInputStream stream = new ImmutableFileRepeatableInputStream(file, false);
    assertThat(IOUtils.toString(stream, defaultCharset()), equalTo(CONTENT));
    assertThat(file.exists(), is(true));
  }

  @Test
  public void closeAndKeepFile() throws Exception {
    ImmutableFileRepeatableInputStream stream = new ImmutableFileRepeatableInputStream(file, false);
    assertThat(IOUtils.toString(stream, defaultCharset()), equalTo(CONTENT));
    stream.close();
    assertThat(file.exists(), is(true));
  }

  @Test
  public void closeAndAutoDelete() throws Exception {
    ImmutableFileRepeatableInputStream stream = new ImmutableFileRepeatableInputStream(file, true);
    assertThat(IOUtils.toString(stream, defaultCharset()), equalTo(CONTENT));
    stream.close();
    assertThat(file.exists(), is(false));
  }

  @Test
  public void fileDoesNotExists() {
    final String fileName = "notthere.bin";
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectCause(instanceOf(FileNotFoundException.class));

    new ImmutableFileRepeatableInputStream(new File(temporaryFolder.getRoot(), fileName), true);
  }

  @Test
  public void getFile() {
    ImmutableFileRepeatableInputStream stream = new ImmutableFileRepeatableInputStream(file, true);
    assertThat(stream.getFile().getAbsolutePath(), equalTo(file.getAbsolutePath()));
  }

  @Test
  public void isAutoDelete() {
    ImmutableFileRepeatableInputStream stream = new ImmutableFileRepeatableInputStream(file, true);
    assertThat(stream.isAutoDelete(), is(true));

    stream = new ImmutableFileRepeatableInputStream(file, false);
    assertThat(stream.isAutoDelete(), is(false));
  }
}
