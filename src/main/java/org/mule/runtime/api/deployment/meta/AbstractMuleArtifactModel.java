/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.meta;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.mule.runtime.api.meta.MuleVersion.NO_REVISION;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import static org.mule.runtime.api.util.Preconditions.checkState;
import org.mule.api.annotation.NoExtend;
import org.mule.runtime.api.meta.MuleVersion;
import org.mule.runtime.api.meta.model.ExtensionModel;

/**
 * Base class for creating models for Mule artifacts from JSON describer files.
 * <p/>
 * The idea behind this object is to just load some bits of information that later each "loader" will consume to generate things
 * like {@link ClassLoader}, {@link ExtensionModel}, etc.
 *
 * @since 1.0
 */
@NoExtend
public abstract class AbstractMuleArtifactModel {

  public static final String NAME = "name";
  public static final String REQUIRED_PRODUCT = "requiredProduct";
  public static final String MIN_MULE_VERSION = "minMuleVersion";
  public static final String ID = "id";
  public static final String CLASS_LOADER_MODEL_LOADER_DESCRIPTOR = "classLoaderModelLoaderDescriptor";
  public static final String BUNDLE_DESCRIPTOR_LOADER = "bundleDescriptorLoader";


  private static final String MANDATORY_FIELD_MISSING_MESSAGE =
      "Invalid artifact descriptor: \"%s\". Mandatory field \"%s\" is missing or has an invalid value. %s";
  private static final String CLASS_LOADER_MODEL_LOADER_DESCRIPTOR_ID = CLASS_LOADER_MODEL_LOADER_DESCRIPTOR + ID;
  private static final String BUNDLE_DESCRIPTOR_LOADER_ID = BUNDLE_DESCRIPTOR_LOADER + ID;



  private final String name;
  private final String minMuleVersion;
  private final Product requiredProduct;
  private final MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor;
  private final MuleArtifactLoaderDescriptor bundleDescriptorLoader;

  /**
   * Creates a new model
   *
   * @param name                             name of the artifact
   * @param minMuleVersion                   minimum Mule Runtime version that requires to work correctly.
   * @param requiredProduct                  the target product for the artifact
   * @param classLoaderModelLoaderDescriptor describes how to create the class loader for the artifact.
   * @param bundleDescriptorLoader           indicates how to load the bundle descriptor.
   */
  protected AbstractMuleArtifactModel(
                                      String name, String minMuleVersion,
                                      Product requiredProduct, MuleArtifactLoaderDescriptor classLoaderModelLoaderDescriptor,
                                      MuleArtifactLoaderDescriptor bundleDescriptorLoader) {
    checkArgument(classLoaderModelLoaderDescriptor != null, "classLoaderModelLoaderDescriptor cannot be null");
    checkArgument(bundleDescriptorLoader != null, "bundleDescriptorLoader cannot be null");
    checkArgument(minMuleVersion == null || new MuleVersion(minMuleVersion).getRevision() != NO_REVISION,
                  "descriptor minMuleVersion must have patch version specified");
    this.minMuleVersion = minMuleVersion;
    this.name = name;
    this.requiredProduct = requiredProduct;
    this.classLoaderModelLoaderDescriptor = classLoaderModelLoaderDescriptor;
    this.bundleDescriptorLoader = bundleDescriptorLoader;
  }

  public String getName() {
    return name;
  }

  public String getMinMuleVersion() {
    return minMuleVersion;
  }

  public Product getRequiredProduct() {
    return requiredProduct;
  }

  public MuleArtifactLoaderDescriptor getBundleDescriptorLoader() {
    return bundleDescriptorLoader;
  }

  public MuleArtifactLoaderDescriptor getClassLoaderModelLoaderDescriptor() {
    return classLoaderModelLoaderDescriptor;
  }

  /**
   * Validates that all the required fields for a valid model are set. It does not check whether or not the fields have valid
   * values, it just checks that they are not null.
   * <p/>
   * This method is useful for when constructing a model from a deserialized JSON and we want to check if the JSON was ok.
   */
  public void validateModel(String descriptorName) {
    validateMandatoryFieldIsSet(descriptorName, name, NAME);
    validateMandatoryFieldIsSet(descriptorName, requiredProduct, REQUIRED_PRODUCT,
                                format("Valid values are %s",
                                       join(", ", stream(Product.values()).map(Product::name).collect(toList()))));
    validateMandatoryFieldIsSet(descriptorName, minMuleVersion, MIN_MULE_VERSION);
    validateMandatoryFieldIsSet(descriptorName, classLoaderModelLoaderDescriptor, CLASS_LOADER_MODEL_LOADER_DESCRIPTOR);
    validateMandatoryFieldIsSet(descriptorName, classLoaderModelLoaderDescriptor.getId(),
                                CLASS_LOADER_MODEL_LOADER_DESCRIPTOR_ID);
    validateMandatoryFieldIsSet(descriptorName, bundleDescriptorLoader, BUNDLE_DESCRIPTOR_LOADER);
    validateMandatoryFieldIsSet(descriptorName, bundleDescriptorLoader.getId(), BUNDLE_DESCRIPTOR_LOADER_ID);
    doValidateCustomFields(descriptorName);
  }

  private void validateMandatoryFieldIsSet(String descriptorName, Object field, String fieldName, String extraErrorMessage) {
    checkState(field != null, format(MANDATORY_FIELD_MISSING_MESSAGE, descriptorName, fieldName, extraErrorMessage));
  }

  void validateMandatoryFieldIsSet(String descriptorName, Object field, String fieldName) {
    validateMandatoryFieldIsSet(descriptorName, field, fieldName, EMPTY);
  }

  void doValidateCustomFields(String descriptorName) {
    // Do nothing
  }
}
