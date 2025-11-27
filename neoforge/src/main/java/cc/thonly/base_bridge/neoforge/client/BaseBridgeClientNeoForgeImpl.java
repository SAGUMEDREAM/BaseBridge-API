package cc.thonly.base_bridge.neoforge.client;

import cc.thonly.base_bridge.inf.BaseBridge;
import cc.thonly.base_bridge.inf.client.BaseBridgeClient;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;

public class BaseBridgeClientNeoForgeImpl implements BaseBridgeClient {
    @Override
    public void registerBlockCutout(Block block) {
        registerBlockCutout(block, ChunkSectionLayer.CUTOUT);
    }

    @Override
    public void registerBlockCutout(Block block, ChunkSectionLayer layer) {
        ItemBlockRenderTypes.setRenderLayer(block, layer);
        BaseBridge.CUTOUT_BLOCKS.add(block);
    }
}
