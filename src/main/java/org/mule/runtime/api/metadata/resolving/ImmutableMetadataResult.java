/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.resolving;

import org.mule.runtime.api.metadata.MetadataResolvingException;

import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Immutable concrete implementation of {@link MetadataResult}
 *
 * @since 1.0
 */
public final class ImmutableMetadataResult<T> implements MetadataResult<T>
{

    private final T result;
    private final boolean isSuccess;
    private final MetadataFailure failure;

    ImmutableMetadataResult(T result)
    {
        this.result = result;
        this.isSuccess = true;
        this.failure = null;
    }

    ImmutableMetadataResult(Exception e)
    {
        this(null, e);
    }

    ImmutableMetadataResult(T result, Exception e)
    {
        this(result, e.getMessage(), e);
    }

    ImmutableMetadataResult(T result, String message, Exception e)
    {
        this.result = result;
        this.isSuccess = false;
        this.failure = new MetadataFailure(message, ExceptionUtils.getStackTrace(e), getFailureCode(e));
    }

    ImmutableMetadataResult(T result, MetadataFailure failure)
    {
        this.result = result;
        this.isSuccess = false;
        this.failure = failure;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public T get()
    {
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSuccess()
    {
        return isSuccess;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MetadataFailure> getFailure()
    {
        return Optional.ofNullable(failure);
    }

    private FailureCode getFailureCode(Exception e)
    {
        return e instanceof MetadataResolvingException ? ((MetadataResolvingException) e).getFailure() : FailureCode.UNKNOWN;
    }
}
