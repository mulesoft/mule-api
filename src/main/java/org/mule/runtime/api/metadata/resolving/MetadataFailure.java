/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.resolving;

/**
 * Immutable representation of an error that occurred during Metadata resolving
 *
 * @since 1.0
 */
public final class MetadataFailure
{

    private final String message;
    private final String reason;
    private final FailureCode failureCode;


    public MetadataFailure(String message, String reason, FailureCode failureCode)
    {
        this.message = message;
        this.reason = reason;
        this.failureCode = failureCode;
    }

    /**
     * @return a custom message describing the context in which the failure occurred
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @return the {@link FailureCode} of the error that occurred
     */
    public FailureCode getFailureCode()
    {
        return failureCode;
    }

    /**
     * @return the original message of the error that produced this failure
     */
    public String getReason()
    {
        return reason;
    }

}
