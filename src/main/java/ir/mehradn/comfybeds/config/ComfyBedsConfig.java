package ir.mehradn.comfybeds.config;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public final class ComfyBedsConfig {
    public static void register() {
        MidnightConfig.init("comfy-beds", _ComfyBedsConfig.class);
    }

    public static boolean getAllowRestAtDay() {
        return _ComfyBedsConfig.allowRestAtDay;
    }

    public static ChangeRespawn getChangeRespawn() {
        switch (_ComfyBedsConfig.changeRespawn) {
            case NORMAL -> { return ChangeRespawn.NORMAL; }
            case COMMAND -> { return (getAllowRestAtDay() ? ChangeRespawn.COMMAND : ChangeRespawn.NORMAL); }
            case SHIFT_CLICK -> { return ChangeRespawn.SHIFT_CLICK; }
            case NOT_SHIFT_CLICK -> { return ChangeRespawn.NOT_SHIFT_CLICK; }
        }
        return ChangeRespawn.NORMAL;
    }

    public static Component getInstruction(boolean sleeping) {
        switch (ComfyBedsConfig.getChangeRespawn()) {
            case NORMAL -> { return Component.translatable("comfy-beds.command.normal"); }
            case SHIFT_CLICK -> { return Component.translatable("comfy-beds.command.shiftClick"); }
            case NOT_SHIFT_CLICK -> { return Component.translatable("comfy-beds.command.notShiftClick"); }
            case COMMAND -> {
                if (!sleeping)
                    return Component.translatable("comfy-beds.command.command");
                Component here = Component.translatable("comfy-beds.command.here").setStyle(Style.EMPTY
                    .withUnderlined(true)
                    .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/setbed")));
                return Component.translatable("comfy-beds.command.clickHere", here);
            }
        }
        return Component.empty();
    }

    public static OutsideOverworld getOutsideOverworld() {
        switch (_ComfyBedsConfig.outsideOverworld) {
            case EXPLODE -> { return OutsideOverworld.EXPLODE; }
            case MONSTERS -> { return OutsideOverworld.MONSTERS; }
            case REST -> { return OutsideOverworld.REST; }
        }
        return OutsideOverworld.EXPLODE;
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

    // DO NOT USE OUTSIDE OF THIS CLASS!!!
    // I'm forced to keep it public.
    public static final class _ComfyBedsConfig extends MidnightConfig {
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

        @Entry
        public static boolean allowRestAtDay = true;
        @Entry
        public static ChangeRespawn changeRespawn = ChangeRespawn.COMMAND;
        @Entry
        public static OutsideOverworld outsideOverworld = OutsideOverworld.MONSTERS;
    }
}
