/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.util;

import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.api.meta.model.config.ConfigurationModel;
import org.mule.runtime.api.meta.model.connection.ConnectionProviderModel;
import org.mule.runtime.api.meta.model.connection.HasConnectionProviderModels;
import org.mule.runtime.api.meta.model.operation.HasOperationModels;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.parameter.ParameterGroupModel;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;
import org.mule.runtime.api.meta.model.source.HasSourceModels;
import org.mule.runtime.api.meta.model.source.SourceCallbackModel;
import org.mule.runtime.api.meta.model.source.SourceModel;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExtensionWalkerTestCase {

  @Mock
  private ExtensionModel extension;

  @Mock
  private ConfigurationModel configuration;

  @Mock
  private OperationModel operation;

  @Mock
  private ConnectionProviderModel connectionProvider;

  @Mock
  private ParameterGroupModel groupModel;

  @Mock
  private ParameterModel parameterModel;

  @Mock
  private SourceModel source;

  @Mock
  private SourceCallbackModel sourceCallback;

  @Before
  public void before() {

    when(source.getErrorCallback()).thenReturn(of(sourceCallback));
    when(source.getSuccessCallback()).thenReturn(of(sourceCallback));

    when(extension.getConfigurationModels()).thenReturn(asList(configuration));
    when(extension.getOperationModels()).thenReturn(asList(operation));
    when(extension.getSourceModels()).thenReturn(asList(source));
    when(extension.getConnectionProviders()).thenReturn(asList(connectionProvider));

    when(configuration.getOperationModels()).thenReturn(asList(operation));
    when(configuration.getSourceModels()).thenReturn(asList(source));
    when(configuration.getConnectionProviders()).thenReturn(asList(connectionProvider));

    when(groupModel.getParameterModels()).thenReturn(asList(parameterModel));
    addParameter(configuration, operation, connectionProvider, source, sourceCallback);
  }

  private void addParameter(ParameterizedModel... models) {
    for (ParameterizedModel model : models) {
      when(model.getParameterGroupModels()).thenReturn(asList(groupModel));
    }
  }

  @Test
  public void walk() {
    AtomicInteger configs = new AtomicInteger(0);
    AtomicInteger operations = new AtomicInteger(0);
    AtomicInteger sources = new AtomicInteger(0);
    AtomicInteger parameterGroups = new AtomicInteger(0);
    AtomicInteger parameters = new AtomicInteger(0);
    AtomicInteger providers = new AtomicInteger(0);

    new ExtensionWalker() {

      @Override
      public void onConfiguration(ConfigurationModel model) {
        configs.incrementAndGet();
      }

      @Override
      public void onOperation(HasOperationModels owner, OperationModel model) {
        operations.incrementAndGet();
      }

      @Override
      public void onConnectionProvider(HasConnectionProviderModels owner, ConnectionProviderModel model) {
        providers.incrementAndGet();
      }

      @Override
      public void onSource(HasSourceModels owner, SourceModel model) {
        sources.incrementAndGet();
      }

      @Override
      public void onParameterGroup(ParameterizedModel owner, ParameterGroupModel model) {
        parameterGroups.incrementAndGet();
      }

      @Override
      public void onParameter(ParameterizedModel owner, ParameterGroupModel groupModel, ParameterModel model) {
        assertThat(groupModel, is(sameInstance(ExtensionWalkerTestCase.this.groupModel)));
        parameters.incrementAndGet();
      }
    }.walk(extension);

    assertCount(configs, 1);
    assertCount(operations, 2);
    assertCount(sources, 2);
    assertCount(providers, 2);
    assertCount(parameterGroups, 11);
    assertCount(parameters, 11);
  }

  private void assertCount(AtomicInteger actual, int expected) {
    assertThat(actual.get(), is(expected));
  }
}
