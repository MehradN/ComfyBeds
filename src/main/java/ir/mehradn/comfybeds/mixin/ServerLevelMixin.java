package ir.mehradn.comfybeds.mixin;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements WorldGenLevel {
    @Shadow public abstract void updateSleepingPlayerList();

    private boolean wasDay;

    protected ServerLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, holder, supplier, bl, bl2, l, i);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void initializeWasDay(CallbackInfo ci) {
        this.wasDay = this.isDay();
    }

    @Inject(method = "updateSleepingPlayerList", cancellable = true, at = @At("HEAD"))
    private void additionalDayCheck(CallbackInfo ci) {
        if (this.isDay())
            ci.cancel();
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void additionalNightCheck(CallbackInfo ci) {
        if (!this.isDay() && this.wasDay)
            this.updateSleepingPlayerList();
        this.wasDay = this.isDay();
    }
}
