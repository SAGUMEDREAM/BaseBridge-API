package cc.thonly.base_bridge.fabric.impl;

import cc.thonly.base_bridge.inf.TradeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class TradeRegistryFabricImpl implements TradeRegistry {

    @Override
    public void registerVillagerTrade(ResourceKey<VillagerProfession> profession, int level, VillagerTrades.ItemListing... trades) {
        List<VillagerTrades.ItemListing> list = Arrays.asList(trades);
        registerVillagerTrade(profession, level, list);
    }

    @Override
    public void registerVillagerTrade(ResourceKey<VillagerProfession> profession, int level, Collection<VillagerTrades.ItemListing> trades) {
        TradeOfferHelper.registerVillagerOffers(profession, level, factories -> factories.addAll(trades));
    }

    @Override
    public void registerVillagerOffers(ResourceKey<VillagerProfession> profession, int level, Consumer<Collection<VillagerTrades.ItemListing>> consumer) {
        List<VillagerTrades.ItemListing> trades = new ArrayList<>();
        consumer.accept(trades);
        registerVillagerTrade(profession, level, trades);
    }
}
