package cc.thonly.base_bridge.mixin;

import cc.thonly.base_bridge.inf.StrippableBlockRegistry;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin extends Item {
    @Mutable
    @Shadow @Final protected static Map<Block, Block> STRIPPABLES;

    public AxeItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "<clinit>",at = @At("TAIL"))
    private static void initStrippable(CallbackInfo ci) {
        if (STRIPPABLES instanceof ImmutableMap) {
            STRIPPABLES = new HashMap<>(STRIPPABLES);
        }
        StrippableBlockRegistry.STRIPPABLES.set(STRIPPABLES);
    }
}
