package cc.thonly.base_bridge.neoforge.impl;

import cc.thonly.base_bridge.inf.CompostingRegistry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("deprecation")
public class CompostingRegistryNeoForgeImpl implements CompostingRegistry {
    private final Set<Item> addedDynamicItem = new HashSet<>(128);
    private final Set<Tuple<TagKey<Item>, Float>> addedItemTagEntries = new HashSet<>(128);

    @Override
    public Float get(ItemLike item) {
        return ComposterBlock.COMPOSTABLES.getOrDefault(item.asItem(), 0.0F);
    }

    @Override
    public void add(ItemLike item, Float chance) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
    }

    @Override
    public void add(TagKey<Item> tag, Float chance) {
        this.addedItemTagEntries.add(new Tuple<>(tag, chance));
    }

    public Set<Item> getAddedDynamicItem() {
        return this.addedDynamicItem;
    }

    public Set<Tuple<TagKey<Item>, Float>> getAddedItemTagEntries() {
        return this.addedItemTagEntries;
    }
}
