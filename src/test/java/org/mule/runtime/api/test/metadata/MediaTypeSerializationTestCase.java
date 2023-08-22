/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.metadata;

import static java.nio.charset.StandardCharsets.UTF_8;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.mule.runtime.api.metadata.MediaType;

import java.io.InputStream;
import java.io.ObjectInputStream;

import org.junit.Test;

public class MediaTypeSerializationTestCase {


  @Test
  public void readFromV10() throws Exception {
    // The media type is "m/s; param1=value1; param2=value2; charset=UTF-8"
    final InputStream resourceAsStream = getClass().getResourceAsStream("/media_type/mediaTypeV10.ser");
    final ObjectInputStream objectInputStream = new ObjectInputStream(resourceAsStream);
    final Object o = objectInputStream.readObject();
    assertThat(o, instanceOf(MediaType.class));
    final MediaType parsed = (MediaType) o;
    assertThat(parsed.isDefinedInApp(), is(false));
    assertThat(parsed.getPrimaryType(), is("m"));
    assertThat(parsed.getSubType(), is("s"));
    assertThat(parsed.getCharset().get(), is(UTF_8));
    assertThat(parsed.getParameter("charset"), is(nullValue()));
    assertThat(parsed.getParameter("param1"), is("value1"));
    assertThat(parsed.getParameter("param2"), is("value2"));
    assertThat(parsed.getParameter(""), is(nullValue()));
    assertThat(parsed.toRfcString(), is("m/s; charset=UTF-8; param1=\"value1\"; param2=\"value2\""));
  }

  @Test
  public void readFromV11() throws Exception {
    // The media type is "m/s; param1=value1; param2=value2; charset=UTF-8"
    final InputStream resourceAsStream = getClass().getResourceAsStream("/media_type/mediaTypeV11.ser");
    final ObjectInputStream objectInputStream = new ObjectInputStream(resourceAsStream);
    final Object o = objectInputStream.readObject();
    assertThat(o, instanceOf(MediaType.class));
    final MediaType parsed = (MediaType) o;
    assertThat(parsed.isDefinedInApp(), is(false));
    assertThat(parsed.getPrimaryType(), is("m"));
    assertThat(parsed.getSubType(), is("s"));
    assertThat(parsed.getCharset().get(), is(UTF_8));
    assertThat(parsed.getParameter("charset"), is(nullValue()));
    assertThat(parsed.getParameter("param1"), is("value1"));
    assertThat(parsed.getParameter("param2"), is("value2"));
    assertThat(parsed.getParameter(""), is(nullValue()));
    assertThat(parsed.toRfcString(), is("m/s; charset=UTF-8; param1=\"value1\"; param2=\"value2\""));
  }


  @Test
  public void readFromV11DefinedInApp() throws Exception {
    // The media type is "m/s; param1=value1; param2=value2; charset=UTF-8"
    final InputStream resourceAsStream = getClass().getResourceAsStream("/media_type/mediaTypeV11DefinedInApp.ser");
    final ObjectInputStream objectInputStream = new ObjectInputStream(resourceAsStream);
    final Object o = objectInputStream.readObject();
    assertThat(o, instanceOf(MediaType.class));
    final MediaType parsed = (MediaType) o;
    assertThat(parsed.isDefinedInApp(), is(true));
    assertThat(parsed.getPrimaryType(), is("m"));
    assertThat(parsed.getSubType(), is("s"));
    assertThat(parsed.getCharset().get(), is(UTF_8));
    assertThat(parsed.getParameter("charset"), is(nullValue()));
    assertThat(parsed.getParameter("param1"), is("value1"));
    assertThat(parsed.getParameter("param2"), is("value2"));
    assertThat(parsed.getParameter(""), is(nullValue()));
    assertThat(parsed.toRfcString(), is("m/s; charset=UTF-8; param1=\"value1\"; param2=\"value2\""));
  }
}
