package org.mule.runtime.api.map;

import java.util.Map;
import java.util.UUID;

public interface ObservableMap<K, V> extends Map<K, V> {


  UUID addEntryListener(ObjectStoreEntryListener listener);

  boolean removeEntryListener(String id);
}
