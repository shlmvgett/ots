package ru.otus.jdbc.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

  private final Map<K, V> cache = new WeakHashMap<>();
  private final List<HwListener<K, V>> listeners = new ArrayList<>();

  @Override
  public void put(K key, V value) {
    listen(key, value, "put");
    cache.put(key, value);
  }

  @Override
  public void remove(K key) {
    listen(key, cache.get(key), "remove");
    cache.remove(key);
  }

  @Override
  public V get(K key) {
    listen(key, cache.get(key), "get");
    return cache.get(key);
  }

  @Override
  public void addListener(HwListener<K, V> listener) {
    if (!this.listeners.contains(listener)) {
      this.listeners.add(listener);
    }
  }

  @Override
  public void removeListener(HwListener<K, V> listener) {
    this.listeners.remove(listener);
  }

  private void listen(K key, V value, String action) {
    listeners.forEach(l -> l.notify(key, value, action));
  }
}
