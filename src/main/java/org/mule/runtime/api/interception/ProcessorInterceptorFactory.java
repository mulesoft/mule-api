/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.interception;

import org.mule.runtime.api.component.location.ComponentLocation;

import java.util.function.Supplier;

/**
 * Abstract Factory for creating {@link ProcessorInterceptor} instances.
 * <p>
 * Implementations may have fields annotated with {@link javax.inject.Inject @Inject}, which will be resolved before attempting to
 * call {@link #get()}.
 */
public interface ProcessorInterceptorFactory extends Supplier<ProcessorInterceptor> {

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

}
