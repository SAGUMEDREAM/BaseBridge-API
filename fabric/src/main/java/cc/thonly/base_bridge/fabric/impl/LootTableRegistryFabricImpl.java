package cc.thonly.base_bridge.fabric.impl;

import cc.thonly.base_bridge.inf.LootTableRegistry;
import dev.architectury.event.events.common.LootEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.List;

public class LootTableRegistryFabricImpl implements LootTableRegistry {
    List<Replace> REPLACED = new ArrayList<>(128);

    @Override
    public void addModify(Modify modify) {
        LootEvent.MODIFY_LOOT_TABLE.register((key, context, builtin) -> {
            modify.modify(key, context::addPool, builtin);
        });
    }

    @Override
    public void addReplace(Replace replace) {
        REPLACED.add(replace);
    }

    @Override
    public LootTable replace(ResourceKey<LootTable> key, LootTable lootTable) {
        for (Replace replace : REPLACED) {
            LootTable table = replace.replace(key, lootTable);
            if (table != null && lootTable != table) {
                return table;
            }
        }
        return lootTable;
    }
}
