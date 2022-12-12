package net.avalonmc.mcmod.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

import static net.avalonmc.mcmod.AvalonMod.mc;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin {

    @Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", cancellable = true)
    private void onAddMessage(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo ci) {
        if (message.getString().matches(".*"+mc.player.getName().getString()+".*:\\[TeamMSG\\] .*")) {
            // Cancel the message from being added to the chat
            ci.cancel();
            return;
        }
        if (message.getString().matches(".*: \\[TeamMSG\\] .*")) {
            // Cancel the message from being added to the chat
            ci.cancel();
            System.out.println("TeamMSG: " + message);
            mc.inGameHud.getChatHud().addMessage(Text.of("§5[§dAvalon§5] §d"+ message.getString().split("\\s+")[0] +" §r" + message));
        }
    }
}
