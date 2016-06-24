/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.util.Optional.ofNullable;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Immutable representation of Media Types as defined in
 * <a href="https://www.ietf.org/rfc/rfc2046.txt">RFC-2046 Part Two</a>.
 * <p>
 * Also provides constants for common media types used in Mule.
 *
 * @since 1.0
 */
public final class MediaType implements Serializable
{
    private static final long serialVersionUID = -3626429370741009489L;

    public static final MediaType ANY = new MediaType("*", "*");

    public static final MediaType JSON = new MediaType("text", "json");
    public static final MediaType APPLICATION_JSON = new MediaType("application", "json");
    public static final MediaType ATOM = new MediaType("application", "atom+xml");
    public static final MediaType RSS = new MediaType("application", "rss+xml");
    public static final MediaType APPLICATION_XML = new MediaType("application", "xml");
    public static final MediaType XML = new MediaType("text", "xml");
    public static final MediaType TEXT = new MediaType("text", "plain");
    public static final MediaType HTML = new MediaType("text", "html");

    public static final MediaType BINARY = new MediaType("application", "octet-stream");
    public static final MediaType UNKNOWN = new MediaType("content", "unknown");
    public static final MediaType MULTIPART_MIXED = new MediaType("multipart", "mixed");
    public static final MediaType MULTIPART_RELATED = new MediaType("multipart", "related");
    public static final MediaType MULTIPART_X_MIXED_REPLACE = new MediaType("multipart", "x-mixed-replace");

    private final String primaryType;
    private final String subType;
    private final String encodingStr;
    private volatile transient AtomicReference<Charset> encoding;

    /**
     * Builds a new media-type with the given parameters. This would be the equivalent of the media
     * type {@code "[primaryType]/[subType]; charset=[encoding]"}.
     * 
     * @param primaryType
     * @param subType
     * @param encoding
     */
    public MediaType(String primaryType, String subType, Charset encoding)
    {
        this.primaryType = primaryType;
        this.subType = subType;
        initEncoding();
        this.encoding.set(encoding);
        this.encodingStr = encoding != null ? encoding.name() : null;
    }

    /**
     * Builds a new media-type with the given parameters. This would be the equivalent of the media
     * type {@code "[primaryType]/[subType]"}.
     * 
     * @param primaryType
     * @param subType
     */
    public MediaType(String primaryType, String subType)
    {
        this(primaryType, subType, null);
    }

    /**
     * @return The left part of the represented type.
     */
    public String getPrimaryType()
    {
        return primaryType;
    }

    /**
     * @return The right part of the represented type.
     */
    public String getSubType()
    {
        return subType;
    }

    /**
     * @return The value of the {@code charset} parameter.
     *         <p>
     *         This may be not set, in which case it is up to the caller to determine the
     *         appropriate encoding to use.
     */
    public Optional<Charset> getEncoding()
    {
        initEncoding();
        if (encodingStr != null)
        {
            encoding.compareAndSet(null, Charset.forName(encodingStr));
        }
        return ofNullable(encoding.get());
    }

    private void initEncoding()
    {
        if (encoding == null)
        {
            synchronized (this)
            {
                if (encoding == null)
                {
                    encoding = new AtomicReference<>(null);
                }
            }
        }
    }

    /**
     * Evaluates the type of this object against the ones of the {@code other} {@link MediaType}.
     * <p>
     * This will ignore any optional parameters, such as the encoding.
     * 
     * @param other The {@link MediaType} to evaluate against.
     * @return {@code true} if the types are the same.
     */
    public boolean matches(MediaType other)
    {
        return Objects.equals(primaryType, other.primaryType)
               && Objects.equals(subType, other.subType);
    }

    /**
     * @return a textual representation of this object, according to the RFC.
     */
    @Override
    public String toString()
    {
        return primaryType + "/" + subType + (getEncoding().isPresent() ? "; charset=" + getEncoding().get().name() : "");
    }

    @Override
    public int hashCode()
    {
        // TODO MULE-9987 Check if it is actually needed to leave encoding out.
        return Objects.hash(primaryType, subType);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (obj == this)
        {
            return true;
        }
        if (obj.getClass() != getClass())
        {
            return false;
        }
        MediaType other = (MediaType) obj;

        return Objects.equals(primaryType, other.primaryType)
               && Objects.equals(subType, other.subType)
               && Objects.equals(getEncoding(), other.getEncoding());
    }
}
