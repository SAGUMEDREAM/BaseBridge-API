package cc.thonly.base_bridge.neoforge.impl;

import cc.thonly.base_bridge.inf.StrippableBlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.Optional;

public class StrippableBlockRegistryNeoForgeImpl implements StrippableBlockRegistry {

    @Override
    public void register(Block input, Block stripped) {
        STRIPPABLES.get().put(input, stripped);
    }

    @Override
    public Optional<BlockState> getStripped(BlockState blockState) {
        Map<Block, Block> map = STRIPPABLES.get();
        return Optional.ofNullable(map.get(blockState.getBlock())).map((block) -> {
            return block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, (Direction.Axis) blockState.getValue(RotatedPillarBlock.AXIS));
        });
    }
}
