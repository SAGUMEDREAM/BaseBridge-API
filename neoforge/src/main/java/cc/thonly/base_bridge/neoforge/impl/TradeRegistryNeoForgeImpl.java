package cc.thonly.base_bridge.neoforge.impl;

import cc.thonly.base_bridge.inf.TradeRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class TradeRegistryNeoForgeImpl implements TradeRegistry {

    /**
     * profession -> level -> trades
     */
    private static final Map<ResourceKey<VillagerProfession>,
            Map<Integer, List<VillagerTrades.ItemListing>>> TRADE_MAP = new ConcurrentHashMap<>();


    @Override
    public void registerVillagerTrade(ResourceKey<VillagerProfession> profession, int level, VillagerTrades.ItemListing... trades) {
        registerVillagerTrade(profession, level, Arrays.asList(trades));
    }

    @Override
    public void registerVillagerTrade(ResourceKey<VillagerProfession> profession, int level, Collection<VillagerTrades.ItemListing> trades) {
        TRADE_MAP
                .computeIfAbsent(profession, p -> new ConcurrentHashMap<>())
                .computeIfAbsent(level, l -> new ArrayList<>())
                .addAll(trades);
    }

    @Override
    public void registerVillagerOffers(ResourceKey<VillagerProfession> profession, int level,
                                       Consumer<Collection<VillagerTrades.ItemListing>> consumer) {

        List<VillagerTrades.ItemListing> list = new ArrayList<>();
        consumer.accept(list);
        registerVillagerTrade(profession, level, list);
    }


    public void onVillagerTrades(VillagerTradesEvent event) {
        ResourceKey<VillagerProfession> type = event.getType();
        Map<Integer, List<VillagerTrades.ItemListing>> levelListMap = TRADE_MAP.get(type);
        if (levelListMap == null) {
            return;
        }
        levelListMap.forEach((level, itemListings) -> {
            event.getTrades().computeIfAbsent(level, (x) -> NonNullList.create()).addAll(itemListings);
        });
    }
}
