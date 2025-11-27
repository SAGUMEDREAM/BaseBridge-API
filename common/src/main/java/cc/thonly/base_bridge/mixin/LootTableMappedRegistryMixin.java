package cc.thonly.base_bridge.mixin;

import cc.thonly.base_bridge.inf.BaseBridge;
import cc.thonly.base_bridge.inf.LootTableRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.include.com.google.common.base.Objects;

@Mixin(MappedRegistry.class)
public class LootTableMappedRegistryMixin<T> {
    @Shadow
    @Final
    private ResourceKey<? extends Registry<T>> key;

    @Unique
    private final ThreadLocal<ResourceKey<T>> CURRENT_KEY = ThreadLocal.withInitial(() -> null);

    @Inject(method = "register", at = @At("HEAD"), order = 2000)
    public void setCurrentKey(ResourceKey<T> resourceKey, T object, RegistrationInfo registrationInfo, CallbackInfoReturnable<Holder.Reference<T>> cir) {
        CURRENT_KEY.set(resourceKey);
    }

    @SuppressWarnings("unchecked")
    @ModifyVariable(
            method = "register",
            at = @At("HEAD"),
            argsOnly = true,
            index = 2,
            order = 2001
    )
    public Object modifyLoot(Object value) {
        if (!Objects.equal(this.key, Registries.LOOT_TABLE)) {
            return value;
        }
        if (value instanceof LootTable lootTable && BaseBridge.retLoaded()) {
            LootTableRegistry lootTableRegistry = BaseBridge.getRet().getLootTableRegistry();
            LootTable replace = lootTableRegistry.replace((ResourceKey<LootTable>) CURRENT_KEY.get(), lootTable);
            if (replace != null) {
                return replace;
            }
        }
        return value;
    }
}
