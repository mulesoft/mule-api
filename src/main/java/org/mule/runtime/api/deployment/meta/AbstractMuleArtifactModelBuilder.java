/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.deployment.meta;

import static org.mule.runtime.api.util.Preconditions.checkArgument;
import org.mule.api.annotation.NoExtend;

/**
 * A builder to create instances of {@link AbstractMuleArtifactModel} implementations.
 *
 * @param <T> type of the builder, used to enable builder pattern on subclasses methods
 * @param <M> type of the model being built, used to have type safety
 *
 * @since 1.0
 */
@NoExtend
public abstract class AbstractMuleArtifactModelBuilder<T extends AbstractMuleArtifactModelBuilder, M extends AbstractMuleArtifactModel> {

  private String name;
  private String minMuleVersion;
  private Product product;
  private MuleArtifactLoaderDescriptor bundleDescriptorLoader;
  private MuleArtifactLoaderDescriptor classLoaderModelDescriptorLoader;

  /**
   * Sets the describer's name
   *
   * @param name the name the describer will have
   * @return {@code this} builder
   */
  public T setName(String name) {
    this.name = name;

    return getThis();
  }

  /**
   * Sets the required product for the artifact.
   * 
   * @param product the required product for the artifact.
   * @return {@code this} builder
   */
  public T setRequiredProduct(Product product) {
    this.product = product;
    return getThis();
  }

  public String getName() {
    return name;
  }

  public Product getRequiredProduct() {
    return product;
  }

  /**
   * @return the parametrized type of the builder
   */
  protected abstract T getThis();

  /**
   * Sets the describer's minimum Mule Runtime version that requires to work correctly
   *
   * @param muleVersion of the describer
   * @return {@code this} builder
   */
  public T setMinMuleVersion(String muleVersion) {
    this.minMuleVersion = muleVersion;
    return getThis();
  }

  public String getMinMuleVersion() {
    return minMuleVersion;
  }

  /**
   * Sets the bundle descriptor loader for the artifact
   *
   * @param bundleDescriptorLoader describes the loader for the bundle descriptor. Non null
   * @return a {@link MuleArtifactLoaderDescriptor} to populate the {@link ClassLoader} describer with the ID and any additional
   *         attributes
   */
  public T withBundleDescriptorLoader(MuleArtifactLoaderDescriptor bundleDescriptorLoader) {
    checkArgument(bundleDescriptorLoader != null, "bundleDescriptorLoader cannot be null");
    this.bundleDescriptorLoader = bundleDescriptorLoader;

    return getThis();
  }

  /**
   * Sets the bundle descriptor loader for the artifact
   *
   * @param classLoaderModelDescriptorLoader describes the loader for the classloader model descriptor. Non null
   * @return a {@link MuleArtifactLoaderDescriptor} to populate the {@link ClassLoader} describer with the ID and any additional
   *         attributes
   */
  public T withClassLoaderModelDescriptorLoader(MuleArtifactLoaderDescriptor classLoaderModelDescriptorLoader) {
    checkArgument(classLoaderModelDescriptorLoader != null, "classLoaderModelDescriptorLoader cannot be null");
    this.classLoaderModelDescriptorLoader = classLoaderModelDescriptorLoader;

    return getThis();
  }

  public MuleArtifactLoaderDescriptor getBundleDescriptorLoader() {
    return bundleDescriptorLoader;
  }


  public MuleArtifactLoaderDescriptor getClassLoaderModelDescriptorLoader() {
    return classLoaderModelDescriptorLoader;
  }

  /**
   * @return a well formed model of {@link M} type
   */
  public abstract M build();
}
