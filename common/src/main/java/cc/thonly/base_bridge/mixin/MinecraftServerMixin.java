package cc.thonly.base_bridge.mixin;

import cc.thonly.base_bridge.event.ServerReloadFinishedCallback;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "reloadResources", at = @At("RETURN"))
    public void onResourceReloadedDone(Collection<String> collection, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        CompletableFuture<Void> future = cir.getReturnValue();
        future.thenAccept(unused -> {
            ServerReloadFinishedCallback.EVENT.invoker().onReloadFinished((MinecraftServer) (Object) this);
        });
    }
}
