package ir.mehradn.comfybeds.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import ir.mehradn.comfybeds.config.ComfyBedsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
    @Shadow public abstract void displayClientMessage(Component chatComponent, boolean actionBar);

    @Shadow public abstract void sendSystemMessage(Component component);

    public ServerPlayerMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    @WrapWithCondition(method = "startSleepInBed", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/server/level/ServerPlayer;setRespawnPosition(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/BlockPos;FZZ)V"))
    private boolean disableSetSpawn(ServerPlayer instance, ResourceKey<Level> dimension, BlockPos position,
                                    float angle, boolean forced, boolean sendMessage) {
        boolean f;
        switch (ComfyBedsConfig.setSpawnCondition()) {
            case COMMAND -> f = false;
            case SHIFT_CLICK -> f = this.isShiftKeyDown();
            case NORMAL_CLICK -> f = !this.isShiftKeyDown();
            default -> f = true;
        }
        return f;
    }

    @ModifyExpressionValue(method = "startSleepInBed", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/world/level/Level;isDay()Z"))
    private boolean disableDayRequirement(boolean isDay) {
        return !ComfyBedsConfig.allowSleepAtDay() && isDay;
    }

    @Inject(method = "startSleepInBed", at = @At("RETURN"))
    private void informAboutSetSpawnCondition(CallbackInfoReturnable<Either<BedSleepingProblem, Unit>> ci) {
        if (ci.getReturnValue().left().isPresent() ||
            ComfyBedsConfig.setSpawnCondition() == ComfyBedsConfig.SetSpawnCondition.NORMAL)
            return;
        this.sendSystemMessage(ComfyBedsConfig.getSetSpawnInstruction());
    }
}
