/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.data.sample.SampleDataProviderModel;

import java.util.Optional;

/**
 * Contract interface for a {@link BaseDeclaration} in which it's possible to get/set {@link OutputDeclaration} objects of payload
 * and attributes.
 *
 * @since 1.0
 */
@NoImplement
public interface WithOutputDeclaration {

  /**
   * @return the {@link OutputDeclaration} that describes {@code this} declarations output type.
   */
  OutputDeclaration getOutput();

  /**
   * Sets the {@link OutputDeclaration} that describes {@code this} declarations output type.
   */
  void setOutput(OutputDeclaration content);

  /**
   * @return the {@link OutputDeclaration} that describes {@code this} declarations output attributes type.
   */
  OutputDeclaration getOutputAttributes();

  /**
   * Sets the {@link OutputDeclaration} that describes {@code this} declarations output attributes type.
   */
  void setOutputAttributes(OutputDeclaration outputAttributes);

  /**
   * @return an {@link Optional} {@link SampleDataProviderModel} associated to this declaration
   * @since 1.4.0
   */
  Optional<SampleDataProviderModel> getSampleDataProviderModel();

  /**
   * Sets a {@link SampleDataProviderModel} that describes {@code this} declaration's sample data capabilities
   *
   * @param sampleProviderModel a {@link SampleDataProviderModel}
   * @since 1.4.0
   */
  void setSampleDataProviderModel(SampleDataProviderModel sampleProviderModel);
}
