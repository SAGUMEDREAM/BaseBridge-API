package cc.thonly.base_bridge.neoforge.mixin;

import cc.thonly.base_bridge.inf.ServerContentRegistry;
import cc.thonly.base_bridge.util.LoadingPhase;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(RegistryDataLoader.class)
public class RegistryDataLoaderMixin {
    @Unique
    private static final ThreadLocal<Boolean> IS_SERVER = ThreadLocal.withInitial(() -> false);

    @Inject(
            method = "load(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;Ljava/util/List;)Lnet/minecraft/core/RegistryAccess$Frozen;",
            at = @At("HEAD")
    )
    private static void loadFromResource(ResourceManager resourceManager, List<HolderLookup.RegistryLookup<?>> registries, List<net.minecraft.resources.RegistryDataLoader.RegistryData<?>> entries, CallbackInfoReturnable<RegistryAccess.Frozen> cir) {
        if (entries.equals(RegistryDataLoader.WORLDGEN_REGISTRIES)) {
            ServerContentRegistry.LOADING_PHASE.set(LoadingPhase.DYNAMIC_REGISTRIES);
        }
        if (entries.equals(RegistryDataLoader.DIMENSION_REGISTRIES)) {
            ServerContentRegistry.LOADING_PHASE.set(LoadingPhase.DIMENSION_REGISTRIES);
        }
    }

//    @WrapOperation(method = "load(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;Ljava/util/List;)Lnet/minecraft/core/RegistryAccess$Frozen;",
//            at = @At(value = "INVOKE",
//                    target = "Lnet/minecraft/resources/RegistryDataLoader;load(Lnet/minecraft/resources/RegistryDataLoader$LoadingFunction;Ljava/util/List;Ljava/util/List;)Lnet/minecraft/core/RegistryAccess$Frozen;"
//            ))
//    private static RegistryAccess.Frozen wrapIsServerCall(RegistryDataLoader.LoadingFunction loadingFunction, List<HolderLookup.RegistryLookup<?>> baseRegistries, List<RegistryDataLoader.RegistryData<?>> entries, Operation<RegistryAccess.Frozen> original) {
//        try {
//            IS_SERVER.set(true);
//            return original.call(loadingFunction, baseRegistries, entries);
//        } finally {
//            IS_SERVER.set(false);
//        }
//    }
//
//    @Inject(
//            method = {"load(Lnet/minecraft/resources/RegistryDataLoader$LoadingFunction;Ljava/util/List;Ljava/util/List;Z)Lnet/minecraft/core/RegistryAccess$Frozen;"},
//            at = {@At(
//                    value = "INVOKE",
//                    target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V",
//                    ordinal = 1
//            )}
//    )
//    private static void loadInject(
//            RegistryDataLoader.LoadingFunction loadingFunction,
//            List<HolderLookup.RegistryLookup<?>> list,
//            List<RegistryDataLoader.RegistryData<?>> entries,
//            CallbackInfoReturnable<RegistryAccess.Frozen> cir,
//            @Local(ordinal = 2) List<RegistryDataLoader.Loader<?>> registriesList
//    ) {
//        if (IS_SERVER.get()) {
//            for (RegistryDataLoader.Loader<?> next : registriesList) {
//                WritableRegistry<?> registry = next.registry();
//                if (registry instanceof MappedRegistry<?> mappedRegistry) {
//                    ServerContentRegistry serverContentRegistry =
//                            BaseBridge.getRet().getServerContentRegistry();
//                    serverContentRegistry.write(mappedRegistry);
//                }
//            }
//        }
//    }

}
