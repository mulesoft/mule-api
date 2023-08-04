/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.test.metadata;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;

import org.junit.Test;

public class TypedValueTestCase {

  @Test
  public void nullValueToString() {
    final DataType mockedDataType = mock(DataType.class);
    final TypedValue<String> typedValue = new TypedValue<>(null, mockedDataType);
    String stringValue = typedValue.toString();
    assertThat(stringValue, containsString("value: \'null\'"));
  }

}
