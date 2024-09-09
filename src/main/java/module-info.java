/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
import org.mule.api.annotation.jpms.PrivilegedApi;

/**
 * API for Mule Runtime.
 * 
 * @moduleGraph
 * @since 1.5
 */
@PrivilegedApi(
    privilegedPackages = {
        "org.mule.runtime.privileged.event"
    },
    privilegedArtifactIds = {})
module org.mule.runtime.api {

  requires transitive org.mule.runtime.api.annotations;
  requires org.mule.runtime.metadata.model.api;
  requires org.mule.runtime.metadata.model.message;
  requires org.mule.runtime.artifact.declaration;
  requires org.mule.runtime.artifact.declaration.persistence;
  
  requires jakarta.activation;
  requires java.naming;
  requires java.sql;
  requires transitive java.inject;
  
  // SLF4J is the API provided by the Mule Runtime
  // for artifacts to use the underlying logging functionality
  requires transitive org.slf4j;
  
  requires com.github.benmanes.caffeine;
  requires com.google.common;
  requires com.google.gson;
  requires org.apache.commons.io;
  requires org.apache.commons.lang3;
  requires semver4j;
  
  exports org.mule.runtime.api.app.declaration.serialization;
  exports org.mule.runtime.api.artifact;
  exports org.mule.runtime.api.bulk;
  exports org.mule.runtime.api.util.classloader;
  exports org.mule.runtime.api.cluster;
  exports org.mule.runtime.api.component;
  exports org.mule.runtime.api.component.execution;
  exports org.mule.runtime.api.component.location;
  exports org.mule.runtime.api.config;
  exports org.mule.runtime.api.config.custom;
  exports org.mule.runtime.api.connection;
  exports org.mule.runtime.api.connection.serialization;
  exports org.mule.runtime.api.connectivity;
  exports org.mule.runtime.api.deployment.management;
  exports org.mule.runtime.api.deployment.meta;
  exports org.mule.runtime.api.deployment.persistence;
  exports org.mule.runtime.api.dsl;
  exports org.mule.runtime.api.el;
  exports org.mule.runtime.api.el.validation;
  exports org.mule.runtime.api.event;
  exports org.mule.runtime.api.exception;
  exports org.mule.runtime.api.functional;
  exports org.mule.runtime.api.healthcheck;
  exports org.mule.runtime.api.i18n;
  exports org.mule.runtime.api.ioc;
  exports org.mule.runtime.api.interception;
  exports org.mule.runtime.api.legacy.exception;
  exports org.mule.runtime.api.lifecycle;
  exports org.mule.runtime.api.lock;
  exports org.mule.runtime.api.message;
  exports org.mule.runtime.api.message.error.matcher;
  exports org.mule.runtime.api.meta;
  exports org.mule.runtime.api.meta.type;
  exports org.mule.runtime.api.meta.model;
  exports org.mule.runtime.api.meta.model.config;
  exports org.mule.runtime.api.meta.model.data.sample;
  exports org.mule.runtime.api.meta.model.declaration.fluent;
  exports org.mule.runtime.api.meta.model.declaration.fluent.util;
  exports org.mule.runtime.api.meta.model.deprecated;
  exports org.mule.runtime.api.meta.model.connection;
  exports org.mule.runtime.api.meta.model.construct;
  exports org.mule.runtime.api.meta.model.display;
  exports org.mule.runtime.api.meta.model.error;
  exports org.mule.runtime.api.meta.model.function;
  exports org.mule.runtime.api.meta.model.nested;
  exports org.mule.runtime.api.meta.model.notification;
  exports org.mule.runtime.api.meta.model.operation;
  exports org.mule.runtime.api.meta.model.parameter;
  exports org.mule.runtime.api.meta.model.source;
  exports org.mule.runtime.api.meta.model.stereotype;
  exports org.mule.runtime.api.meta.model.util;
  exports org.mule.runtime.api.meta.model.version;
  exports org.mule.runtime.api.memory.management;
  exports org.mule.runtime.api.memory.provider;
  exports org.mule.runtime.api.memory.provider.type;
  exports org.mule.runtime.api.metadata;
  exports org.mule.runtime.api.metadata.descriptor;
  exports org.mule.runtime.api.metadata.resolving;
  exports org.mule.runtime.api.notification;
  exports org.mule.runtime.api.parameterization;
  exports org.mule.runtime.api.retry.policy;
  exports org.mule.runtime.api.sampledata;
  exports org.mule.runtime.api.scheduler;
  exports org.mule.runtime.api.security;
  exports org.mule.runtime.api.serialization;
  exports org.mule.runtime.api.service;
  exports org.mule.runtime.api.source;
  exports org.mule.runtime.api.store;
  exports org.mule.runtime.api.streaming;
  exports org.mule.runtime.api.streaming.bytes;
  exports org.mule.runtime.api.streaming.object;
  exports org.mule.runtime.api.streaming.exception;
  exports org.mule.runtime.api.time;
  exports org.mule.runtime.api.tls;
  exports org.mule.runtime.api.transformation;
  exports org.mule.runtime.api.tx;
  exports org.mule.runtime.api.util;
  exports org.mule.runtime.api.util.collection;
  exports org.mule.runtime.api.util.concurrent;
  exports org.mule.runtime.api.util.xmlsecurity;
  exports org.mule.runtime.api.value;

