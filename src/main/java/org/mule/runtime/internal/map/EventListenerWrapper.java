package org.mule.runtime.internal.map;

import org.mule.runtime.api.map.EntryEvent;
import org.mule.runtime.api.map.EntryListener;

import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryRemovedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;

class EventListenerWrapper<K, V> implements EntryAddedListener<K, V>, EntryUpdatedListener<K, V>, EntryRemovedListener<K, V> {

  private final EntryListener<K, V> delegate;

  EventListenerWrapper(EntryListener<K, V> delegate) {
    this.delegate = delegate;
  }

  @Override
  public void entryAdded(com.hazelcast.core.EntryEvent<K, V> event) {
    delegate.entryAdded(wrapEvent(event));
  }

  @Override
  public void entryRemoved(com.hazelcast.core.EntryEvent<K, V> event) {
    delegate.entryRemoved(wrapEvent(event));
  }

  @Override
  public void entryUpdated(com.hazelcast.core.EntryEvent<K, V> event) {
    delegate.entryUpdated(wrapEvent(event));
  }

  private static <T, U> EntryEvent<T, U> wrapEvent(com.hazelcast.core.EntryEvent<T, U> hzEvent) {
    return new EventEntryWrapper<>(hzEvent);
  }
}

