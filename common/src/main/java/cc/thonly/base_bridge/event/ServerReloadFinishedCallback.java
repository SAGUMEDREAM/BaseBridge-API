package cc.thonly.base_bridge.event;

import cc.thonly.base_bridge.impl.Event;
import cc.thonly.base_bridge.impl.EventFactory;
import net.minecraft.server.MinecraftServer;

@FunctionalInterface
public interface ServerReloadFinishedCallback {
    Event<ServerReloadFinishedCallback> EVENT =
            EventFactory.createArrayBacked(ServerReloadFinishedCallback.class, (listeners) -> (server) -> {
                for (ServerReloadFinishedCallback listener : listeners) {
                    listener.onReloadFinished(server);
                }
            });

    void onReloadFinished(MinecraftServer server);
}
