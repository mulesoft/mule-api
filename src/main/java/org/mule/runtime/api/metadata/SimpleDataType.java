/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import java.nio.charset.Charset;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

/**
 * A data type that simply wraps a Java type.
 * <p>
 * This type also allows a mime type and encoding to be associated with the Java type.
 *
 * @since 1.0
 */
public class SimpleDataType<T> implements DataType<T>
{
    private static final long serialVersionUID = -4590745924720880358L;

    private static final String CHARSET_PARAM = "charset";

    protected final Class<T> type;
    protected String mimeType;
    protected String encoding;

    public SimpleDataType(Class<T> type, String mimeType, String encoding)
    {
        checkTypeForNull(type);
        this.type = type;
        setMimeType(mimeType);
        setEncoding(encoding);
    }

    public SimpleDataType(Class<T> type, String mimeType)
    {
        checkTypeForNull(type);
        this.type = type;
        setMimeType(mimeType);
    }

    protected void checkTypeForNull(Class type)
    {
        // TODO MULE-9895 validate this in the builder.
        if (type == null)
        {
            throw new IllegalArgumentException("'type' cannot be null.");
        }
    }

    public SimpleDataType(Class<T> type)
    {
        this(type, null);
    }

    @Override
    public Class<T> getType()
    {
        return type;
    }

    @Override
    public String getMimeType()
    {
        return mimeType;
    }

    private void setMimeType(String mimeType)
    {
        if (mimeType == null)
        {
            this.mimeType = ANY_MIME_TYPE;
        }
        else
        {
            try
            {
                MimeType mt = new MimeType(mimeType);
                this.mimeType = mt.getPrimaryType() + "/" + mt.getSubType();
                if (mt.getParameter(CHARSET_PARAM) != null)
                {
                    setEncoding(mt.getParameter(CHARSET_PARAM));
                }
            }
            catch (MimeTypeParseException e)
            {
                throw new IllegalArgumentException("MimeType cannot be parsed: " + mimeType);
            }
        }
    }

    @Override
    public String getEncoding()
    {
        return encoding;
    }

    private void setEncoding(String encoding)
    {
        if (encoding != null && encoding.trim().length() > 0)
        {
            // Checks that the encoding is valid and supported
            Charset.forName(encoding);
        }

        this.encoding = encoding;
    }

    @Override
    public boolean isCompatibleWith(DataType dataType)
    {
        if (this == dataType)
        {
            return true;
        }
        if (dataType == null)
        {
            return false;
        }

        SimpleDataType that = (SimpleDataType) dataType;

        //ANY_MIME_TYPE will match to a null or non-null value for MimeType        
        if ((this.getMimeType() == null && that.getMimeType() != null || that.getMimeType() == null && this.getMimeType() != null) && !ANY_MIME_TYPE.equals(this.mimeType) && !ANY_MIME_TYPE.equals(that.mimeType))
        {
            return false;
        }

        if (this.getMimeType() != null && !this.getMimeType().equals(that.getMimeType()) && !ANY_MIME_TYPE.equals(that.getMimeType()) && !ANY_MIME_TYPE.equals(this.getMimeType()))
        {
            return false;
        }

        if (!fromPrimitive(this.getType()).isAssignableFrom(fromPrimitive(that.getType())))
        {
            return false;
        }

        return true;
    }


    private Class<?> fromPrimitive(Class<?> type)
    {
        Class<?> primitiveWrapper = getPrimitiveWrapper(type);
        if (primitiveWrapper != null)
        {
            return primitiveWrapper;
        }
        else
        {
            return type;
        }
    }

    private Class<?> getPrimitiveWrapper(Class<?> primitiveType)
    {
        if (boolean.class.equals(primitiveType))
        {
            return Boolean.class;
        }
        else if (float.class.equals(primitiveType))
        {
            return Float.class;
        }
        else if (long.class.equals(primitiveType))
        {
            return Long.class;
        }
        else if (int.class.equals(primitiveType))
        {
            return Integer.class;
        }
        else if (short.class.equals(primitiveType))
        {
            return Short.class;
        }
        else if (byte.class.equals(primitiveType))
        {
            return Byte.class;
        }
        else if (double.class.equals(primitiveType))
        {
            return Double.class;
        }
        else if (char.class.equals(primitiveType))
        {
            return Character.class;
        }
        return null;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        SimpleDataType that = (SimpleDataType) o;

        if (!type.equals(that.type))
        {
            return false;
        }

        //ANY_MIME_TYPE will match to a null or non-null value for MimeType
        if ((this.mimeType == null && that.mimeType != null || that.mimeType == null && this.mimeType != null) && !ANY_MIME_TYPE.equals(that.mimeType))
        {
            return false;
        }

        if (this.mimeType != null && !mimeType.equals(that.mimeType) && !ANY_MIME_TYPE.equals(that.mimeType))
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = type.hashCode();
        result = 31 * result + (encoding != null ? encoding.hashCode() : 0);
        result = 31 * result + (mimeType != null ? mimeType.hashCode() : 0);
        return result;
    }


    @Override
    public String toString()
    {
        return "SimpleDataType{" +
               "type=" + (type == null ? null : type.getName()) +
               ", mimeType='" + mimeType + '\'' +
               ", encoding='" + encoding + '\'' +
               '}';
    }
}
