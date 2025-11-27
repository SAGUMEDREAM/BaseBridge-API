package cc.thonly.base_bridge.fabric.client;

import cc.thonly.base_bridge.inf.BaseBridge;
import cc.thonly.base_bridge.inf.client.BaseBridgeClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;

@Environment(EnvType.CLIENT)
public class BaseBridgeClientFabricImpl implements BaseBridgeClient {
    @Override
    public void registerBlockCutout(Block block) {
        registerBlockCutout(block, ChunkSectionLayer.CUTOUT);
    }

    @Override
    public void registerBlockCutout(Block block, ChunkSectionLayer layer) {
        BlockRenderLayerMap.putBlock(block, layer);
        BaseBridge.CUTOUT_BLOCKS.add(block);
    }
}
