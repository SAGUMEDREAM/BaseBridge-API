package cc.thonly.base_bridge.impl;

import cc.thonly.base_bridge.inf.BaseBridge;
import cc.thonly.base_bridge.inf.client.BaseBridgeClient;

public class BaseBridgeDriver {

    public static void initialize(BaseBridge bridge) {
        BaseBridge.setRet(bridge);
        bridge.registerInternalEvents();
    }

    public static void initializeClient(BaseBridgeClient bridge) {
        BaseBridgeClient.setRet(bridge);
    }

}
