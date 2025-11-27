package cc.thonly.base_bridge.neoforge.impl;

import cc.thonly.base_bridge.inf.LootTableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.event.LootTableLoadEvent;

import java.util.ArrayList;
import java.util.List;

public class LootTableRegistryNeoForgeImpl implements LootTableRegistry {
    List<Modify> MODIFY = new ArrayList<>(128);
    List<Replace> REPLACED = new ArrayList<>(128);

    @Override
    public void addModify(Modify modify) {
        MODIFY.add(modify);
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

    public void onModify(LootTableLoadEvent event) {
        LootTable table = event.getTable();
        for (Modify modify : MODIFY) {
            modify.modify(event.getKey(), pool -> table.addPool(pool.build()), true);
        }
    }
}
