/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.meta.model.connection;

import org.junit.Test;
import static org.junit.Assert.*;

import org.mule.runtime.api.meta.model.connection.ConnectionManagementType;

public class ConnectionManagementTypeTest {

  @Test
  public void testEnumValues() {
    ConnectionManagementType[] expectedValues = {
        ConnectionManagementType.POOLING,
        ConnectionManagementType.CACHED,
        ConnectionManagementType.NONE
    };

    assertArrayEquals(expectedValues, ConnectionManagementType.values());
  }

  @Test
  public void testEnumValueOf() {
    assertEquals(ConnectionManagementType.POOLING, ConnectionManagementType.valueOf("POOLING"));
    assertEquals(ConnectionManagementType.CACHED, ConnectionManagementType.valueOf("CACHED"));
    assertEquals(ConnectionManagementType.NONE, ConnectionManagementType.valueOf("NONE"));
  }
}
