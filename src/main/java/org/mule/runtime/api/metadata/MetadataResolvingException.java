/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.runtime.api.metadata.resolving.FailureCode;

/**
 * Exception that represents an error related to Metadata resolving.
 * A {@link FailureCode} is required in order to describe the error reason.
 * <p>
 * //TODO MULE-8946: Extend from MuleException
 *
 * @since 1.0
 */
public final class MetadataResolvingException extends Exception
{

    private final FailureCode failure;

    public MetadataResolvingException(String message, FailureCode failure)
    {
        super(message);
        this.failure = failure;
    }

    public MetadataResolvingException(String message, FailureCode failure, Throwable cause)
    {
        super(message, cause);
        this.failure = failure;
    }

    public FailureCode getFailure()
    {
        return failure;
    }
}
