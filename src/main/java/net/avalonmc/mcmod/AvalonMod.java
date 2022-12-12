package net.avalonmc.mcmod;

import com.mojang.brigadier.CommandDispatcher;
import net.avalonmc.mcmod.commands.TeamMsg;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class AvalonMod implements ModInitializer {
    public static MinecraftClient mc;
    @Override
    public void onInitialize() {
        System.out.println("AvalonMod loaded!");
        ClientCommandRegistrationCallback.EVENT.register(AvalonMod::registerCommands);
        mc = MinecraftClient.getInstance();
    }

    private static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess) {
        TeamMsg.register(dispatcher);
    }

}
