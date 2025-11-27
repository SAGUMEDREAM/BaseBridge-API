package cc.thonly.base_bridge.fabric.mixin;

import cc.thonly.base_bridge.inf.c.CommonInfBiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = BiomeSelectionContext.class, remap = false)
public interface BiomeSelectionContextMixin extends CommonInfBiomeSelectionContext {

    @Shadow ResourceKey<Biome> getBiomeKey();

    @Shadow Biome getBiome();

    @Shadow Holder<Biome> getBiomeRegistryEntry();

    @Override
    @Unique
    default ResourceKey<Biome> base_bridge$getBiomeKey() {
        return this.getBiomeKey();
    }

    @Override
    @Unique
    default Biome base_bridge$getBiome() {
        return this.getBiome();
    }

    @Override
    @Unique
    default Holder<Biome> base_bridge$getBiomeRegistryEntry() {
        return this.getBiomeRegistryEntry();
    }
}
