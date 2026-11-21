package com.example.abyss;

import com.example.abyss.command.GiveAbyssCommand;
import com.example.abyss.registry.ModEntities;
import com.example.abyss.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbyssMod implements ModInitializer {
    public static final String MODID = "abyss";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        ModEntities.register();
        ModItems.register();
        FabricDefaultAttributeRegistry.register(ModEntities.VOID_SHARD, VoidShardEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SWARM_DRONE, SwarmDroneEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.BONE_SERPENT, BoneSerpentEntity.createAttributes());
        CommandRegistrationCallback.EVENT.register((dispatcher, ra, env) -> GiveAbyssCommand.register(dispatcher));
        ServerTickEvents.END_WORLD_TICK.register(AbyssTickHandler::tick);
        LOGGER.info("AbyssRelicsMod initialized");
    }
}
