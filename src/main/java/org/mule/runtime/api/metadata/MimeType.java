/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import java.nio.charset.Charset;
import java.util.Objects;

/**
 * Common mime types used in Mule
 *
 * @since 1.0
 */
public final class MimeType
{
    public static final MimeType ANY = new MimeType("*", "*");

    public static final MimeType JSON = new MimeType("text", "json");
    public static final MimeType APPLICATION_JSON = new MimeType("application", "json");
    public static final MimeType ATOM = new MimeType("application", "atom+xml");
    public static final MimeType RSS = new MimeType("application", "rss+xml");
    public static final MimeType APPLICATION_XML = new MimeType("application", "xml");
    public static final MimeType XML = new MimeType("text", "xml");
    public static final MimeType TEXT = new MimeType("text", "plain");
    public static final MimeType HTML = new MimeType("text", "html");

    public static final MimeType BINARY = new MimeType("application", "octet-stream");
    public static final MimeType UNKNOWN = new MimeType("content", "unknown");
    public static final MimeType MULTIPART_MIXED = new MimeType("multipart", "mixed");
    public static final MimeType MULTIPART_RELATED = new MimeType("multipart", "related");
    public static final MimeType MULTIPART_X_MIXED_REPLACE = new MimeType("multipart", "x-mixed-replace");

    private final String primaryType;
    private final String subType;
    private final Charset encoding;

    public MimeType(String type, String subType, Charset encoding)
    {
        this.primaryType = type;
        this.subType = subType;
        this.encoding = encoding;
    }

    public MimeType(String type, String subType)
    {
        this.primaryType = type;
        this.subType = subType;
        this.encoding = null;
    }

    public String getPrimaryType()
    {
        return primaryType;
    }

    public String getSubType()
    {
        return subType;
    }

    /**
     * The encoding for the object to transform
     */
    public Charset getEncoding()
    {
        return encoding;
    }

    public boolean matches(MimeType other)
    {
        return Objects.equals(primaryType, other.primaryType)
               && Objects.equals(subType, other.subType);
    }

    @Override
    public String toString()
    {
        return primaryType + "/" + subType + (encoding != null ? "; charset=" + encoding.name() : "");
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(primaryType, subType, encoding);
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
        MimeType other = (MimeType) obj;

        return Objects.equals(primaryType, other.primaryType)
               && Objects.equals(subType, other.subType)
               && Objects.equals(encoding, other.encoding);
    }
}
