package org.polyfrost.forgecompat.mixin;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow
    protected List<ButtonWidget> buttons;

    @Shadow
    protected Minecraft minecraft;

    @Shadow
    protected abstract void buttonClicked(ButtonWidget button);

    @Inject(method = "init(Lnet/minecraft/client/Minecraft;II)V", at = @At("TAIL"))
    private void forgecompat$initGuiPost(Minecraft minecraft, int width, int height, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.InitGuiEvent.Post((Screen) (Object) this, buttons));
    }

    @Redirect(method = "mouseClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;mouseClicked(Lnet/minecraft/client/Minecraft;II)Z"))
    private boolean forgecompat$actionPerformedPre(ButtonWidget button, Minecraft minecraft, int mouseX, int mouseY) {
        return button.mouseClicked(minecraft, mouseX, mouseY) && !MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.ActionPerformedEvent.Pre((Screen) (Object) this, button, buttons));
    }

    @Redirect(method = "mouseClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;buttonClicked(Lnet/minecraft/client/gui/widget/ButtonWidget;)V"))
    private void forgecompat$actionPerformedPost(Screen screen, ButtonWidget button) {
        buttonClicked(button);
        if (equals(minecraft.screen)) MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.ActionPerformedEvent.Post(screen, button, buttons));
    }

    @Inject(method = "sendChatMessage(Ljava/lang/String;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/living/player/LocalClientPlayerEntity;sendChat(Ljava/lang/String;)V"), cancellable = true)
    private void forgecompat$clientCommand(String message, boolean addToHistory, CallbackInfo ci) {
        if (ClientCommandHandler.instance.run(minecraft.player, message) != 0) ci.cancel();
    }
}
