/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.interception;

import org.mule.runtime.api.artifact.Registry;

import java.util.List;
import java.util.function.Supplier;

/**
 * Abstract Factory for creating {@link FlowInterceptor} instances.
 * <p>
 * Implementations may have fields annotated with {@link javax.inject.Inject @Inject}, which will be resolved before attempting to
 * call {@link #get()}.
 *
 * @since 1.4
 */
public interface FlowInterceptorFactory extends Supplier<FlowInterceptor> {

  /**
   * By making a {@link FlowInterceptorOrder} available in the {@link Registry} with this key, the order in which the
   * {@link FlowInterceptorFactory FlowInterceptorFactories} products will be applied to the applicable components. can be
   * customized.
   * <p>
   * For each {@link FlowInterceptorFactory factory}, its fully qualified class name will be obtained and matched against the
   * passed {@code packagesOrder} to sort the factories. In the case there is more than one {@link FlowInterceptorFactory factory}
   * with a package name prefix, the order in which they were {@link #addInterceptorFactory(FlowInterceptorFactory) added} will be
   * kept.
   * <p>
   * Assuming this is called with parameters {@code ("org.package", "com.plugin")}, and the following
   * {@link FlowInterceptorFactory factories} have been added through {@link #addInterceptorFactory(FlowInterceptorFactory)} (in
   * this order):
   * <ol>
   * <li>{@code com.plugin.SomeInterceptor}</li>
   * <li>{@code org.mule.MuleInterceptor}</li>
   * <li>{@code org.package.logging.LoggerInterceptor}</li>
   * <li>{@code com.plugin.SomeOtherInterceptor}</li>
   * <li>{@code org.mule.OtherMuleInterceptor}</li>
   * </ol>
   * Those {@link FlowInterceptorFactory factories} will be sorted, when obtained through {@link #getInterceptorFactories()} like
   * this:
   * <ol>
   * <li>{@code org.package.logging.LoggerInterceptor}</li>
   * <li>{@code com.plugin.SomeInterceptor}</li>
   * <li>{@code com.plugin.SomeOtherInterceptor}</li>
   * <li>{@code org.mule.MuleInterceptor}</li>
   * <li>{@code org.mule.OtherMuleInterceptor}</li>
   * </ol>
   */
  public static final String FLOW_INTERCEPTORS_ORDER_REGISTRY_KEY = "_muleFlowInterceptorFactoryOrder";

  /**
   * Determines if a {@link FlowInterceptor} shall be created by this factory to be applied to a flow based on its name.
   *
   * @param flowName the name of the to-be intercepted flow.
   * @return {@code true} if this handler must be applied to the flow with the provided name, {@code false} otherwise.
   */
  default boolean intercept(String flowName) {
    return true;
  }

  interface FlowInterceptorOrder extends Supplier<List<String>> {

  }

}
