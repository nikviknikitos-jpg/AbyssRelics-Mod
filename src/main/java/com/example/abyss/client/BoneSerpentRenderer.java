package com.example.abyss.client;

import com.example.abyss.entity.BoneSerpentEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class BoneSerpentRenderer extends EntityRenderer<BoneSerpentEntity> {
    private final BlockRenderManager brm;
    public BoneSerpentRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.brm = ctx.getBlockRenderManager();
    }
    @Override public Identifier getTexture(BoneSerpentEntity e) { return new Identifier("minecraft", "textures/block/bone_block_side.png"); }
    @Override
    public void render(BoneSerpentEntity e, float yaw, float tickDelta, MatrixStack m,
                       VertexConsumerProvider vcp, int light) {
        int segs = 8;
        for (int i = 0; i < segs; i++) {
            m.push();
            double t = i / (double) segs;
            double offset = -t * 6.0;
            m.translate(-Math.sin((e.age + tickDelta) * 0.1 - t * 3) * 1.5, -t * 2.0 + Math.sin(e.age * 0.1) * 0.5, offset);
            m.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(Math.sin((e.age + tickDelta) * 0.1 - t * 3) * 30)));
            float s = i == 0 ? 1.0f : 0.8f;
            m.scale(s, s, s);
            var state = (i == 0) ? Blocks.SKELETON_SKULL.getDefaultState() : Blocks.BONE_BLOCK.getDefaultState();
            brm.renderBlockAsEntity(state, m, vcp, light, OverlayTexture.DEFAULT_UV);
            m.pop();
        }
    }
}
