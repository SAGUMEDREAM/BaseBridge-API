package cc.thonly.base_bridge.inf;

import cc.thonly.base_bridge.inf.c.CommonInfBiomeModification;
import cc.thonly.base_bridge.inf.c.CommonInfBiomeSelectionContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.Predicate;

public interface BiomeRegistry<C extends CommonInfBiomeSelectionContext, M extends CommonInfBiomeModification> {
    void addFeature(Predicate<C> biomeSelector, GenerationStep.Decoration step, ResourceKey<PlacedFeature> placedFeatureRegistryKey);

    void addCarver(Predicate<C> biomeSelector, ResourceKey<ConfiguredWorldCarver<?>> configuredCarverKey);

    void addSpawn(Predicate<C> biomeSelector,
                  MobCategory spawnGroup, EntityType<?> entityType,
                  int weight, int minGroupSize, int maxGroupSize);

    M create(ResourceLocation id);
}
