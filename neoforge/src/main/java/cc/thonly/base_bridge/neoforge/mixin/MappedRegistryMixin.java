package cc.thonly.base_bridge.neoforge.mixin;

import cc.thonly.base_bridge.inf.BaseBridge;
import cc.thonly.base_bridge.inf.ServerContentRegistry;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MappedRegistry.class)
public class MappedRegistryMixin<T> {
    @Unique
    private boolean base_bridge$injected = false;

    @SuppressWarnings("unchecked")
    @Inject(method = "freeze", at = @At("HEAD"))
    public void freezeInject(CallbackInfoReturnable<Registry<T>> cir) {
        if (!BaseBridge.retLoaded()) {
            return;
        }
        if (this.base_bridge$injected) {
            return;
        }
        this.base_bridge$injected = true;
        MappedRegistry<T> registry = (MappedRegistry<T>) (Object) this;
        ServerContentRegistry serverContentRegistry = BaseBridge.getRet().getServerContentRegistry();
        serverContentRegistry.write(registry);
    }
}
