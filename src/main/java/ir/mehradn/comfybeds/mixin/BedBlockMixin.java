package ir.mehradn.comfybeds.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import ir.mehradn.comfybeds.config.ComfyBedsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedBlock.class)
public abstract class BedBlockMixin extends HorizontalDirectionalBlock implements EntityBlock {
    protected BedBlockMixin(Properties properties) {
        super(properties);
    }

    @ModifyReturnValue(method = "canSetSpawn", at = @At("RETURN"))
    private static boolean allowRestOutsideOverworld(boolean original) {
        return ComfyBedsConfig.loadedConfig.outsideOverworld.get() == ComfyBedsConfig.OutsideOverworld.REST || original;
    }

    @Inject(method = "use", cancellable = true, at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/world/level/Level;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"))
    private void disableBedExplosion(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit,
                                     CallbackInfoReturnable<InteractionResult> ci) {
        if (ComfyBedsConfig.loadedConfig.outsideOverworld.get() == ComfyBedsConfig.OutsideOverworld.MONSTERS)
            player.displayClientMessage(Player.BedSleepingProblem.NOT_SAFE.getMessage(), true);
        if (ComfyBedsConfig.loadedConfig.outsideOverworld.get() != ComfyBedsConfig.OutsideOverworld.EXPLODE)
            ci.setReturnValue(InteractionResult.SUCCESS);
    }
}
