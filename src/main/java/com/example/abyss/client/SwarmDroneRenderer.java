package com.example.abyss.client;

import com.example.abyss.entity.SwarmDroneEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class SwarmDroneRenderer extends EntityRenderer<SwarmDroneEntity> {
    private final BlockRenderManager brm;
    public SwarmDroneRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.brm = ctx.getBlockRenderManager();
    }
    @Override public Identifier getTexture(SwarmDroneEntity e) { return new Identifier("minecraft", "textures/block/amethyst_block.png"); }
    @Override
    public void render(SwarmDroneEntity e, float yaw, float tickDelta, MatrixStack m,
                       VertexConsumerProvider vcp, int light) {
        m.push();
        m.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(e.age * 8 + tickDelta));
        m.multiply(RotationAxis.POSITIVE_X.rotationDegrees(e.age * 5));
        float s = 0.7f;
        m.scale(s, s, s);
        brm.renderBlockAsEntity(Blocks.AMETHYST_BLOCK.getDefaultState(), m, vcp, light, OverlayTexture.DEFAULT_UV);
        m.pop();
    }
}
