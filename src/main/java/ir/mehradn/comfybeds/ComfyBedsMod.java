package ir.mehradn.comfybeds;

import ir.mehradn.comfybeds.config.ComfyBedsConfig;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComfyBedsMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("comfy-beds");

	public void onInitialize() {
        ComfyBedsConfig.register();
	}
}