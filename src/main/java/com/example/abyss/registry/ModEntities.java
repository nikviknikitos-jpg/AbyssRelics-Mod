package com.example.abyss.registry;

import com.example.abyss.entity.BoneSerpentEntity;
import com.example.abyss.entity.SwarmDroneEntity;
import com.example.abyss.entity.VoidShardEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static EntityType<VoidShardEntity> VOID_SHARD;
    public static EntityType<SwarmDroneEntity> SWARM_DRONE;
    public static EntityType<BoneSerpentEntity> BONE_SERPENT;

    public static void register() {
        VOID_SHARD = Registry.register(Registries.ENTITY_TYPE,
                new Identifier("abyss", "void_shard"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, VoidShardEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
        SWARM_DRONE = Registry.register(Registries.ENTITY_TYPE,
                new Identifier("abyss", "swarm_drone"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, SwarmDroneEntity::new)
                        .dimensions(EntityDimensions.fixed(0.8f, 0.8f)).build());
        BONE_SERPENT = Registry.register(Registries.ENTITY_TYPE,
                new Identifier("abyss", "bone_serpent"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, BoneSerpentEntity::new)
                        .dimensions(EntityDimensions.fixed(2.0f, 2.0f)).build());
    }
}
