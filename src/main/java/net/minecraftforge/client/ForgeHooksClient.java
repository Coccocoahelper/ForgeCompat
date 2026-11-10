package net.minecraftforge.client;

import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.resource.model.ModelTransformations;
import net.minecraft.item.Item;

public class ForgeHooksClient {
    public static BakedModel handleCameraTransforms(BakedModel model, ModelTransformations.Type type) {
        model.getTransformations().apply(type);
        return model;
    }

    public static void renderTileItem(Item item, int metadata) {
    }
}
