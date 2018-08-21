package com.awin.recruitment.library;

public interface StreamWriter<K, V> {
    void send(K key, V value);
}
