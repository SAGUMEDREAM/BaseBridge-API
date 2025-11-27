package cc.thonly.base_bridge.inf;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.Collection;
import java.util.function.Consumer;

public interface TradeRegistry {
    void registerVillagerTrade(ResourceKey<VillagerProfession> profession, int level, VillagerTrades.ItemListing... trades);

    void registerVillagerTrade(ResourceKey<VillagerProfession> profession, int level, Collection<VillagerTrades.ItemListing> trades);

    void registerVillagerOffers(ResourceKey<VillagerProfession> profession, int level, Consumer<Collection<VillagerTrades. ItemListing>> consumer);
}
