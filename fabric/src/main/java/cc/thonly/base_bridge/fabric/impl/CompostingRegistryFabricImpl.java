package cc.thonly.base_bridge.fabric.impl;

import cc.thonly.base_bridge.inf.CompostingRegistry;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public class CompostingRegistryFabricImpl implements CompostingRegistry {

    @Override
    public Float get(ItemLike item) {
        return CompostingChanceRegistry.INSTANCE.get(item);
    }

    @Override
    public void add(ItemLike item, Float chance) {
        CompostingChanceRegistry.INSTANCE.add(item, chance);
    }

    @Override
    public void add(TagKey<Item> tag, Float chance) {
        CompostingChanceRegistry.INSTANCE.add(tag, chance);
    }
}
