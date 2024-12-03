package org.example;

import java.util.*;

public class MyHashMap<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>> {
    private static final float MAX_LOAD = 0.7f;
    private static final int INIT_CAPACITY = 8;
    private Entry<K, V>[] table;
    private int size;
    private int capacity;

    private static class Entry<K, V> implements Map.Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldVal = this.value;
            this.value = value;
            return oldVal;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }

        @Override
        public boolean equals(Object o){
            if(this == o) return true;
            if(!(o instanceof Entry)) return false;
            Entry<?, ?> e = (Entry<?, ?>) o;
            return Objects.equals(key, e.key) && Objects.equals(value, e.value);
        }

        @Override
        public int hashCode(){
            return Objects.hash(key, value);
        }
    }

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        this.capacity = INIT_CAPACITY;
        table = new Entry[capacity];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private int hash(K key) {
        return Math.abs(Objects.hashCode(key)) % capacity;
    }

    private float loadFactor() {
        return (float) size / capacity;
    }

    private void resize() {
        capacity *= 2;
        Entry<K, V>[] oldTable = table;
        table = new Entry[capacity];
        size = 0; // Сброс size — важно для правильного расчета коэффициента заполнения

        for (Entry<K, V> entry : oldTable) {
            while (entry != null) {
                put(entry.key, entry.value); // Повторное использование метода put для перехеширования
                entry = entry.next;
            }
        }
    }

    public V putIfAbsent(K key, V value) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        if (loadFactor() >= MAX_LOAD) resize();
        int index = hash(key);
        for (Entry<K,V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key))
                return e.value;
        }
        table[index] = new Entry<>(key, value);
        size++;
        return null;
    }

    public V put(K key, V value) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        if (loadFactor() >= MAX_LOAD) resize();
        int i = hash(key);
        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        table[i] = new Entry<>(key, value);
        size++;
        return null;
    }

    public void clear() {
        size = 0;
        Arrays.fill(table, null); //More efficient than creating a new array.
    }

    public V remove(Object key) {
        int index = hash((K) key);
        Entry<K, V> prev = null;
        for (Entry<K, V> e = table[index]; e != null; prev = e, e = e.next) {
            if (e.key.equals(key)) {
                if (prev == null)
                    table[index] = e.next;
                else
                    prev.next = e.next;
                size--;
                return e.value;
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public V get(Object key) {
        int index = hash((K) key);
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key))
                return e.value;
        }
        return null;
    }

    public boolean containsKey(Object key) {
        int index = hash((K) key);
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key))
                return true;
        }
        return false;
    }

    public boolean containsValue(Object value) {
        for (Entry<K, V> entry : table) {
            for (Entry<K, V> e = entry; e != null; e = e.next) {
                if (Objects.equals(e.value, value))
                    return true;
            }
        }
        return false;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for (Entry<K, V> entry : table) {
            for(Entry<K,V> e = entry; e != null; e = e.next) {
                set.add(e);
            }
        }
        return set;
    }

    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (Entry<K, V> entry : table) {
            for(Entry<K,V> e = entry; e != null; e = e.next) {
                set.add(e.key);
            }
        }
        return set;
    }

    public Collection<V> values() {
        List<V> list = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            for(Entry<K,V> e = entry; e != null; e = e.next) {
                list.add(e.value);
            }
        }
        return list;
    }


    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<Map.Entry<K, V>> {
        private int currentIndex = 0;
        private Entry<K, V> currentEntry;

        public MyHashMapIterator() {
            findNextEntry();
        }

        private void findNextEntry() {
            while (currentIndex < capacity && table[currentIndex] == null) {
                currentIndex++;
            }
            currentEntry = (currentIndex < capacity) ? table[currentIndex] : null;
        }


        @Override
        public boolean hasNext() {
            return currentEntry != null;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Map.Entry<K, V> result = currentEntry;
            currentEntry = currentEntry.next;
            if (currentEntry == null) {
                currentIndex++;
                findNextEntry();
            }
            return result;
        }
    }
}
