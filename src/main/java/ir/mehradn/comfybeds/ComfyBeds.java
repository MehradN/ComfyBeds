package ir.mehradn.comfybeds;

import ir.mehradn.comfybeds.config.ComfyBedsConfig;
import ir.mehradn.comfybeds.event.ServerStartEvent;
import ir.mehradn.comfybeds.event.SetBedCommand;
import ir.mehradn.mehradconfig.entrypoint.ModMenuConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComfyBeds implements ModInitializer {
    public static final String MOD_ID = "comfy-beds";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public void onInitialize() {
        LOGGER.info("Registering events...");
        ServerStartEvent.register();
        SetBedCommand.register();
        LOGGER.info("Registering modmenu...");
        ModMenuConfig.register(MOD_ID, ComfyBedsConfig::new);
    }
}