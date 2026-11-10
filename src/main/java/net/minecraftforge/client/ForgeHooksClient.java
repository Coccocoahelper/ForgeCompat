package net.minecraftforge.client;

import net.minecraft.block.BedBlock;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.client.resource.model.ModelTransformations;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class ForgeHooksClient {
    public static BakedModel handleCameraTransforms(BakedModel model, ModelTransformations.Type type) {
        model.getTransformations().apply(type);
        return model;
    }

    public static void orientBedCamera(WorldView world, BlockPos pos, BlockState state, Entity entity) {
        if (state.getBlock() instanceof BedBlock) {
            GlStateManager.rotatef(state.get(BedBlock.FACING).getIdHorizontal() * 90, 0.0F, 1.0F, 0.0F);
        }
    }

    public static void renderTileItem(Item item, int metadata) {
    }
}
