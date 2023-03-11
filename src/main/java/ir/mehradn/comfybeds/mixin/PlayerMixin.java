package ir.mehradn.comfybeds.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import ir.mehradn.comfybeds.config.ComfyBedsConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;stopSleepInBed(ZZ)V"))
    private boolean disableAutoWakeup(Player instance, boolean wakeImmediately, boolean updateLevelForSleepingPlayers) {
        return !ComfyBedsConfig.getAllowRestAtDay();
    }
}
