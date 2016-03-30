/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.metadata.resolving;

import org.mule.api.metadata.MetadataResolvingException;

import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Immutable concrete implementation of {@link MetadataResult}
 *
 * @since 1.0
 */
public final class ImmutableMetadataResult<T> implements MetadataResult<T>
{

    private final T payload;
    private final boolean isSucess;
    private final MetadataFailure failure;

    ImmutableMetadataResult(T payload)
    {
        this.payload = payload;
        this.isSucess = true;
        this.failure = null;
    }

    ImmutableMetadataResult(Exception e)
    {
        this(null, e);
    }

    ImmutableMetadataResult(T payload, Exception e)
    {
        this(payload, e.getMessage(), e);
    }

    ImmutableMetadataResult(T payload, String message, Exception e)
    {
        this.payload = payload;
        this.isSucess = false;
        this.failure = new MetadataFailure(message, ExceptionUtils.getStackTrace(e), getFailureCode(e));
    }

    ImmutableMetadataResult(T payload, MetadataFailure failure)
    {
        this.payload = payload;
        this.isSucess = false;
        this.failure = failure;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get()
    {
        return payload;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSuccess()
    {
        return isSucess;
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
