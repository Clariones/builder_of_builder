package clariones.tool.builder;

public class KV<K,V> {
    protected K key;
    protected V value;

    public KV() {
        super();
    }
    public KV(K k, V v) {
        this();
        setKey(k);
        setValue(v);
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
