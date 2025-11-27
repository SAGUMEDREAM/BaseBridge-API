package cc.thonly.base_bridge.neoforge.impl;

import cc.thonly.base_bridge.inf.BiomeRegistry;
import cc.thonly.base_bridge.inf.c.CommonInfBiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.levelgen.GenerationStep;

import java.util.function.Predicate;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BiomeRegistryNeoForgeImpl implements BiomeRegistry {
    @Override
    public void addFeature(Predicate biomeSelector, GenerationStep.Decoration step, ResourceKey placedFeatureRegistryKey) {
        BiomeModifications.addFeature(biomeSelector, step, placedFeatureRegistryKey);
    }

    @Override
    public void addCarver(Predicate biomeSelector, ResourceKey configuredCarverKey) {
        BiomeModifications.addCarver(biomeSelector, configuredCarverKey);
    }

    @Override
    public void addSpawn(Predicate biomeSelector, MobCategory spawnGroup, EntityType entityType, int weight, int minGroupSize, int maxGroupSize) {
        BiomeModifications.addSpawn(biomeSelector, spawnGroup, entityType, weight, minGroupSize, maxGroupSize);
    }

    @Override
    public CommonInfBiomeModification create(ResourceLocation id) {
        return (CommonInfBiomeModification) BiomeModifications.create(id);
    }
}
