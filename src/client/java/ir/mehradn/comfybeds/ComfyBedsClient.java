package ir.mehradn.comfybeds;

import ir.mehradn.mehradconfig.entrypoint.ModMenuConfigScreen;
import ir.mehradn.mehradconfig.gui.ConfigScreenBuilder;
import net.fabricmc.api.ClientModInitializer;

public class ComfyBedsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModMenuConfigScreen.register(ComfyBeds.MOD_ID, new ConfigScreenBuilder()
            .setScreenType(ConfigScreenBuilder.DefaultScreens.COMPACT)
            .setDescriptionY(81));
    }
}
