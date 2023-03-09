package ir.mehradn.comfybeds.config;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public final class ComfyBedsConfig {
    public static void register() {
        MidnightConfig.init("comfy-beds", _ComfyBedsConfig.class);
    }

    public static boolean allowSleepAtDay() {
        return _ComfyBedsConfig.allowSleepAtDay;
    }

    public static SetSpawnCondition setSpawnCondition() {
        switch (_ComfyBedsConfig.setSpawnCondition) {
            case NORMAL -> {return SetSpawnCondition.NORMAL;}
            case COMMAND -> {return (allowSleepAtDay() ? SetSpawnCondition.COMMAND : SetSpawnCondition.NORMAL);}
            case SHIFT_CLICK -> {return SetSpawnCondition.SHIFT_CLICK;}
            case NORMAL_CLICK -> {return SetSpawnCondition.NORMAL_CLICK;}
        }
        return SetSpawnCondition.NORMAL;
    }

    public static Component getSetSpawnInstruction() {
        switch (ComfyBedsConfig.setSpawnCondition()) {
            case NORMAL -> {return Component.translatable("comfy-beds.chatMessage.normal");}
            case COMMAND -> {
                Component here = Component.translatable("comfy-beds.chatMessage.here").setStyle(Style.EMPTY
                    .withUnderlined(true)
                    .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/setspawnbed")));
                return Component.translatable("comfy-beds.chatMessage.runCommand", here);
            }
            case SHIFT_CLICK -> {return Component.translatable("comfy-beds.chatMessage.shiftClick");}
            case NORMAL_CLICK -> {return Component.translatable("comfy-beds.chatMessage.normalClick");}
        }
        return Component.empty();
    }

    public enum SetSpawnCondition {
        NORMAL,
        COMMAND,
        SHIFT_CLICK,
        NORMAL_CLICK
    }

    // DO NOT USE OUTSIDE OF THIS CLASS!!!
    // I'm forced to keep it public.
    public static final class _ComfyBedsConfig extends MidnightConfig {
        public enum SetSpawnCondition {
            NORMAL,
            COMMAND,
            SHIFT_CLICK,
            NORMAL_CLICK
        }

        @Entry
        public static boolean allowSleepAtDay = true;
        @Entry
        public static SetSpawnCondition setSpawnCondition = SetSpawnCondition.COMMAND;
    }
}
