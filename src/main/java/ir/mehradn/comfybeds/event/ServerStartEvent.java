package ir.mehradn.comfybeds.event;

import ir.mehradn.comfybeds.ComfyBeds;
import ir.mehradn.comfybeds.config.ComfyBedsConfig;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import java.io.IOException;

public final class ServerStartEvent {
    public static void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(ServerStartEvent::onServerStarted);
    }

    private static void onServerStarted(MinecraftServer server) {
        ComfyBeds.LOGGER.info("Loading the config...");
        ComfyBedsConfig.loadedConfig = new ComfyBedsConfig();
        try {
            ComfyBedsConfig.loadedConfig.load();
        } catch (IOException e) {
            ComfyBeds.LOGGER.warn("Failed to load the config!", e);
        }
    }
}
