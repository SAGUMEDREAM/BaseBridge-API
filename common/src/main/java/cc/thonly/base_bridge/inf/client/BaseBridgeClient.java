package cc.thonly.base_bridge.inf.client;

import cc.thonly.base_bridge.util.LazyObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;

@Environment(EnvType.CLIENT)
public interface BaseBridgeClient {
    LazyObject<BaseBridgeClient> RET = new LazyObject<>();

    void registerBlockCutout(Block block);

    void registerBlockCutout(Block block, ChunkSectionLayer layer);

    static BaseBridgeClient getRet() {
        return RET.get();
    }

    static void setRet(BaseBridgeClient bridge) {
        RET.set(bridge);
    }
}
