package org.mule.runtime.api.map;

import org.mule.api.annotation.NoImplement;

/**
 * Listener that will be invoked when an event occurs on a map entry.
 *
 * @see EntryEvent
 * @param <K> the map keys type.
 * @param <V> the map values type.
 * @since 4.5.0
 */
@NoImplement
public interface EntryListener<K, V> {

  /**
   * Callback to invoke when an entry is added to the map.
   *
   * @param event {@link EntryEvent} with event's contextual data.
   */
  void entryAdded(EntryEvent<K, V> event);

  /**
   * Callback to invoke when an entry is removed from the map.
   *
   * @param event {@link EntryEvent} with event's contextual data.
   */
  void entryRemoved(EntryEvent<K, V> event);

  /**
   * Callback to invoke when an entry from the map is modified.
   *
   * @param event {@link EntryEvent} with event's contextual data.
   */
  void entryUpdated(EntryEvent<K, V> event);
}
