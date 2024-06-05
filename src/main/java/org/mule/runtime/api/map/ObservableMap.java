package org.mule.runtime.api.map;

import org.mule.api.annotation.NoImplement;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * Concurrent, distributed and observable map.
 *
 * @param <K> the keys type.
 * @param <V> the values type.
 * @since 4.5.0
 */
@NoImplement
public interface ObservableMap<K, V> extends ConcurrentMap<K, V> {

  /**
   * Adds a {@link EntryListener} for this map.
   * <p>
   * To receive an event, you should implement a corresponding {@link EntryListener} sub-interface for that event.
   *
   * @param listener {@link EntryListener} for this map
   * @return a String which can be used as a key to remove the listener.
   * @throws NullPointerException if the specified listener is {@code null}
   * @see EntryListener
   */
  UUID addEntryListener(EntryListener<K, V> listener);

  /**
   * Removes the specified entry listener.
   * <p>
   * Returns silently if there is no such listener added before.
   *
   * @param id ID of registered listener
   * @return true if registration is removed, false otherwise
   */
  boolean removeEntryListener(UUID id);
}
