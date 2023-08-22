/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.component.location;

import static java.lang.String.format;

import static org.junit.Assert.fail;

import org.mule.runtime.api.component.location.Location;

import java.util.function.Consumer;

import io.qameta.allure.Issue;
import org.junit.Test;

public class LocationTestCase {

  @Test
  public void invalidLocationGlobalName() {
    Consumer<String> locationBuilderConsumer = globalNameLocationConsumer();
    testInvalidParts(locationBuilderConsumer);
  }

  @Test
  public void invalidLocationPart() {
    Consumer<String> locationBuilderConsumer = partLocationConsumer("globalName");
    testInvalidParts(locationBuilderConsumer);
  }

  @Test
  public void validLocationGlobalName() {
    Consumer<String> locationBuilderConsumer = globalNameLocationConsumer();
    testValidParts(locationBuilderConsumer);
  }

  @Test
  public void validLocationPart() {
    Consumer<String> locationBuilderConsumer = partLocationConsumer("globalName");
    testValidParts(locationBuilderConsumer);
  }

  @Test
  @Issue("W-10923083")
  public void aPlaceholderIsAValidGlobalName() {
    Consumer<String> locationBuilderConsumer = partLocationConsumer("${placeholder}");
    testValidParts(locationBuilderConsumer);
  }

  private void testValidParts(Consumer<String> locationBuilderConsumer) {
    validLocation("lala", locationBuilderConsumer);
    validLocation("la\\la", locationBuilderConsumer);
    validLocation("la:la", locationBuilderConsumer);
    validLocation("\\lala:", locationBuilderConsumer);
    validLocation("lal$a", locationBuilderConsumer);
    validLocation("l$ala", locationBuilderConsumer);
  }

  private Consumer<String> globalNameLocationConsumer() {
    return part -> Location.builder().globalName(part).build();
  }

  private Consumer<String> partLocationConsumer(String globalName) {
    return part -> Location.builder().globalName(globalName).addPart(part).build();
  }

  private void testInvalidParts(Consumer<String> locationBuilderConsumer) {
    invalidLocation("la/la", locationBuilderConsumer);
    invalidLocation("lala}", locationBuilderConsumer);
    invalidLocation("{lala", locationBuilderConsumer);
    invalidLocation("[lala", locationBuilderConsumer);
    invalidLocation("lala]", locationBuilderConsumer);
    invalidLocation("l#ala", locationBuilderConsumer);
    invalidLocation("[l#ala/", locationBuilderConsumer);
  }

  private void invalidLocation(String location, Consumer<String> locationBuilderConsumer) {
    try {
      locationBuilderConsumer.accept(location);
      fail(format("Location part %s should not be valid", location));
    } catch (IllegalArgumentException e) {
      // expected error
    }
  }

  private void validLocation(String location, Consumer<String> locationBuilderConsumer) {
    locationBuilderConsumer.accept(location);
  }

}
