package cc.thonly.base_bridge.inf;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public interface DefaultAttributeRegistry {
    void register(EntityType<? extends LivingEntity> type, AttributeSupplier.Builder builder);

    void register(EntityType<? extends LivingEntity> type, AttributeSupplier container);
}
