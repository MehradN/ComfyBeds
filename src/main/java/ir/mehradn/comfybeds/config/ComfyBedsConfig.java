package ir.mehradn.comfybeds.config;

import ir.mehradn.comfybeds.ComfyBeds;
import ir.mehradn.mehradconfig.MehradConfig;
import ir.mehradn.mehradconfig.entry.BooleanEntry;
import ir.mehradn.mehradconfig.entry.ConfigEntry;
import ir.mehradn.mehradconfig.entry.EnumEntry;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import java.util.List;

public class ComfyBedsConfig extends MehradConfig {
    public static ComfyBedsConfig loadedConfig = null;
    public final BooleanEntry allowRestAtDay =
        new BooleanEntry("allowRestAtDay", true);
    public final EnumEntry<ChangeRespawn> changeRespawn =
        new EnumEntry<>("changeRespawn", ChangeRespawn.class, ChangeRespawn.COMMAND);
    public final BooleanEntry provideInstructions =
        new BooleanEntry("provideInstructions", true);
    public final EnumEntry<OutsideOverworld> outsideOverworld =
        new EnumEntry<>("outsideOverworld", OutsideOverworld.class, OutsideOverworld.MONSTERS);

    public ComfyBedsConfig() {
        super(ComfyBeds.MOD_ID);
    }

    public Component getInstruction(boolean sleeping) {
        return switch (this.changeRespawn.get()) {
            case NORMAL -> Component.translatable("comfy-beds.command.normal");
            case SHIFT_CLICK -> Component.translatable("comfy-beds.command.shiftClick");
            case NOT_SHIFT_CLICK -> Component.translatable("comfy-beds.command.notShiftClick");
            case COMMAND -> {
                if (!sleeping)
                    yield Component.translatable("comfy-beds.command.command");
                Component here = Component.translatable("comfy-beds.command.here").setStyle(Style.EMPTY
                    .withUnderlined(true)
                    .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/setbed")));
                yield Component.translatable("comfy-beds.command.clickHere", here);
            }
        };
    }

    @Override
    public List<ConfigEntry<?>> getEntries() {
        return List.of(
            this.allowRestAtDay,
            this.changeRespawn,
            this.provideInstructions,
            this.outsideOverworld
        );
    }

    @Override
    public MehradConfig createNewInstance() {
        return new ComfyBedsConfig();
    }

    public enum ChangeRespawn {
        NORMAL,
        COMMAND,
        SHIFT_CLICK,
        NOT_SHIFT_CLICK
    }

    public enum OutsideOverworld {
        EXPLODE,
        MONSTERS,
        REST
    }
}
