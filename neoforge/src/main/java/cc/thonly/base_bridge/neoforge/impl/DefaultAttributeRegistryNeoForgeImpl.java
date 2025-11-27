package cc.thonly.base_bridge.neoforge.impl;

import cc.thonly.base_bridge.inf.DefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public class DefaultAttributeRegistryNeoForgeImpl implements DefaultAttributeRegistry {
    @Override
    public void register(EntityType<? extends LivingEntity> type, AttributeSupplier.Builder builder) {
        register(type, builder.build());
    }

    @Override
    public void register(EntityType<? extends LivingEntity> type, AttributeSupplier container) {
        FabricDefaultAttributeRegistry.register(type, container);
    }
}
