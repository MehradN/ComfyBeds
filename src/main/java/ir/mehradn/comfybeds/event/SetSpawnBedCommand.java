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
            dispatcher.register(Commands.literal("setspawnbed")
                .executes(SetSpawnBedCommand::setSpawnBed)));
    }

    public static int setSpawnBed(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.translatable("comfy-beds.command.onlyPlayer"));
            return 0;
        } else if (ComfyBedsConfig.setSpawnCondition() != ComfyBedsConfig.SetSpawnCondition.COMMAND) {
            context.getSource().sendFailure(ComfyBedsConfig.getSetSpawnInstruction());
            return 0;
        } else if (!player.isSleeping()) {
            context.getSource().sendFailure(Component.translatable("comfy-beds.command.onlyOnBed"));
            return 0;
        }
        player.setRespawnPosition(player.level.dimension(), player.getSleepingPos().get(), player.getYRot(), false, true);
        return 1;
    }
}
