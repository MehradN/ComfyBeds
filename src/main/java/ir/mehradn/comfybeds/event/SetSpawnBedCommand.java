package ir.mehradn.comfybeds.event;

import com.mojang.brigadier.context.CommandContext;
import ir.mehradn.comfybeds.config.ComfyBedsConfig;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public final class SetSpawnBedCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(Commands.literal("setbed")
                .executes(SetSpawnBedCommand::setSpawnBed)));
    }

    public static int setSpawnBed(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("This command can only be used by a player"));
            return 0;
        }

        if (ComfyBedsConfig.getChangeRespawn() != ComfyBedsConfig.ChangeRespawn.COMMAND
            || !player.isSleeping()
            || player.getSleepingPos().isEmpty()) {
            context.getSource().sendSystemMessage(ComfyBedsConfig.getInstruction(false));
            return 0;
        }

        player.setRespawnPosition(player.level.dimension(), player.getSleepingPos().get(), player.getYRot(), false, true);
        return 1;
    }
}
