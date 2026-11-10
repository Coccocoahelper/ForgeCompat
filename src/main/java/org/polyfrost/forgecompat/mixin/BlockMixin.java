package org.polyfrost.forgecompat.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public class BlockMixin {
    public boolean isLadder(WorldView world, BlockPos pos, LivingEntity entity) {
        Object block = this;
        return block == Blocks.LADDER || block == Blocks.VINE;
    }
}
