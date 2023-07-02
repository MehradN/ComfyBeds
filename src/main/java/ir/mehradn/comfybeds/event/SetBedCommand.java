package ir.mehradn.comfybeds.event;

import com.mojang.brigadier.context.CommandContext;
import ir.mehradn.comfybeds.config.ComfyBedsConfig;
import ir.mehradn.comfybeds.util.mixin.ServerPlayerExpanded;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public final class SetBedCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(Commands.literal("setbed")
                .requires(CommandSourceStack::isPlayer)
                .executes(SetBedCommand::setSpawnBed)));
    }

    public static int setSpawnBed(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        assert player != null;

        if (!((ServerPlayerExpanded)player).canSleepNaturally()) {
            context.getSource().sendFailure(Component.translatable("comfy-beds.command.no_bed"));
            return 0;
        }

        if (ComfyBedsConfig.loadedConfig.changeRespawn.get() != ComfyBedsConfig.ChangeRespawn.COMMAND ||
            !player.isSleeping() || player.getSleepingPos().isEmpty()) {
            context.getSource().sendSystemMessage(ComfyBedsConfig.loadedConfig.getInstruction(false));
            return 0;
        }

        player.setRespawnPosition(player.level().dimension(), player.getSleepingPos().get(), player.getYRot(), false, true);
        return 1;
    }
}
