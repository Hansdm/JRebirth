package org.jrebirth.af.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jrebirth.af.api.concurrent.RunnablePriority;

public class MultiMap<K, V> implements Map<K, List<V>> {

    private final Map<K, List<V>> map = new HashMap<>();

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        boolean res = false;
        for (final List<V> list : this.map.values()) {
            if (list.contains(value)) {
                res = true;
                break;
            }
        }
        return res;
    }

    @Override
    public List<V> get(final Object key) {
        return this.map.get(key);
    }

    public boolean add(final K key, final V value) {
        if (!this.map.containsKey(key)) {
            this.map.put(key, new ArrayList<V>());
        }
        return this.map.get(key).add(value);
    }

    @Override
    public List<V> put(final K key, final List<V> value) {
        return this.map.put(key, value);
    }

    @Override
    public List<V> remove(final Object key) {
        return this.map.remove(key);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<List<V>> values() {
        return this.map.values();
    }

    @Override
    public Set<java.util.Map.Entry<K, List<V>>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public void putAll(final Map<? extends K, ? extends List<V>> m) {
        this.map.putAll(m);
    }

    public class Entry<V> {

        private final String id;
        private final String description;
        private final RunnablePriority priority;
        private final V value;

        public Entry(final String id, final String description, final RunnablePriority priority, final V value) {
            super();
            this.id = id;
            this.description = description;
            this.priority = priority;
            this.value = value;
        }

        /**
         * @return the id
         */
        public String getId() {
            return this.id;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return this.description;
        }

        /**
         * @return the priority
         */
        public RunnablePriority getPriority() {
            return this.priority;
        }

        /**
         * @return the value
         */
        public V getValue() {
            return this.value;
        }

    }

}
