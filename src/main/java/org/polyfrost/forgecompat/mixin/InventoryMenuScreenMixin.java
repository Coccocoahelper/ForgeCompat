package org.polyfrost.forgecompat.mixin;

import net.minecraft.client.gui.screen.game.inventory.InventoryMenuScreen;
import net.minecraft.inventory.slot.InventorySlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(InventoryMenuScreen.class)
public class InventoryMenuScreenMixin {
    @Shadow
    private InventorySlot hoveredSlot;

    public InventorySlot getSlotUnderMouse() {
        return hoveredSlot;
    }
}
