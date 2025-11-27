package cc.thonly.base_bridge.inf;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Collection;
import java.util.function.Consumer;

public interface CreativeTabRegistry {
    void modifyEntries(ResourceKey<CreativeModeTab> resourceKey, Collection<ItemLike> collection);
    void modifyEntries(ResourceKey<CreativeModeTab> resourceKey, Consumer<Collection<ItemLike>> consumer);
    void modifyStackEntries(ResourceKey<CreativeModeTab> resourceKey, Collection<ItemStack> collection);
    void modifyStackEntries(ResourceKey<CreativeModeTab> resourceKey, Consumer<Collection<ItemStack>> consumer);
    void modifyConsumer(ResourceKey<CreativeModeTab> resourceKey, Consumer<CreativeModeTab.Output> consumer);
    void modifyAddAfter(ResourceKey<CreativeModeTab> resourceKey, Item item, Consumer<CreativeModeTab.Output> consumer);
}
