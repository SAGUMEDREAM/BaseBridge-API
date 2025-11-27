package cc.thonly.base_bridge.inf;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public interface CompostingRegistry {
    Float get(ItemLike item);

    void add(ItemLike item, Float chance);

    void add(TagKey<Item> tag, Float chance);
}
