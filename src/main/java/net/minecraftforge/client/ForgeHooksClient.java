package net.minecraftforge.client;

import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.resource.model.ModelTransformations;

public class ForgeHooksClient {
    public static BakedModel handleCameraTransforms(BakedModel model, ModelTransformations.Type type) {
        model.getTransformations().apply(type);
        return model;
    }
}
