/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/**
 * Test module for mule-api
 *
 * @since 1.5
 */
module org.mule.runtime.api.test {

  requires transitive org.mule.runtime.api;
  requires transitive org.mule.runtime.api.annotations;
  requires org.mule.runtime.metadata.model.api;
  requires org.mule.runtime.metadata.model.message;
  requires org.mule.runtime.artifact.declaration;
  requires org.mule.runtime.artifact.declaration.persistence;


  requires com.github.benmanes.caffeine;
  requires com.google.common;
  requires com.google.gson;
  requires org.apache.commons.io;
  requires org.apache.commons.lang3;
  requires semver4j;
  requires transitive junit;
  requires org.hamcrest;
  requires io.qameta.allure.commons;
  requires org.mockito;
  requires org.json;
  requires jsonassert;

  exports org.mule.runtime.api.test;
  exports org.mule.runtime.api.test.component.execution;
  exports org.mule.runtime.api.test.component.location;
  exports org.mule.runtime.api.test.deployment.el;
  exports org.mule.runtime.api.test.deployment.exception;
  exports org.mule.runtime.api.test.deployment.functional;
  exports org.mule.runtime.api.test.deployment.internal.util.collection;
  exports org.mule.runtime.api.test.deployment.internal.exception;
  exports org.mule.runtime.api.test.deployment.message.error.matcher;
  exports org.mule.runtime.api.test.deployment.meta;
  exports org.mule.runtime.api.test.deployment.meta.model;
  exports org.mule.runtime.api.test.deployment.meta.model.declaration.fuent;
  exports org.mule.runtime.api.test.deployment.meta.model.tck;
  exports org.mule.runtime.api.test.deployment.meta.model.util;
  exports org.mule.runtime.api.test.deployment.metadata;
  exports org.mule.runtime.api.test.deployment.metadata.descriptor;
  exports org.mule.runtime.api.test.deployment.persistence;
  exports org.mule.runtime.api.test.deployment.store;
  exports org.mule.runtime.api.test.streaming;
  exports org.mule.runtime.api.test.util;
  exports org.mule.runtime.api.test.util.collection;
  exports org.mule.runtime.api.test.util.tck;
  exports org.mule.runtime.api.test.util.xmlsecurity;

  opens org.mule.runtime.api.test.deployment.meta.model.util to org.mockito;
}
