package cc.thonly.base_bridge.neoforge.impl;

import cc.thonly.base_bridge.inf.FuelRegistry;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;

import java.util.Map;

public class FuelRegistryNeoForgeImpl implements FuelRegistry {
    // item → burn time
    private final Map<Item, Integer> itemBurns = new Object2ObjectOpenHashMap<>(128);

    // tag → burn time
    private final Map<TagKey<Item>, Integer> tagBurns = new Object2ObjectOpenHashMap<>(128);


    @Override
    public void register(ItemLike item, int tick) {
        itemBurns.put(item.asItem(), tick);
    }

    @Override
    public void register(TagKey<Item> tagKey, int tick) {
        tagBurns.put(tagKey, tick);
    }

    @Override
    public void unregister(TagKey<Item> tagKey) {
        tagBurns.remove(tagKey);
    }

    @SuppressWarnings("deprecation")
    public void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        Item item = event.getItemStack().getItem();

        if (itemBurns.containsKey(item)) {
            event.setBurnTime(itemBurns.get(item));
            return;
        }

        for (var entry : tagBurns.entrySet()) {
            if (item.builtInRegistryHolder().is(entry.getKey())) {
                event.setBurnTime(entry.getValue());
                return;
            }
        }
    }
}
