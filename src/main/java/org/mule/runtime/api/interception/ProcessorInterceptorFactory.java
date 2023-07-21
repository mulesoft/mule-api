/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.interception;

import org.mule.runtime.api.artifact.Registry;
import org.mule.runtime.api.component.location.ComponentLocation;

import java.util.List;
import java.util.function.Supplier;

/**
 * Abstract Factory for creating {@link ProcessorInterceptor} instances.
 * <p>
 * Implementations may have fields annotated with {@link javax.inject.Inject @Inject}, which will be resolved before attempting to
 * call {@link #get()}.
 */
public interface ProcessorInterceptorFactory extends Supplier<ProcessorInterceptor> {

  /**
   * By making a {@link ProcessorInterceptorOrder} available in the {@link Registry} with this key, the order in which the
   * {@link ProcessorInterceptorFactory ProcessorInterceptorFactories} products will be applied to the applicable components. can
   * be customized.
   * <p>
   * For each {@link ProcessorInterceptorFactory factory}, its fully qualified class name will be obtained and matched against the
   * passed {@code packagesOrder} to sort the factories. In the case there is more than one {@link ProcessorInterceptorFactory
   * factory} with a package name prefix, the order in which they were {@link #addInterceptorFactory(ProcessorInterceptorFactory)
   * added} will be kept.
   * <p>
   * Assuming this is called with parameters {@code ("org.package", "com.plugin")}, and the following
   * {@link ProcessorInterceptorFactory factories} have been added through
   * {@link #addInterceptorFactory(ProcessorInterceptorFactory)} (in this order):
   * <ol>
   * <li>{@code com.plugin.SomeInterceptor}</li>
   * <li>{@code org.mule.MuleInterceptor}</li>
   * <li>{@code org.package.logging.LoggerInterceptor}</li>
   * <li>{@code com.plugin.SomeOtherInterceptor}</li>
   * <li>{@code org.mule.OtherMuleInterceptor}</li>
   * </ol>
   * Those {@link ProcessorInterceptorFactory factories} will be sorted, when obtained through {@link #getInterceptorFactories()}
   * like this:
   * <ol>
   * <li>{@code org.package.logging.LoggerInterceptor}</li>
   * <li>{@code com.plugin.SomeInterceptor}</li>
   * <li>{@code com.plugin.SomeOtherInterceptor}</li>
   * <li>{@code org.mule.MuleInterceptor}</li>
   * <li>{@code org.mule.OtherMuleInterceptor}</li>
   * </ol>
   */
  public static final String INTERCEPTORS_ORDER_REGISTRY_KEY = "_muleProcessorInterceptorFactoryOrder";

  /**
   * Determines if a {@link ProcessorInterceptor} shall be created by this factory to be applied to a component based on some of
   * its attributes.
   *
   * @param location the location and identification properties of the to-be intercepted component in the mule app configuration.
   * @return {@code true} if this handler must be applied to the component with the provided parameters, {@code false} otherwise.
   */
  default boolean intercept(ComponentLocation location) {
    return true;
  }

  interface ProcessorInterceptorOrder extends Supplier<List<String>> {

  }

}
