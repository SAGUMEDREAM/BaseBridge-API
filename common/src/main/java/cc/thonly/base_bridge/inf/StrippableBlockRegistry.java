package cc.thonly.base_bridge.inf;

import cc.thonly.base_bridge.util.FixedLazyObject;
import cc.thonly.base_bridge.util.LazyObject;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface StrippableBlockRegistry {
    LazyObject<Map<Block, Block>> STRIPPABLES = new FixedLazyObject<>(new HashMap<>());

    void register(Block input, Block stripped);

    Optional<BlockState> getStripped(BlockState blockState);
}
