package net.minecraftforge.event.entity.player;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class PlayerUseItemEvent extends PlayerEvent {
    public final ItemStack item;
    public int duration;

    public PlayerUseItemEvent(PlayerEntity player, ItemStack item, int duration) {
        super(player);
        this.item = item;
        this.duration = duration;
    }

    public static class Finish extends PlayerUseItemEvent {
        public ItemStack result;

        public Finish(PlayerEntity player, ItemStack item, int duration, ItemStack result) {
            super(player, item, duration);
            this.result = result;
        }
    }
}
