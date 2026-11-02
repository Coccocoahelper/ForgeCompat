package org.polyfrost.forgecompat.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import net.minecraft.network.Connection;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.text.Text;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow
    private Minecraft minecraft;

    @Shadow
    @Final
    private Connection connection;

    @Inject(method = "handleChatMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketUtils;ensureOnSameThread(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/handler/PacketHandler;Lnet/minecraft/util/BlockableEventLoop;)V", shift = At.Shift.AFTER), cancellable = true)
    private void forgecompat$chatReceived(ChatMessageS2CPacket packet, CallbackInfo ci) {
        ClientChatReceivedEvent event = new ClientChatReceivedEvent(packet.getType(), packet.getMessage());
        if (MinecraftForge.EVENT_BUS.post(event) || event.message == null) {
            ci.cancel();
        } else if (event.message != packet.getMessage()) {
            Text message = event.message;
            if (event.type == 2) minecraft.gui.setOverlayMessage(message, false);
            else minecraft.gui.getChat().addMessage(message);
            ci.cancel();
        }
    }

    @Inject(method = "onDisconnect", at = @At("HEAD"))
    private void forgecompat$disconnect(Text reason, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new FMLNetworkEvent.ClientDisconnectionFromServerEvent(connection));
    }
}
