package cc.thonly.base_bridge.neoforge.mixin;

import com.mojang.blaze3d.opengl.GlDevice;
import com.mojang.blaze3d.shaders.ShaderType;
import com.mojang.blaze3d.systems.GpuDevice;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.ClientHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiFunction;

@Pseudo
@Mixin(ClientHooks.class)
public class ClientHooksMixin {
//    @Inject(method = "createGpuDevice", at = @At("HEAD"), cancellable = true, order = 100)
//    private static void modifyCreateGpuDevice(long window, int debugLevel, boolean syncDebug, BiFunction<ResourceLocation, ShaderType, String> defaultShaderSource, boolean enableDebugLabels, CallbackInfoReturnable<GpuDevice> cir) {
//        final var glDevice = new GlDevice(window, debugLevel, syncDebug, defaultShaderSource, enableDebugLabels);
//        cir.setReturnValue(glDevice);
//    }
}
