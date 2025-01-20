import java.util.ArrayList;
import java.util.List;

public class Lab12 {
    public static void main(String[] args) {
        Map<String, Integer> map = new Map<>();

        map.put("Alice", 25);
        System.out.println("Alice's age: " + map.get("Alice"));

        map.put("Bob", 30);
        System.out.println("Bob's age: " + map.get("Bob"));

        map.put("Alice", 26);
        System.out.println("Updated Alice's age: " + map.get("Alice"));

        map.remove("Bob");
        System.out.println("Bob's age after removal: " + map.get("Bob"));
    }
}

interface IEntry<K, V> {
    K getKey();
    V getValue();
    void setValue(V value);
}

class Entry<K, V> implements IEntry<K, V> {

    private K key;
    private V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public void setValue(V value) {this.value = value;}

}

interface IList<T> {
    int size();
    boolean isEmpty();
}

interface IMap<K, V> extends IList<K> {
    V put(K key, V value);
    V remove(K key);
    V get(K key);
    Iterable<Entry<K, V>> entrySet();
    Iterable<K> keySet();
    Iterable<V> values();
}


//
class ArrayListBasedMap<K, V> implements IMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private List<ArrayList<Entry<K, V>>> buckets;
    private int size;

    public ArrayListBasedMap() {
        buckets = new ArrayList<>(DEFAULT_CAPACITY);
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            buckets.add(new ArrayList<>());
        }
        size = 0;
    }

    private int hash(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode()) % buckets.size();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key);
        ArrayList<Entry<K, V>> bucket = buckets.get(index);

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                V oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }

        bucket.add(new Entry<>(key, value));
        size++;
        return null;
    }

    @Override
    public V remove(K key) {
        int index = hash(key);
        ArrayList<Entry<K, V>> bucket = buckets.get(index);

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                V oldValue = entry.getValue();
                bucket.remove(entry);
                size--;
                return oldValue;
            }
        }

        return null;
    }

    @Override
    public V get(K key) {
        int index = hash(key);
        ArrayList<Entry<K, V>> bucket = buckets.get(index);

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }

        return null;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        List<Entry<K, V>> allEntries = new ArrayList<>();
        for (ArrayList<Entry<K, V>> bucket : buckets) {
            allEntries.addAll(bucket);
        }
        return allEntries;
    }

    @Override
    public Iterable<K> keySet() {
        List<K> keys = new ArrayList<>();
        for (ArrayList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    @Override
    public Iterable<V> values() {
        List<V> values = new ArrayList<>();
        for (ArrayList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                values.add(entry.getValue());
            }
        }
        return values;
    }

}


// ARRAY BASED MAP
class Map<K, V> implements IMap<K, V> {

    private static final int DEFAULT_CAPACITY = 100;
    private Entry<K, V>[] table;
    private int size;


    public Map() {
        table = new Entry[DEFAULT_CAPACITY];
        size = 0;
    }

    // key alıyor index döndürüyor
    private int hash(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode()) % table.length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key);
        if (table[index] != null && table[index].getKey().equals(key)) {
            V oldValue = table[index].getValue();
            table[index].setValue(value);
            return oldValue;
        }

        table[index] = new Entry<>(key, value);
        size++;
        return null;
    }

    @Override
    public V remove(K key) {
        int index = hash(key);
        if (table[index] != null && table[index].getKey().equals(key)) {
            V oldValue = table[index].getValue();
            table[index] = null;
            size--;
            return oldValue;
        }
        return null;
    }


    // key alıyor value döndürüyor
    @Override
    public V get(K key) {
        int index = hash(key);
        if (table[index] != null && table[index].getKey().equals(key)) {
            return table[index].getValue();
        }
        return null;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        List<Entry<K, V>> entries = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                entries.add(entry);
            }
        }
        return entries;
    }

    @Override
    public Iterable<K> keySet() {
        List<K> keys = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    @Override
    public Iterable<V> values() {
        List<V> values = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                values.add(entry.getValue());
            }
        }
        return values;
    }
}
