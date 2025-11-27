package cc.thonly.base_bridge.neoforge.inf;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.world.level.block.Block;

public interface IFireBlock {
    Object2IntMap<Block> base_bridge$getIgniteOdds();
    Object2IntMap<Block> base_bridge$getBurnOdds();
}
