package ir.mehradn.comfybeds.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.mojang.authlib.GameProfile;
import ir.mehradn.comfybeds.config.ComfyBedsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
    public ServerPlayerMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    /*@WrapWithCondition(method = "startSleepInBed", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/server/level/ServerPlayer;setRespawnPosition(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/BlockPos;FZZ)V"))
    private boolean disableSetSpawn(ServerPlayer instance, ResourceKey<Level> dimension, BlockPos position,
                                    float angle, boolean forced, boolean sendMessage) {
        return false;
    }*/

    @ModifyExpressionValue(method = "startSleepInBed", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/world/level/Level;isDay()Z"))
    private boolean disableDayRequirement(boolean isDay) {
        return !ComfyBedsConfig.allowSleepAtDay() && isDay;
    }
}
