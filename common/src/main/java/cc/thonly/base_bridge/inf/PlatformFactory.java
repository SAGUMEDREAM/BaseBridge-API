package cc.thonly.base_bridge.inf;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

@SuppressWarnings("unchecked")
public interface PlatformFactory {
    Map<Class<?>, Object> CONTENTS = new Object2ObjectOpenHashMap<>();

    static <T> void impl(Class<T> infClazz, T impl) {
        CONTENTS.put(infClazz, impl);
    }

    static <T> T getImpl(Class<T> infClazz) {
        if (!hasImpl(infClazz)) {
            throw new NullPointerException("Implementation not found for: " + infClazz.getName());
        }
        return (T) CONTENTS.get(infClazz);
    }

    static <T> boolean hasImpl(Class<T> infClazz) {
        return CONTENTS.containsKey(infClazz);
    }
}
