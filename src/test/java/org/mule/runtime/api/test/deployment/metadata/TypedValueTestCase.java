/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.deployment.metadata;

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
