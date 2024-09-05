/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static org.mule.runtime.api.metadata.AbstractDataTypeBuilderFactory.getDefaultFactory;
import static org.mule.runtime.api.metadata.MediaType.ANY;
import static org.mule.runtime.api.util.classloader.MuleImplementationLoaderUtils.getMuleImplementationsLoader;
import static org.mule.runtime.api.util.classloader.MuleImplementationLoaderUtils.isResolveMuleImplementationLoadersDynamically;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.el.ExpressionFunction;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.streaming.bytes.CursorStreamProvider;
import org.mule.runtime.api.streaming.object.CursorIteratorProvider;
import org.mule.runtime.api.util.MultiMap;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Defines a Java type and its association with additional information about the data, like MIME type and encoding.
 * <p>
 * The java type may be a {@link Collection}, in which case implementations should provide additional information about the item
 * type.
 * <p>
 * This also provides constants for some commonly used {@link DataType}s.
 *
 * @since 1.0
 */
@NoImplement
public interface DataType extends Serializable {

  /**
   * Provides a builder to create {@link DataType} objects.
   *
   * @return a new {@link DataTypeBuilder}.
   */
  static DataTypeBuilder builder() {
    return getDefaultFactory().create();
  }

  /**
   * Provides a builder to create {@link DataType} objects based on an existing {@link DataType} instance.
   *
   * @param dataType existing {@link DataType} to use as a template to create a new {@link DataTypeBuilder} instance.
   * @return a new {@link DataTypeBuilder} based on the template {@code dataType} provided.
   */
  static DataTypeBuilder builder(DataType dataType) {
    return getDefaultFactory().create(dataType);
  }

  /**
   * Shortcut to create a {@link DataType} using just a Java type. Default values will be used for {@code mimeType} and
   * {@code encoding}.
   *
   * @param type the Java type to create {@link DataType} for.
   * @return a new {@link DataTypeBuilder} for the given {@code type}.
   */
  static DataType fromType(Class<?> type) {
    return builder().type(type).build();
  }

  /**
   * Shortcut to create the {@link DataType} from an Object instance.
   * <p>
   * This behaves in the same way as {@link #fromType(Class)} creating a {@link DataType} based on the value type with default
   * values being used for {@code mimeType} and {@code encoding} if the Object type has no mimeType or encoding. The
   * {@link DataTypeBuilder} used by default may introspect certain types that do contain type this meta-data such as
   * {@link javax.activation.DataHandler} and {@link javax.activation.DataSource} and populate {@code mimeType} and
   * {@code encoding} values based on this.
   *
   * @param value the object to determine the {@link DataType} of.
   * @return a new {@link DataType} for the given {@code value}.
   */
  static DataType fromObject(Object value) {
    return getDefaultFactory().create().fromObject(value).build();
  }

  /**
   * Shortcut to create the {@link DataType} from an {@link ExpressionFunction} instance.
   * <p>
   * This behaves in the same way as {@link #fromObject(Object)} but automatically populates the {@code returnType} and
   * {@code parameters} based on the function.
   *
   * @param function the function to determine the {@link DataType} of.
   * @return a new {@link DataType} for the given {@code value}.
   */
  static DataType fromFunction(ExpressionFunction function) {
    return getDefaultFactory().create().fromFunction(function).build();
  }

  /**
   * Static method to evaluate if the {@param superType} is compatible with the {@param subType}.
   * <p/>
   * This means that the {@param superType} mime type should match the one in the given {@param subType} and the
   * {@param superType} JAVA class should be assignable from the {@param subType} one.
   *
   * @param superType
   * @param subType
   * @return a boolean indicating whether or not the {@param superType} is compatible with the {@param subType}
   */
  static boolean areCompatible(DataType superType, DataType subType) {
    return superType.isCompatibleWith(subType);
  }

  /**
   * {@link DataType} implementation that dynamically determines what implementation to use. In case
   * {@link org.mule.runtime.api.util.MuleSystemProperties.RESOLVE_MULE_IMPLEMENTATIONS_LOADER_DYNAMICALLY} is set, an
   * implementation for every implementation loader will be available.
   *
   * @since 1.8
   */
  class DynamicDelegateDataType implements DataType {

    private static final long serialVersionUID = -4590745924767485440L;

    private transient final Supplier<DataType> delegateSupplier;
    private final DataType delegate;
    private transient final Map<ClassLoader, DataType> cache = new ConcurrentHashMap<>();

    public DynamicDelegateDataType(Supplier<DataType> delegateSupplier) {
      this.delegateSupplier = delegateSupplier;
      this.delegate = delegateSupplier.get();
    }

    public DataType getDelegate() {
      if (isResolveMuleImplementationLoadersDynamically()) {
        return cache.computeIfAbsent(getMuleImplementationsLoader(), classLoader -> delegateSupplier.get());
      } else {
        return delegate;
      }
    }

    @Override
    public Class<?> getType() {
      return getDelegate().getType();
    }

    @Override
    public MediaType getMediaType() {
      return getDelegate().getMediaType();
    }

    @Override
    public boolean isCompatibleWith(DataType dataType) {
      return getDelegate().isCompatibleWith(dataType);
    }

    @Override
    public boolean isStreamType() {
      return getDelegate().isStreamType();
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof DataType)) {
        return false;
      }

      DataType that = o instanceof DynamicDelegateDataType ? ((DynamicDelegateDataType) o).getDelegate() : (DataType) o;

