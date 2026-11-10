package org.polyfrost.forgecompat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.ServerListEntry;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodType.methodType;

public final class OverwriteHooks {
    private static final String ROOT = "net.ccbluex.liquidbounce.";
    private static final MethodHandle RENDER_SLOT;
    private static final MethodHandle KEEP_ITEM_ENABLED;
    private static final MethodHandle KEEP_ITEM_IN_THIRD_PERSON;
    private static final MethodHandle RENDER_BLOCKING;
    private static final MethodHandle UNCP_BLOCKING;
    private static final MethodHandle LOADING_CIRCLE;
    private static final MethodHandle HIDE_ADDRESS;
    private static final MethodHandle DRAW_FONT_40;
    private static final MethodHandle DRAW_FONT_35;

    static {
        try {
            MethodType bool = methodType(boolean.class);
            MethodType drawCentered = methodType(void.class, String.class, float.class, float.class, int.class, boolean.class);
            RENDER_SLOT = instance("utils.inventory.SilentHotbar", "renderSlot", methodType(int.class, boolean.class));
            KEEP_ITEM_ENABLED = instance("features.module.modules.render.SilentHotbarModule", "handleEvents", bool);
            KEEP_ITEM_IN_THIRD_PERSON = instance("features.module.modules.render.SilentHotbarModule", "getKeepItemInHandInThirdPerson", bool);
            RENDER_BLOCKING = instance("features.module.modules.combat.KillAura", "getRenderBlocking", bool);
            UNCP_BLOCKING = instance("features.module.modules.movement.NoSlow", "isUNCPBlocking", bool);
            LOADING_CIRCLE = instance("utils.render.RenderUtils", "drawLoadingCircle", methodType(void.class, float.class, float.class));
            HIDE_ADDRESS = instance("utils.client.ServerUtils", "hideSensitiveInformation", methodType(String.class, String.class));
            DRAW_FONT_40 = onField("ui.font.Fonts", "font40", "ui.font.GameFontRenderer", "drawCenteredString", drawCentered);
            DRAW_FONT_35 = onField("ui.font.Fonts", "font35", "ui.font.GameFontRenderer", "drawCenteredString", drawCentered);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private OverwriteHooks() {
    }

    private static MethodHandle instance(String owner, String method, MethodType type) throws ReflectiveOperationException {
        return onField(owner, "INSTANCE", owner, method, type);
    }

    private static MethodHandle onField(String owner, String field, String fieldType, String method, MethodType type) throws ReflectiveOperationException {
        ClassLoader loader = OverwriteHooks.class.getClassLoader();
        Class<?> target = Class.forName(ROOT + fieldType, false, loader);
        MethodHandles.Lookup lookup = MethodHandles.publicLookup();
        MethodHandle getter = lookup.findStaticGetter(Class.forName(ROOT + owner, false, loader), field, target);
        return MethodHandles.collectArguments(lookup.findVirtual(target, method, type), 0, getter);
    }

    public static ItemStack heldItem(LivingEntity entity, ItemStack original) {
        if (!(entity instanceof LocalClientPlayerEntity player)) return original;
        try {
            boolean keep = (boolean) KEEP_ITEM_ENABLED.invokeExact() && (boolean) KEEP_ITEM_IN_THIRD_PERSON.invokeExact();
            return player.inventory.getItem((int) RENDER_SLOT.invokeExact(keep));
        } catch (Throwable t) {
            throw new IllegalStateException(t);
        }
    }

    public static boolean forceBlocking(LivingEntity entity, ItemStack stack) {
        if (!(entity instanceof LocalClientPlayerEntity)) return false;
        try {
            return stack.getItem() instanceof SwordItem && (boolean) RENDER_BLOCKING.invokeExact() || (boolean) UNCP_BLOCKING.invokeExact();
        } catch (Throwable t) {
            throw new IllegalStateException(t);
        }
    }

    public static void drawConnecting(Screen screen) {
        float centerX = screen.width / 2.0F;
        float top = screen.height / 4.0F;
        ServerListEntry server = Minecraft.getInstance().getCurrentServerEntry();
        try {
            LOADING_CIRCLE.invokeExact(centerX, top + 70.0F);
            String address = server == null ? "Unknown" : (String) HIDE_ADDRESS.invokeExact(server.ip);
            DRAW_FONT_40.invokeExact("Connecting to", centerX, top + 110.0F, 0xFFFFFF, true);
            DRAW_FONT_35.invokeExact(address, centerX, top + 120.0F, 0x5281FB, true);
        } catch (Throwable t) {
            throw new IllegalStateException(t);
        }
    }
}
