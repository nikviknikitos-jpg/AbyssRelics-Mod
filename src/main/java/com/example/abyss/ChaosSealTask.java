package com.example.abyss;

import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class ChaosSealTask {
    public static void start(ServerWorld world, Vec3d center) {
        world.getServer().execute(() -> {
            new Thread(() -> {
                try {
                    for (int i = 0; i < 60; i++) {
                        final int tick = i;
                        world.getServer().execute(() -> {
                            Box box = new Box(center.x - 14, center.y - 14, center.z - 14,
                                    center.x + 14, center.y + 14, center.z + 14);
                            List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, box,
                                    e -> e.isAlive() && !(e instanceof net.minecraft.entity.player.PlayerEntity));
                            for (LivingEntity e : list) {
                                e.requestTeleport(center.x, center.y + 0.5, center.z);
                                e.setVelocity(0, 0, 0);
                            }
                            world.spawnParticles(ParticleTypes.PORTAL, center.x, center.y, center.z,
                                    10, 1, 1, 1, 0.05);
                        });
                        Thread.sleep(50);
                    }
                    world.getServer().execute(() -> {
                        AbyssUtil.areaDamage(world, center, 14.0, 35.0f, 1.5);
                        AbyssUtil.explode(world, center, 4.5f);
                        world.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, center.x, center.y, center.z,
                                4, 1, 1, 1, 0);
                    });
                } catch (InterruptedException ignored) {}
            }, "ChaosSeal").start();
        });
    }
}
