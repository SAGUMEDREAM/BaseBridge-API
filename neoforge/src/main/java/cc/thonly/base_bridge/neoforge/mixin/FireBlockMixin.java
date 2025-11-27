package cc.thonly.base_bridge.neoforge.mixin;

import cc.thonly.base_bridge.neoforge.inf.IFireBlock;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FireBlock.class)
public class FireBlockMixin implements IFireBlock {
    @Shadow
    @Final
    private Object2IntMap<Block> burnOdds;

    @Shadow
    @Final
    private Object2IntMap<Block> igniteOdds;

    @Override
    @Unique
    public Object2IntMap<Block> base_bridge$getIgniteOdds() {
        return this.igniteOdds;
    }

    @Override
    @Unique
    public Object2IntMap<Block> base_bridge$getBurnOdds() {
        return this.burnOdds;
    }
}
