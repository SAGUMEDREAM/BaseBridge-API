package cc.thonly.base_bridge.fabric.impl;

import cc.thonly.base_bridge.fabric.client.BaseBridgeClientFabricImpl;
import cc.thonly.base_bridge.impl.BaseBridgeDriver;
import cc.thonly.base_bridge.inf.BaseBridge;
import cc.thonly.base_bridge.util.PlatformType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class BaseBridgeFabric implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        BaseBridgeDriver.initialize(new BaseBridgeFabricImpl());
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            initializeClient();
        }
        BaseBridge.setPlatformType(PlatformType.FABRIC);
    }

    void initializeClient() {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) {
            return;
        }
        BaseBridgeDriver.initializeClient(new BaseBridgeClientFabricImpl());
    }
}
