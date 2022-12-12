package net.avalonmc.mcmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.avalonmc.mcmod.mixin.ChatHudMixin;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.message.LastSeenMessageList;
import net.minecraft.text.Text;

import static net.avalonmc.mcmod.AvalonMod.mc;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;

public class TeamMsg  {

    static String[] playerNames = {"friselis", "uJoBuddy", "Ihaveatrashaim", "bytecoding", "0x36", "rtm516", "Th3Dilli", "DAMcraft", "LLethul"};

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        LiteralCommandNode<FabricClientCommandSource> literalCommandNode = dispatcher.register(ClientCommandManager.literal("teammsg")
                .then(argument("message", StringArgumentType.greedyString())
                        .executes(TeamMsg::teamMessage)
                ));

        dispatcher.register(ClientCommandManager.literal("tm").redirect(literalCommandNode));
        dispatcher.register(ClientCommandManager.literal("tmsg").redirect(literalCommandNode));
    }

    public static int teamMessage(CommandContext<?> context) {
        String message = context.getArgument("message", String.class);
        for (PlayerEntity onlinePlayer : mc.world.getPlayers()) {
            if (onlinePlayer.getUuid().equals(mc.player.getUuid())) {
                continue;
            }

            for (String targetName : playerNames) {
                if (onlinePlayer.getName().getString().equals(targetName)) {
                    // Send a direct message to the onlinePlayer
                    String command = "msg " + onlinePlayer.getName().getString() + " [TeamMSG] " + message;
                    mc.player.sendCommand(command, null);
                }
            }

            mc.inGameHud.getChatHud().addMessage(Text.of("§5[§dAvalon§5] §d"+ mc.player.getName().getString() +"§5: §r" + message));
        }
        return 1;
    }


}
