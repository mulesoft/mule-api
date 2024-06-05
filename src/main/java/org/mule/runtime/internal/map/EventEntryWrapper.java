package org.mule.runtime.internal.map;

import org.mule.runtime.api.map.EntryEvent;

/**
 * Wrapper of {@link com.hazelcast.core.EntryEvent}, implementing our {@link EntryEvent}.
 *
 * @param <K> the keys type.
 * @param <V> the values type.
 */
public class EventEntryWrapper<K, V> implements EntryEvent<K, V> {

  private final com.hazelcast.core.EntryEvent<K, V> hzEvent;

  public EventEntryWrapper(com.hazelcast.core.EntryEvent<K, V> hzEvent) {
    this.hzEvent = hzEvent;
  }

  @Override
  public K getKey() {
    return hzEvent.getKey();
  }

  @Override
  public V getValue() {
    return hzEvent.getValue();
  }

  @Override
  public String getAddressAsString() {
    return hzEvent.getMember().getAddress().toString();
  }

  @Override
  public boolean isLocalMember() {
    return hzEvent.getMember().localMember();
  }
}
