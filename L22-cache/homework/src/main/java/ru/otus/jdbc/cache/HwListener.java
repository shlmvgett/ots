package ru.otus.jdbc.cache;

public interface HwListener<K, V> {

  void notify(K key, V value, String action);
}
