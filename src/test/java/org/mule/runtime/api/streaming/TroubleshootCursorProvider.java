/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.streaming;

import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mule.runtime.api.streaming.CursorProvider.TRACK_CURSOR_PROVIDER_CLOSE;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.mule.runtime.api.component.location.ComponentLocation;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;

@RunWith(Parameterized.class)
public class TroubleshootCursorProvider {

  @Parameter
  public CursorProvider cursorProvider;

  @Parameter(1)
  public boolean hasComponentLocation;

  @Parameter(2)
  public boolean trackCursorProviderClose;

  @Parameterized.Parameters(name = "hasComponentLocation: {1}, trackCursorProviderClose: {2}")
  public static Object[] cursorProviders() {
    return new Object[] {
        new Object[] {new DummyCursorProvider(), false, false},
        new Object[] {new DummyCursorProviderComponentLocation(), true, false},
        new Object[] {new DummyCursorProvider(), false, true},
        new Object[] {new DummyCursorProviderComponentLocation(), true, true},
    };
  }

  @Before
  public void setUp() throws NoSuchFieldException, IllegalAccessException {
    setStaticField(CursorProvider.class, "TRACK_CURSOR_PROVIDER_CLOSE", trackCursorProviderClose);
  }

  @Test
  @Issue("MULE-18047")
  @Description("The cursor provider should have a component location reference")
  public void cursorProviderTracksCloser() {
    assertThat(cursorProvider.isTrackCursorProviderClose(), is(trackCursorProviderClose));
    assertThat(TRACK_CURSOR_PROVIDER_CLOSE, is(trackCursorProviderClose));
  }

  @Test
  @Issue("MULE-18047")
  @Description("The cursor provider should have a component location reference")
  public void cursorProviderHasComponentLocation() {
    if (hasComponentLocation) {
      assertThat(cursorProvider.getOriginatingLocation().get(), is(notNullValue()));
    } else {
      assertThat(cursorProvider.getOriginatingLocation().orElse(null), is(nullValue()));
    }
  }

  private void setStaticField(Class cls, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
    Field field = cls.getField(fieldName);
    Field modifiers = Field.class.getDeclaredField("modifiers");

    boolean wasAccessible = field.isAccessible();

    modifiers.setAccessible(true);
    field.setAccessible(true);

    int prevModifiers = modifiers.getInt(field);

    modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

    field.set(null, value);

    modifiers.setInt(field, prevModifiers);
    modifiers.setAccessible(false);
    field.setAccessible(wasAccessible);
  }

  static class DummyCursorProviderComponentLocation extends DummyCursorProvider {

    @Override
    public Optional<ComponentLocation> getOriginatingLocation() {
      return of(mock(ComponentLocation.class));
    }
  }

  static class DummyCursorProvider implements CursorProvider {

    @Override
    public Cursor openCursor() {
      return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void releaseResources() {

    }

    @Override
    public boolean isClosed() {
      return false;
    }
  }
}
