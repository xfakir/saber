package cn.xfakir.saber.core.common.collection;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MultiValueMap<K,V> {
    private Map<K, List<V>> map = new LinkedHashMap<K, List<V>>();

    public void add(K key, V value) {
        if (map.containsKey(key)) {
            get(key).add(value);
        } else {
            List<V> list = new LinkedList<V>();
            list.add(value);
            this.map.put(key,list);
        }
    }

    public V getFirst(K key) {
        List<V> list = this.get(key);
        if (list != null) {
            return list.get(0);
        }
        return null;
    }

    public List<V> get(K key) {
        return this.map.get(key);
    }

    public Map<K, List<V>> getMap() {
        return map;
    }
}
