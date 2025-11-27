package cc.thonly.base_bridge.fabric.mixin;

import cc.thonly.base_bridge.inf.c.CommonInfBiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = BiomeModification.class, remap = false)
public class BiomeModificationMixin implements CommonInfBiomeModification {
    @Shadow @Final private ResourceLocation id;

    @Override
    @Unique
    public ResourceLocation base_bridge$getId() {
        return this.id;
    }
}
