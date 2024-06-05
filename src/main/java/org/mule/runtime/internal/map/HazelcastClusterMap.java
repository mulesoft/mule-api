package org.mule.runtime.internal.map;

import org.mule.runtime.api.map.EntryListener;
import org.mule.runtime.api.map.ObservableMap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.hazelcast.core.HazelcastException;
import com.hazelcast.map.IMap;
import com.hazelcast.splitbrainprotection.SplitBrainProtectionException;

public class HazelcastClusterMap<K, V> implements ObservableMap<K, V> {

  private final IMap<K, V> iMap;

  public HazelcastClusterMap(IMap<K, V> hazelcastInstanceMap) {
    iMap = hazelcastInstanceMap;
  }

  @Override
  public UUID addEntryListener(EntryListener<K, V> listener) {
    try {
      return iMap.addEntryListener(new EventListenerWrapper<>(listener), true);
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error adding entry listener");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error adding entry listener", e);
    }
  }

  @Override
  public boolean removeEntryListener(UUID id) {
    try {
      return iMap.removeEntryListener(id);
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error removing entry listener");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error removing entry listener", e);
    }
  }

  @Override
  public int size() {
    try {
      return iMap.size();
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error getting map size");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error getting map size", e);
    }
  }

  @Override
  public boolean isEmpty() {
    try {
      return iMap.isEmpty();
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error checking if map is empty");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error checking if map is empty", e);
    }
  }

  @Override
  public boolean containsKey(Object key) {
    try {
      return iMap.containsKey(key);
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error checking if map contains key");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error checking if map contains key", e);
    }
  }

  @Override
  public boolean containsValue(Object value) {
    try {
      return iMap.containsValue(value);
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error checking if map contains value");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error checking if map contains value", e);
    }
  }

  @Override
  public V get(Object key) {
    try {
      return iMap.get(key);
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error getting a value from a map");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error getting a value from a map", e);
    }
  }

  @Override
  public V put(K key, V value) {
    try {
      return iMap.put(key, value);
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error putting an entry in a map");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error putting an entry in a map", e);
    }
  }

  @Override
  public V remove(Object key) {
    try {
      return iMap.remove(key);
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error removing an entry from a map");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error removing an entry from a map", e);
    }
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    try {
      iMap.putAll(m);
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error putting in a map");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error putting in a map", e);
    }
  }

  @Override
  public void clear() {
    try {
      iMap.clear();
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error clearing a map");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error clearing a map", e);
    }
  }

  @Override
  public Set<K> keySet() {
    try {
      return iMap.keySet();
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error getting the key set from a map");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error getting the key set from a map", e);
    }
  }

  @Override
  public Collection<V> values() {
    try {
      return iMap.values();
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error getting the values from a map");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error getting the values from a map", e);
    }
  }

  @Override
  public Set<Map.Entry<K, V>> entrySet() {
    try {
      return iMap.entrySet();
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error getting the entry set from a map");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error getting the entry set from a map", e);
    }
  }

  @Override
  public V putIfAbsent(K key, V value) {
    try {
      return iMap.putIfAbsent(key, value);
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error on putIfAbsent");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error on putIfAbsent", e);
    }
  }

  @Override
  public boolean remove(Object key, Object value) {
    try {
      return iMap.remove(key, value);
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error on remove");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error on remove", e);
    }
  }

  @Override
  public boolean replace(K key, V oldValue, V newValue) {
    try {
      return iMap.replace(key, oldValue, newValue);
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error on replace");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error on replace", e);
    }
  }

  @Override
  public V replace(K key, V value) {
    try {
      return iMap.replace(key, value);
    } catch (SplitBrainProtectionException e) {
      throw new SplitBrainProtectionException("Error on replace");
    } catch (HazelcastException e) {
      throw new HazelcastException("Error on replace", e);
    }
  }

}
