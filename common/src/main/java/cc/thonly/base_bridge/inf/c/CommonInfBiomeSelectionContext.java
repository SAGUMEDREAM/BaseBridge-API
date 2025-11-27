package cc.thonly.base_bridge.inf.c;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public interface CommonInfBiomeSelectionContext {
    ResourceKey<Biome> base_bridge$getBiomeKey();

    Biome base_bridge$getBiome();

    Holder<Biome> base_bridge$getBiomeRegistryEntry();
}