  uses org.mule.runtime.api.config.custom.ServiceConfigurator;
  uses org.mule.runtime.api.connectivity.ConnectivityTestingStrategy;
  uses org.mule.runtime.api.el.AbstractBindingContextBuilderFactory;
  uses org.mule.runtime.api.message.AbstractMuleMessageBuilderFactory;
  uses org.mule.runtime.api.metadata.AbstractDataTypeBuilderFactory;
  uses org.mule.runtime.api.tls.AbstractTlsContextFactoryBuilderFactory;

  // for DataWeave
  exports org.mule.runtime.privileged.event;
  exports org.mule.runtime.privileged.exception;

  // For runtime-extension-model and extensions-support
  exports org.mule.runtime.privileged.metadata;

  // Internals exposed to test module
  exports org.mule.runtime.internal.exception to
      org.mule.runtime.api.test;
  exports org.mule.runtime.internal.util to
      org.mule.runtime.api.test;
  exports org.mule.runtime.internal.util.collection to
      org.mule.runtime.api.test;
  exports org.mule.runtime.internal.util.xmlsecurity to
      org.mule.runtime.api.test;
  exports org.mule.runtime.internal.meta.model to
      org.mule.runtime.artifact.ast;

  // Allow extensions-support to create objects from these packages dynamically
  opens org.mule.runtime.api.connection to
      org.mule.runtime.extensions.support;
  opens org.mule.runtime.api.exception to
      org.mule.runtime.extensions.support,
      // Introspection by kryo used by mule serializer
      kryo.shaded;
  opens org.mule.runtime.privileged.exception to
      // Introspection by kryo used by mule serializer
      kryo.shaded;
  opens org.mule.runtime.api.lifecycle to
      // Introspection by kryo used by mule serializer
      kryo.shaded;
  opens org.mule.runtime.api.message to
      // Introspection by kryo used by mule serializer
      kryo.shaded;
  opens org.mule.runtime.api.util to
      org.mule.runtime.extensions.support,
      // Introspection by kryo used by mule serializer
      kryo.shaded;

  // Allow introspection for serialization/deserialization by Gson and Kryo
  opens org.mule.runtime.api.component to
      org.mule.runtime.extensions.support,
      com.google.gson,
      kryo.shaded;
  opens org.mule.runtime.api.i18n to
      kryo.shaded;
  opens org.mule.runtime.api.deployment.meta to
      com.google.gson;
  opens org.mule.runtime.api.meta.model to
      com.google.gson;
  opens org.mule.runtime.api.meta.model.data.sample to
      com.google.gson;
  opens org.mule.runtime.api.meta.model.display to
      com.google.gson;
  opens org.mule.runtime.api.meta.model.error to
      com.google.gson;
  opens org.mule.runtime.api.meta.model.parameter to
      com.google.gson;
  opens org.mule.runtime.api.meta.model.stereotype to
      com.google.gson;
  opens org.mule.runtime.api.metadata to
      org.mule.runtime.extensions.support,
      com.google.gson,
      org.mule.runtime.api.test,
      // Introspection by kryo used by mule serializer
      kryo.shaded;
  opens org.mule.runtime.api.metadata.resolving to
      com.google.gson;
  opens org.mule.runtime.api.value to
      com.google.gson;

}
