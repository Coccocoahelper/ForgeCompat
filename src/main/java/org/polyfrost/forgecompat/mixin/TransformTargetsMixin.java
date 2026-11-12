package org.polyfrost.forgecompat.mixin;

import net.minecraft.client.ClientPlayerInteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleManager;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.menu.GameMenuScreen;
import net.minecraft.client.gui.screen.menu.options.OptionsScreen;
import net.minecraft.client.network.PlayerInfo;
import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.model.entity.PlayerModel;
import net.minecraft.client.render.platform.GLX;
import net.minecraft.client.render.world.WorldRenderer;
import net.minecraft.client.sound.system.SoundEngine;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({Minecraft.class, OptionsScreen.class, GameMenuScreen.class, PlayerRenderer.class, PlayerModel.class, LivingEntityRenderer.class, EntityRenderer.class, GameRenderer.class, Screen.class, GLX.class,
        LocalClientPlayerEntity.class, ItemStack.class, BowItem.class, ParticleManager.class, KeyBinding.class, ClientPlayNetworkHandler.class, EntityRenderDispatcher.class,
        ItemEntityRenderer.class, ClientPlayerInteractionManager.class, WorldRenderer.class, SoundEngine.class, PlayerInfo.class})
public abstract class TransformTargetsMixin {
}
