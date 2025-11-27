package cc.thonly.base_bridge.inf;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public interface FuelRegistry {
    void register(ItemLike item, int tick);

    void register(TagKey<Item> tagKey, int tick);

    void unregister(TagKey<Item> tagKey);
}
