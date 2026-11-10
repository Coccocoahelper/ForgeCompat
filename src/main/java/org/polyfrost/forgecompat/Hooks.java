package org.polyfrost.forgecompat;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entities;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.resource.Identifier;

public final class Hooks {
    static final Set<String> FORGE_METHODS = Set.of(
            "canRiderInteract/0", "getDisplayNameString/0", "shouldSpreadItems/0", "getRegistryName/0",
            "getDigSpeed/2", "getMetadata/1", "getToolClasses/1"
    );

    private static int modifiers;

    public static Field getDeclaredField(Class<?> owner, String name) throws NoSuchFieldException {
        try {
            return owner.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            if (owner == Field.class && name.equals("modifiers")) return Hooks.class.getDeclaredField("modifiers");
            throw e;
        }
    }

    public static boolean canRiderInteract(Object entity) {
        return false;
    }

    public static String getDisplayNameString(Object player) {
        return ((PlayerEntity) player).getName();
    }

    public static boolean shouldSpreadItems(Object renderer) {
        return true;
    }

    public static String getRegistryName(Object entry) {
        Identifier id = entry instanceof Block block ? Block.REGISTRY.getKey(block) : Item.REGISTRY.getKey((Item) entry);
        return id == null ? null : id.toString();
    }

    public static float getDigSpeed(Object item, ItemStack stack, BlockState state) {
        return ((Item) item).getMiningSpeed(stack, state.getBlock());
    }

    public static int getMetadata(Object item, ItemStack stack) {
        return stack.getMetadata();
    }

    public static Set<String> getToolClasses(Object item, ItemStack stack) {
        if (item instanceof PickaxeItem) return Set.of("pickaxe");
        if (item instanceof AxeItem) return Set.of("axe");
        if (item instanceof ShovelItem) return Set.of("shovel");
        return Collections.emptySet();
    }

    public static String getEntityName(ItemStack stack) {
        return Entities.getKey(stack.getMetadata());
    }
}
