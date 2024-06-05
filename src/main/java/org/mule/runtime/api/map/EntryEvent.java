package org.mule.runtime.api.map;


import org.mule.api.annotation.NoImplement;

/**
 * Object passed to the {@link EntryListener}s with contextual data about the event.
 *
 * @param <K> the map keys type.
 * @param <V> the map values type.
 * @since 4.5.0
 */
@NoImplement
public interface EntryEvent<K, V> {

  /**
   * @return the modified entry's key.
   */
  K getKey();

  /**
   * @return the modified entry's value.
   */
  V getValue();

  /**
   * @return the address of the member that triggered the event, as a String.
   */
  String getAddressAsString();

  /**
   * @return {@code true} if the event was fired by the local member, or {@code false} otherwise.
   */
  boolean isLocalMember();
}

