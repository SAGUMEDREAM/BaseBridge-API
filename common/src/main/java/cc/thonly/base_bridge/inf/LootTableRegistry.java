package cc.thonly.base_bridge.inf;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;

public interface LootTableRegistry {

    void addModify(Modify modify);

    void addReplace(Replace replace);

    LootTable replace(ResourceKey<LootTable> key, LootTable lootTable);

    interface Modify {
        void modify(ResourceKey<LootTable> key, LootTableModificationContext context, boolean builtin);
    }

    interface Replace {
        LootTable replace(ResourceKey<LootTable> key, LootTable lootTable);
    }

    interface LootTableModificationContext {
        void addPool(LootPool.Builder pool);
    }
}
