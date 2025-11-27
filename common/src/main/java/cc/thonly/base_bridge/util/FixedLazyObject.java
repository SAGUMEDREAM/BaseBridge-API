package cc.thonly.base_bridge.util;

import cc.thonly.base_bridge.exception.AlreadySetException;

public class FixedLazyObject<T> extends LazyObject<T> {
    private boolean modify = false;

    public FixedLazyObject() {
        this(null);
    }

    public FixedLazyObject(T value) {
        super(value);
    }

    @Override
    public void set(T value) {
        if (this.modify) {
            throw new AlreadySetException();
        }
        this.modify = true;
        super.set(value);
    }

}
