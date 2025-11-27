package cc.thonly.base_bridge.fabric.impl;

import cc.thonly.base_bridge.inf.CreativeTabRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class CreativeTabRegistryFabricImpl implements CreativeTabRegistry {
    @Override
    public void modifyEntries(ResourceKey<CreativeModeTab> resourceKey, Collection<ItemLike> collection) {
        ItemGroupEvents.modifyEntriesEvent(resourceKey).register(entries -> {
            for (ItemLike itemLike : collection) {
                entries.accept(itemLike);
            }
        });
    }

    @Override
    public void modifyEntries(ResourceKey<CreativeModeTab> resourceKey, Consumer<Collection<ItemLike>> consumer) {
        Collection<ItemLike> collection = new ArrayList<>();
        consumer.accept(collection);
        modifyEntries(resourceKey,collection);
    }

    @Override
    public void modifyStackEntries(ResourceKey<CreativeModeTab> resourceKey, Collection<ItemStack> collection) {
        ItemGroupEvents.modifyEntriesEvent(resourceKey).register(entries -> {
            for (ItemStack itemLike : collection) {
                entries.accept(itemLike);
            }
        });
    }

    @Override
    public void modifyStackEntries(ResourceKey<CreativeModeTab> resourceKey, Consumer<Collection<ItemStack>> consumer) {
        Collection<ItemStack> collection = new ArrayList<>();
        consumer.accept(collection);
        modifyStackEntries(resourceKey, collection);
    }

    @Override
    public void modifyConsumer(ResourceKey<CreativeModeTab> resourceKey, Consumer<CreativeModeTab.Output> consumer) {
        List<Tuple<ItemStack, CreativeModeTab.TabVisibility>> tuples = new ArrayList<>();
        consumer.accept((itemStack, tabVisibility) -> tuples.add(new Tuple<>(itemStack, tabVisibility)));
        modifyStackEntries(resourceKey, collection -> {
            for (Tuple<ItemStack, CreativeModeTab.TabVisibility> tuple : tuples) {
                collection.add(tuple.getA());
            }
        });
    }

    @Override
    public void modifyAddAfter(ResourceKey<CreativeModeTab> resourceKey, Item item, Consumer<CreativeModeTab.Output> consumer) {
        List<Tuple<ItemStack, CreativeModeTab.TabVisibility>> tuples = new ArrayList<>();
        consumer.accept((itemStack, tabVisibility) -> tuples.add(new Tuple<>(itemStack, tabVisibility)));
        ItemGroupEvents.modifyEntriesEvent(resourceKey).register(entries -> {
            for (Tuple<ItemStack, CreativeModeTab.TabVisibility> tuple : tuples) {
                entries.addAfter(item, tuple.getA());
            }
        });
    }
}
