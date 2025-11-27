package cc.thonly.base_bridge.fabric.impl;

import cc.thonly.base_bridge.inf.FuelRegistry;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FuelRegistryFabricImpl implements FuelRegistry {
    private final Map<ItemLike, Integer> ITEM_ADDER = new Object2ObjectOpenHashMap<>();
    private final Map<TagKey<Item>, Integer> ITEM_TAG_ADDER = new Object2ObjectOpenHashMap<>();
    private final Set<TagKey<Item>> REMOVER = new HashSet<>();
    private boolean initialize = false;
    public void initialize() {
        initialize = true;
        FuelRegistryEvents.BUILD.register((builder, context) -> {
            ITEM_ADDER.forEach(builder::add);
            ITEM_TAG_ADDER.forEach(builder::add);
            REMOVER.forEach(builder::remove);
        });
    }

    @Override
    public void register(ItemLike item, int tick) {
        if (!initialize) {
            initialize();
        }
        ITEM_ADDER.put(item, tick);
    }

    @Override
    public void register(TagKey<Item> tagKey, int tick) {
        if (!initialize) {
            initialize();
        }
        ITEM_TAG_ADDER.put(tagKey, tick);
    }

    @Override
    public void unregister(TagKey<Item> tagKey) {
        if (!initialize) {
            initialize();
        }
        REMOVER.add(tagKey);
    }
}
