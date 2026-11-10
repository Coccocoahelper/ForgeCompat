package org.polyfrost.forgecompat.mixin;

import net.minecraft.entity.Entity;
import org.polyfrost.forgecompat.RavenHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Entity.class)
public class EntityMixin {
    @ModifyVariable(method = "move(DDD)V", at = @At("STORE"), ordinal = 0)
    private boolean forgecompat$ravenSafeWalk(boolean sneakingOnEdge) {
        return RavenHooks.active() ? RavenHooks.safeWalk((Entity) (Object) this) : sneakingOnEdge;
    }
}
