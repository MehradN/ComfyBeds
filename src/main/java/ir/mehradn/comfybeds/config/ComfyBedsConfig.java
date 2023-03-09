package ir.mehradn.comfybeds.config;

import eu.midnightdust.lib.config.MidnightConfig;

public final class ComfyBedsConfig {
    public static void register() {
        MidnightConfig.init("comfy-beds", _ComfyBedsConfig.class);
    }

    public static boolean allowSleepAtDay() {
        return _ComfyBedsConfig.allowSleepAtDay;
    }

    // DO NOT USE OUTSIDE OF THIS CLASS!!!
    // I'm forced to keep it public.
    public static final class _ComfyBedsConfig extends MidnightConfig {
        @Entry
        public static boolean allowSleepAtDay = true;
    }
}
