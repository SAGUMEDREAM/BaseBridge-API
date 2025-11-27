package cc.thonly.base_bridge.fabric.impl;

import cc.thonly.base_bridge.inf.StrippableBlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class StrippableBlockRegistryFabricImpl implements StrippableBlockRegistry {
    @Override
    public void register(Block input, Block stripped) {
        net.fabricmc.fabric.api.registry.StrippableBlockRegistry.register(input, stripped);
    }

    @Override
    public Optional<BlockState> getStripped(BlockState blockState) {
        return Optional.ofNullable(STRIPPABLES.get().get(blockState.getBlock())).map((block) -> {
            return block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, (Direction.Axis) blockState.getValue(RotatedPillarBlock.AXIS));
        });
    }
}
