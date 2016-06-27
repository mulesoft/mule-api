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

    private static final String TYPE_TEXT = "text";
    private static final String TYPE_APPLICATION = "application";
    private static final String TYPE_MULTIPART = "multipart";
    private static final String SUBTYPE_JSON = "json";
    private static final String SUBTYPE_XML = "xml";
    private static final String SUBTYPE_PLAIN = "plain";
    private static final String SUBTYPE_HTML = "html";
    private static final String SUBTYPE_OCTET_STREAM = "octet-stream";
    private static final String SUBTYPE_MIXED = "mixed";
    private static final String SUBTYPE_RELATED = "related";

    public static final MediaType ANY = build("*", "*");

    public static final MediaType JSON = build(TYPE_TEXT, SUBTYPE_JSON);
    public static final MediaType APPLICATION_JSON = build(TYPE_APPLICATION, SUBTYPE_JSON);
    public static final MediaType ATOM = build(TYPE_APPLICATION, "atom+" + SUBTYPE_XML);
    public static final MediaType RSS = build(TYPE_APPLICATION, "rss+" + SUBTYPE_XML);
    public static final MediaType APPLICATION_XML = build(TYPE_APPLICATION, SUBTYPE_XML);
    public static final MediaType XML = build(TYPE_TEXT, SUBTYPE_XML);
    public static final MediaType TEXT = build(TYPE_TEXT, SUBTYPE_PLAIN);
    public static final MediaType HTML = build(TYPE_TEXT, SUBTYPE_HTML);

    public static final MediaType BINARY = build(TYPE_APPLICATION, SUBTYPE_OCTET_STREAM);
    public static final MediaType UNKNOWN = build("content", "unknown");
    public static final MediaType MULTIPART_MIXED = build(TYPE_MULTIPART, SUBTYPE_MIXED);
    public static final MediaType MULTIPART_RELATED = build(TYPE_MULTIPART, SUBTYPE_RELATED);
    public static final MediaType MULTIPART_X_MIXED_REPLACE = build(TYPE_MULTIPART, "x-" + SUBTYPE_MIXED + "-replace");

    private final String primaryType;
    private final String subType;
    private final String charsetStr;
    private volatile transient AtomicReference<Charset> charset;

    /**
     * Builds a new media-type with the given parameters. This would be the equivalent of the media
     * type {@code "[primaryType]/[subType]; charset=[charset]"}.
     * 
     * @param primaryType
     * @param subType
     * @param charset
     */
    public static MediaType build(String primaryType, String subType, Charset charset)
    {
        return new MediaType(primaryType, subType, charset);
    }

    /**
     * Builds a new media-type with the given parameters. This would be the equivalent of the media
     * type {@code "[primaryType]/[subType]"}.
     * 
     * @param primaryType
     * @param subType
     */
    public static MediaType build(String primaryType, String subType)
    {
        return new MediaType(primaryType, subType, null);
    }
    
    private MediaType(String primaryType, String subType, Charset charset)
    {
        this.primaryType = primaryType;
        this.subType = subType;
        initCharset();
        this.charset.set(charset);
        this.charsetStr = charset != null ? charset.name() : null;
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
     *         appropriate charset to use.
     */
    public Optional<Charset> getCharset()
    {
        initCharset();
        if (charsetStr != null)
        {
            charset.compareAndSet(null, Charset.forName(charsetStr));
        }
        return ofNullable(charset.get());
    }

    private void initCharset()
    {
        if (charset == null)
        {
            synchronized (this)
            {
                if (charset == null)
                {
                    charset = new AtomicReference<>(null);
                }
            }
        }
    }

    /**
     * Evaluates the type of this object against the ones of the {@code other} {@link MediaType}.
     * <p>
     * This will ignore any optional parameters, such as the charset.
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
        return primaryType + "/" + subType + (getCharset().isPresent() ? "; charset=" + getCharset().get().name() : "");
    }

    @Override
    public int hashCode()
    {
        // TODO MULE-9987 Check if it is actually needed to leave charset out.
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
               && Objects.equals(getCharset(), other.getCharset());
    }
}
