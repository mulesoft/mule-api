/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.meta.model.deprecated;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

import org.mule.runtime.api.meta.model.deprecated.DeprecableModel;
import org.mule.runtime.api.meta.model.deprecated.DeprecationModel;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class DeprecableModelTest {

  private DeprecationModel deprecationModel;

  @Before
  public void setUp() throws Exception {
    deprecationModel = mock(DeprecationModel.class);
  }

  @Test
  public void testIsDeprecatedWhenDeprecated() {
    DeprecableModel deprecableModel = new TestDeprecableModel(Optional.of(deprecationModel));
    boolean isDeprecated = deprecableModel.isDeprecated();

    assertThat("isDeprecated should return true when a deprecation model is present", isDeprecated, is(true));
  }

  private static class TestDeprecableModel implements DeprecableModel {

    private final Optional<DeprecationModel> deprecationModel;

    TestDeprecableModel(Optional<DeprecationModel> deprecationModel) {
      this.deprecationModel = deprecationModel;
    }

    @Override
    public Optional<DeprecationModel> getDeprecationModel() {
      return deprecationModel;
    }
  }
}
