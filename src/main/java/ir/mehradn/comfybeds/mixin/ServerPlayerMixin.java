package ir.mehradn.comfybeds.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import ir.mehradn.comfybeds.config.ComfyBedsConfig;
import ir.mehradn.comfybeds.util.mixin.ServerPlayerExpanded;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player implements ServerPlayerExpanded {
    private boolean lyingDown = false;

    public ServerPlayerMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    @Shadow public abstract void displayClientMessage(Component chatComponent, boolean actionBar);

    @Shadow public abstract void sendSystemMessage(Component component);

    @Override
    public boolean canSleepNaturally() {
        return level().dimensionType().natural() && level().dimensionType().bedWorks();
    }

    @Override
    public boolean isSleepingNaturally() {
        return isSleeping() && canSleepNaturally() && !level().isDay();
    }

    @WrapWithCondition(method = "startSleepInBed", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/server/level/ServerPlayer;setRespawnPosition(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/BlockPos;FZZ)V"))
    private boolean changeRespawnPointIf(ServerPlayer instance, ResourceKey<Level> dimension, BlockPos position,
                                         float angle, boolean forced, boolean sendMessage) {
        if (!canSleepNaturally())
            return false;
        boolean f;
        switch (ComfyBedsConfig.loadedConfig.changeRespawn.get()) {
            case COMMAND -> f = false;
            case SHIFT_CLICK -> f = this.isShiftKeyDown();
            case NOT_SHIFT_CLICK -> f = !this.isShiftKeyDown();
            default -> f = true;
        }
        return f;
    }

    @ModifyExpressionValue(method = "startSleepInBed", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/world/level/dimension/DimensionType;natural()Z"))
    private boolean allowRestOutsideOverworld(boolean natural) {
        return ComfyBedsConfig.loadedConfig.outsideOverworld.get() == ComfyBedsConfig.OutsideOverworld.REST || natural;
    }

    @ModifyExpressionValue(method = "startSleepInBed", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/world/level/Level;isDay()Z"))
    private boolean allowRestInDay(boolean isDay) {
        return !ComfyBedsConfig.loadedConfig.allowRestAtDay.get() && isDay;
    }

    @Inject(method = "startSleepInBed", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;updateSleepingPlayerList()V"))
    private void startLyingDown(CallbackInfoReturnable<Either<BedSleepingProblem, Unit>> ci) {
        if (level().isDay())
            this.lyingDown = true;
    }

    @Inject(method = "startSleepInBed", at = @At("RETURN"))
    private void informAboutChangingRespawnPoint(CallbackInfoReturnable<Either<BedSleepingProblem, Unit>> ci) {
        if (!canSleepNaturally() ||
            ci.getReturnValue().left().isPresent() ||
            !ComfyBedsConfig.loadedConfig.provideInstructions.get() ||
            ComfyBedsConfig.loadedConfig.changeRespawn.get() == ComfyBedsConfig.ChangeRespawn.NORMAL)
            return;
        this.sendSystemMessage(ComfyBedsConfig.loadedConfig.getInstruction(true));
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void startSleeping(CallbackInfo ci) {
        if (this.lyingDown && !level().isDay()) {
            ((ServerLevel)level()).updateSleepingPlayerList();
            this.lyingDown = false;
        }
    }
}