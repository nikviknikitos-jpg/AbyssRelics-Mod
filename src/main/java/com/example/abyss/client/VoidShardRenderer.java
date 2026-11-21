package com.example.abyss.client;

import com.example.abyss.entity.VoidShardEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class VoidShardRenderer extends EntityRenderer<VoidShardEntity> {
    private final BlockRenderManager brm;
    public VoidShardRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.brm = ctx.getBlockRenderManager();
    }
    @Override public Identifier getTexture(VoidShardEntity e) { return new Identifier("minecraft", "textures/block/crying_obsidian.png"); }
    @Override
    public void render(VoidShardEntity e, float yaw, float tickDelta, MatrixStack m,
                       VertexConsumerProvider vcp, int light) {
        m.push();
        m.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(e.age * 6 + tickDelta));
        m.multiply(RotationAxis.POSITIVE_X.rotationDegrees(e.age * 4));
        float s = 0.6f;
        m.scale(s, s, s);
        brm.renderBlockAsEntity(Blocks.CRYING_OBSIDIAN.getDefaultState(), m, vcp, light, OverlayTexture.DEFAULT_UV);
        m.pop();
    }
}
