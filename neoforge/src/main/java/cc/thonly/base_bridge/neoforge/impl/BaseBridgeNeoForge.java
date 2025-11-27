package cc.thonly.base_bridge.neoforge.impl;

import cc.thonly.base_bridge.impl.BaseBridgeDriver;
import cc.thonly.base_bridge.neoforge.client.BaseBridgeClientNeoForgeImpl;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod("base_bridge")
public class BaseBridgeNeoForge {

    public BaseBridgeNeoForge() {
        IEventBus eventBus = NeoForge.EVENT_BUS;
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        BaseBridgeDriver.initialize(new BaseBridgeNeoForgeImpl());
    }

    private void clientSetup(FMLClientSetupEvent event) {
        BaseBridgeDriver.initializeClient(new BaseBridgeClientNeoForgeImpl());
    }
}
