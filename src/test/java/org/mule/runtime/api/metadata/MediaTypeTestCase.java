/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.UnsupportedCharsetException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MediaTypeTestCase {

  @Rule
  public ExpectedException expected = ExpectedException.none();

  @Test
  public void onlyMimeType() {
    final MediaType parsed = MediaType.parse("m/s");
    assertThat(parsed.getPrimaryType(), is("m"));
    assertThat(parsed.getSubType(), is("s"));
    assertThat(parsed.getCharset().isPresent(), is(false));
    assertThat(parsed.getParameter(""), is(nullValue()));
    assertThat(parsed.toRfcString(), is("m/s"));
  }

  @Test
  public void mimeTypeAndCharset() {
    final MediaType parsed = MediaType.parse("m/s; charset=UTF-8");
    assertThat(parsed.getPrimaryType(), is("m"));
    assertThat(parsed.getSubType(), is("s"));
    assertThat(parsed.getCharset().get(), is(UTF_8));
    assertThat(parsed.getParameter("charset"), is(nullValue()));
    assertThat(parsed.getParameter(""), is(nullValue()));
    assertThat(parsed.toRfcString(), is("m/s; charset=UTF-8"));
  }

  @Test
  public void mimeTypeAndParams() {
    final MediaType parsed = MediaType.parse("m/s; param1=value1; param2=value2");
    assertThat(parsed.getPrimaryType(), is("m"));
    assertThat(parsed.getSubType(), is("s"));
    assertThat(parsed.getCharset().isPresent(), is(false));
    assertThat(parsed.getParameter("param1"), is("value1"));
    assertThat(parsed.getParameter("param2"), is("value2"));
    assertThat(parsed.getParameter(""), is(nullValue()));
    assertThat(parsed.toRfcString(), is("m/s; param1=\"value1\"; param2=\"value2\""));
  }

  @Test
  public void mimeTypeCharsetAndParams() {
    final MediaType parsed = MediaType.parse("m/s; param1=value1; param2=value2; charset=UTF-8");
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
  public void paramWithSpecialChar() {
    final MediaType parsed1 = MediaType.parse("m/s; param1=\"@\"");

    assertThat(parsed1.getParameter("param1"), is("@"));

    final MediaType parsed2 = MediaType.parse(parsed1.toRfcString());

    assertThat(parsed1, is(parsed2));
  }

  @Test
  public void changeCharset() {
    final MediaType parsed = MediaType.parse("m/s");

    final MediaType withCharset = parsed.withCharset(UTF_8);

    assertThat(withCharset.getPrimaryType(), is("m"));
    assertThat(withCharset.getSubType(), is("s"));
    assertThat(withCharset.getCharset().get(), is(UTF_8));
    assertThat(withCharset.getParameter("charset"), is(nullValue()));
    assertThat(withCharset.getParameter(""), is(nullValue()));
    assertThat(withCharset.toRfcString(), is("m/s; charset=UTF-8"));

    assertThat(parsed.matches(withCharset), is(true));
    assertThat(parsed.equals(withCharset), is(false));
  }

  @Test
  public void removeCharset() {
    final MediaType parsed = MediaType.parse("m/s; charset=UTF-8");

    final MediaType withoutCharset = parsed.withoutParameters();

    assertThat(withoutCharset.getPrimaryType(), is("m"));
    assertThat(withoutCharset.getSubType(), is("s"));
    assertThat(withoutCharset.getCharset().isPresent(), is(false));
    assertThat(withoutCharset.getParameter("charset"), is(nullValue()));
    assertThat(withoutCharset.getParameter(""), is(nullValue()));
    assertThat(withoutCharset.toRfcString(), is("m/s"));

    assertThat(parsed.matches(withoutCharset), is(true));
    assertThat(parsed.equals(withoutCharset), is(false));
  }

  @Test
  public void removeParams() {
    final MediaType parsed = MediaType.parse("m/s; param1=value1; param2=value2");

    final MediaType withoutParams = parsed.withoutParameters();

    assertThat(withoutParams.getPrimaryType(), is("m"));
    assertThat(withoutParams.getSubType(), is("s"));
    assertThat(withoutParams.getCharset().isPresent(), is(false));
    assertThat(withoutParams.getParameter("charset"), is(nullValue()));
    assertThat(withoutParams.getParameter("param1"), is(nullValue()));
    assertThat(withoutParams.getParameter("param2"), is(nullValue()));
    assertThat(withoutParams.getParameter(""), is(nullValue()));
    assertThat(withoutParams.toRfcString(), is("m/s"));

    assertThat(parsed.matches(withoutParams), is(true));
    assertThat(parsed.equals(withoutParams), is(false));
  }

  @Test
  public void changeCharsetWithParams() {
    final MediaType parsed = MediaType.parse("m/s; param1=value1; param2=value2");

    final MediaType withCharset = parsed.withCharset(UTF_8);

    assertThat(withCharset.getPrimaryType(), is("m"));
    assertThat(withCharset.getSubType(), is("s"));
    assertThat(withCharset.getCharset().get(), is(UTF_8));
    assertThat(withCharset.getParameter("charset"), is(nullValue()));
    assertThat(withCharset.getParameter("param1"), is("value1"));
    assertThat(withCharset.getParameter("param2"), is("value2"));
    assertThat(withCharset.getParameter(""), is(nullValue()));
    assertThat(withCharset.toRfcString(), is("m/s; charset=UTF-8; param1=\"value1\"; param2=\"value2\""));

    assertThat(parsed.matches(withCharset), is(true));
    assertThat(parsed.equals(withCharset), is(false));
  }

  @Test
  public void matchesMimeType() {
    final MediaType parsed11 = MediaType.parse("m1/s1");
    final MediaType parsed12 = MediaType.parse("m1/s2");
    final MediaType parsed21 = MediaType.parse("m2/s1");
    final MediaType parsed22 = MediaType.parse("m2/s2");

    assertThat(parsed11.matches(MediaType.parse(parsed11.toRfcString())), is(true));
    assertThat(parsed11.matches(MediaType.parse(parsed12.toRfcString())), is(false));
    assertThat(parsed11.matches(MediaType.parse(parsed21.toRfcString())), is(false));
    assertThat(parsed11.matches(MediaType.parse(parsed22.toRfcString())), is(false));

    assertThat(parsed11.matches(MediaType.ANY), is(false));
    assertThat(MediaType.ANY.matches(parsed11), is(false));
  }

  @Test
  public void invalidMimeType() {
    expected.expect(IllegalArgumentException.class);
    expected.expectMessage(containsString("MediaType cannot be parsed"));
    expected.expectMessage(containsString("invalid"));
    MediaType.parse("invalid");
  }

  @Test
  public void invalidCharset() {
    expected.expect(UnsupportedCharsetException.class);
    expected.expectMessage(containsString("invalid"));
    MediaType.parse("m/s;charset=invalid");
  }

  @Test
  public void invalidCharsInParamsMimeType() {
    expected.expect(IllegalArgumentException.class);
    expected.expectMessage(containsString("MediaType cannot be parsed"));
    expected.expectMessage(containsString("m/s;param=@"));
    MediaType.parse("m/s;param=@");
  }

  @Test
  public void serialize() throws IOException, ClassNotFoundException {
    final MediaType parsed = MediaType.parse("m/s");

    final ByteArrayOutputStream os = new ByteArrayOutputStream();
    new ObjectOutputStream(os).writeObject(parsed);
    final Object deserialized = new ObjectInputStream(new ByteArrayInputStream(os.toByteArray())).readObject();

    assertThat(deserialized, is(parsed));
  }

  @Test
  public void serializeWithCharset() throws IOException, ClassNotFoundException {
    final MediaType parsed = MediaType.parse("m/s");
    final MediaType withCharset = parsed.withCharset(UTF_8);


    final ByteArrayOutputStream os = new ByteArrayOutputStream();
    new ObjectOutputStream(os).writeObject(withCharset);
    final Object deserialized = new ObjectInputStream(new ByteArrayInputStream(os.toByteArray())).readObject();

    assertThat(deserialized, is(withCharset));
  }
}
