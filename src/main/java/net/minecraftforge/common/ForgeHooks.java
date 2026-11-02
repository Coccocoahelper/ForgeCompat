package net.minecraftforge.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class ForgeHooks {
    public static boolean onPlayerAttackTarget(PlayerEntity player, Entity target) {
        return !MinecraftForge.EVENT_BUS.post(new AttackEntityEvent(player, target));
    }
}
