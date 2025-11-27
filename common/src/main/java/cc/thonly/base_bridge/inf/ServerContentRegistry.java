package cc.thonly.base_bridge.inf;

import cc.thonly.base_bridge.util.LoadingPhase;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;

import java.util.Collection;
import java.util.Optional;


public interface ServerContentRegistry {
    ThreadLocal<LoadingPhase> LOADING_PHASE = ThreadLocal.withInitial(() -> LoadingPhase.NONE);

    <T> T register(ResourceKey<Registry<T>> registry, ResourceKey<T> resourceKey, T value);

    <T> T register(ResourceKey<Registry<T>> registry, ResourceLocation location, T value);

    <T> T delete(ResourceKey<Registry<T>> registry, ResourceKey<T> resourceKey);

    <T> Optional<T> getValue(ResourceKey<Registry<T>> registry, ResourceKey<T> resourceKey);

    <T> T get(ResourceKey<Registry<T>> registry, ResourceKey<T> resourceKey);

    <T> void write(WritableRegistry<T> writableRegistry);

    <T> Holder.Reference<T> bindReferenceHolder(WritableRegistry<T> writable, ResourceKey<T> resourceKey, T value);

    <T> Collection<Tuple<ResourceKey<T>, T>> entries(ResourceKey<Registry<T>> registry);

    <T> Collection<Tuple<ResourceKey<Registry<T>>, Tuple<ResourceKey<T>, T>>> entries();

}
