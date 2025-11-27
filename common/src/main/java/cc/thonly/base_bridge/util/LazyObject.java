package cc.thonly.base_bridge.util;

public class LazyObject<T> {
    T value;

    public LazyObject() {
        this(null);
    }

    public LazyObject(T value) {
        this.value = value;
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        if (this.value == null) {
            throw new NullPointerException();
        }
        return this.value;
    }
}