      return this.getDelegate().equals(that);
    }

  }

  /**
   * {@link DynamicDelegateDataType} implementation that implements {@link CollectionDataType}.
   *
   * @since 1.8
   */
  class DynamicCollectionDelegateDataType extends DynamicDelegateDataType implements CollectionDataType {

    public DynamicCollectionDelegateDataType(Supplier<DataType> delegateSupplier) {
      super(delegateSupplier);
    }

    @Override
    public DataType getItemDataType() {
      return ((CollectionDataType) getDelegate()).getItemDataType();
    }

  }

  /**
   * {@link DynamicDelegateDataType} implementation that implements {@link MapDataType}.
   *
   * @since 1.8
   */
  class DynamicMapDelegateDataType extends DynamicDelegateDataType implements MapDataType {

    public DynamicMapDelegateDataType(Supplier<DataType> delegateSupplier) {
      super(delegateSupplier);
    }

    @Override
    public DataType getKeyDataType() {
      return ((MapDataType) getDelegate()).getKeyDataType();
    }

    @Override
    public DataType getValueDataType() {
      return ((MapDataType) getDelegate()).getValueDataType();
    }

  }

  DataType TEXT_STRING = new DynamicDelegateDataType(() -> builder().type(String.class).mediaType(MediaType.TEXT).build());

  DataType XML_STRING = new DynamicDelegateDataType(() -> builder().type(String.class).mediaType(MediaType.XML).build());

  DataType JSON_STRING =
      new DynamicDelegateDataType(() -> builder().type(String.class).mediaType(MediaType.APPLICATION_JSON).build());

  DataType HTML_STRING = new DynamicDelegateDataType(() -> builder().type(String.class).mediaType(MediaType.HTML).build());

  DataType ATOM_STRING = new DynamicDelegateDataType(() -> builder().type(String.class).mediaType(MediaType.ATOM).build());

  DataType RSS_STRING = new DynamicDelegateDataType(() -> builder().type(String.class).mediaType(MediaType.RSS).build());

  // Common Java types
  DataType STRING = new DynamicDelegateDataType(() -> fromType(String.class));

  DataType NUMBER = new DynamicDelegateDataType(() -> fromType(Number.class));

  DataType BOOLEAN = new DynamicDelegateDataType(() -> fromType(Boolean.class));

  DataType OBJECT = new DynamicDelegateDataType(() -> fromType(Object.class));

  DataType BYTE_ARRAY = new DynamicDelegateDataType(() -> fromType(byte[].class));

  DataType INPUT_STREAM = new DynamicDelegateDataType(() -> fromType(InputStream.class));

  DataType ITERATOR = new DynamicDelegateDataType(() -> fromType(Iterator.class));

  DataType CURSOR_STREAM_PROVIDER = new DynamicDelegateDataType(() -> fromType(CursorStreamProvider.class));

  DataType CURSOR_ITERATOR_PROVIDER = new DynamicDelegateDataType(() -> fromType(CursorIteratorProvider.class));

  DataType TYPED_VALUE = new DynamicDelegateDataType(() -> fromType(TypedValue.class));

  DataType MULE_MESSAGE = new DynamicDelegateDataType(() -> builder().type(Message.class).mediaType(ANY).build());

  CollectionDataType MULE_MESSAGE_COLLECTION = new DynamicCollectionDelegateDataType(() -> getDefaultFactory().create()
      .collectionType(Collection.class).itemType(Message.class).mediaType(ANY).build());
  CollectionDataType MULE_MESSAGE_LIST =
      new DynamicCollectionDelegateDataType(() -> getDefaultFactory().create().collectionType(List.class)
          .itemType(Message.class).mediaType(ANY).build());
  MapDataType MULE_MESSAGE_MAP =
      new DynamicMapDelegateDataType(() -> getDefaultFactory().create().mapType(Map.class).keyType(String.class)
          .valueType(Message.class).valueMediaType(ANY).build());
  MapDataType MULTI_MAP_STRING_STRING = new DynamicMapDelegateDataType(() -> getDefaultFactory().create().mapType(MultiMap.class)
      .keyType(String.class).valueType(String.class).build());

  /**
   * The object type of the source object to transform.
   *
   * @return the class object of the source object. This must not be null
   */
  Class<?> getType();

  /**
   * The mime type of the the source object to transform.
   *
   * @return the mime type of the source object.
   */
  MediaType getMediaType();

  /**
   * Used to determine if this data type is compatible with the data type passed in. This checks to see if this mime types matches
   * the one in the given dataType and whether the Java types are assignable.
   * <p/>
   * Keep in mind that if this mime type is {@link MediaType#ANY} it will match any mime type {@param dataType} could have.
   * <p>
   * This method is NOT <i>symmetric</i>. That is, {@code a.isCompatibleWith(b)} and {@code b.isCompatibleWith(a)} may yield
   * different result.
   *
   * @param dataType the dataType object to compare with
   * @return true if the mime types are the same and this type can be assigned to the dataType.type.
   */
  boolean isCompatibleWith(DataType dataType);

  /**
   * Used to determine if this DataType is a stream type. Stream types, for example those that have a java type
   * {@link InputStream} or {@link Iterable}, cannot be serialized without prior transformation or consumed twice and often their
   * size, depending on the specific type, is unknown until they have been fully consumed.
   *
   * @return {@code true} if the type is a stream type otherwise {@code false}
   */
  boolean isStreamType();

}
