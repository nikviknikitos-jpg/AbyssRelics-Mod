package com.example.abyss.client;

import com.example.abyss.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class AbyssClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.VOID_SHARD, VoidShardRenderer::new);
        EntityRendererRegistry.register(ModEntities.SWARM_DRONE, SwarmDroneRenderer::new);
        EntityRendererRegistry.register(ModEntities.BONE_SERPENT, BoneSerpentRenderer::new);
    }
}
