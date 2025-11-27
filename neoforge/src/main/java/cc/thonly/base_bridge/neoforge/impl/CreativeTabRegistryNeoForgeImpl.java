package cc.thonly.base_bridge.neoforge.impl;

import cc.thonly.base_bridge.inf.CreativeTabRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.*;
import java.util.function.Consumer;

public class CreativeTabRegistryNeoForgeImpl implements CreativeTabRegistry {

    private final Map<ResourceKey<CreativeModeTab>, Entry> key2Entry = new LinkedHashMap<>();

    @Override
    public void modifyEntries(ResourceKey<CreativeModeTab> tabKey, Collection<ItemLike> items) {
        Entry entry = this.key2Entry.computeIfAbsent(tabKey, k -> new Entry());
        items.forEach(item -> entry.items.add(new ItemStack(item)));
    }

    @Override
    public void modifyEntries(ResourceKey<CreativeModeTab> tabKey, Consumer<Collection<ItemLike>> consumer) {
        List<ItemLike> list = new ArrayList<>();
        consumer.accept(list);
        modifyEntries(tabKey, list);
    }

    @Override
    public void modifyStackEntries(ResourceKey<CreativeModeTab> tabKey, Collection<ItemStack> items) {
        this.key2Entry.computeIfAbsent(tabKey, k -> new Entry())
                .items.addAll(items);
    }

    @Override
    public void modifyStackEntries(ResourceKey<CreativeModeTab> tabKey, Consumer<Collection<ItemStack>> consumer) {
        List<ItemStack> list = new ArrayList<>();
        consumer.accept(list);
        modifyStackEntries(tabKey, list);
    }

    @Override
    public void modifyConsumer(ResourceKey<CreativeModeTab> tabKey, Consumer<CreativeModeTab.Output> consumer) {
        Entry entry = this.key2Entry.computeIfAbsent(tabKey, k -> new Entry());
        entry.consumers.add(consumer);
    }

    @Override
    public void modifyAddAfter(ResourceKey<CreativeModeTab> tabKey, Item afterItem, Consumer<CreativeModeTab.Output> consumer) {
        Entry entry = key2Entry.computeIfAbsent(tabKey, k -> new Entry());
        entry.afterAppender.put(afterItem, consumer);
    }

    public void buildContents(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTab tab = event.getTab();
        Optional<ResourceKey<CreativeModeTab>> tabKeyOp = BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(tab);

        if (tabKeyOp.isEmpty()) return;

        ResourceKey<CreativeModeTab> tabKey = tabKeyOp.get();
        Entry entry = key2Entry.get(tabKey);
        if (entry == null) return;

        event.acceptAll(entry.items);

        for (Consumer<CreativeModeTab.Output> consumer : entry.consumers) {
            consumer.accept(event);
        }

        entry.afterAppender.forEach((afterItem, consumer) -> {
            consumer.accept((stack, visibility) -> {
                event.insertAfter(afterItem.getDefaultInstance(), stack, visibility);
            });
        });
    }

    public static final class Entry {
        final List<ItemStack> items = new ArrayList<>();
        final List<Consumer<CreativeModeTab.Output>> consumers = new ArrayList<>();
        final Map<Item, Consumer<CreativeModeTab.Output>> afterAppender = new LinkedHashMap<>();
    }
}
