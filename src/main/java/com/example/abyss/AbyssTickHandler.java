package com.example.abyss;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class AbyssTickHandler {
    public static void tick(ServerWorld world) {
        List<ServerPlayerEntity> players = world.getPlayers();
        for (ServerPlayerEntity p : players) {
            if (p.getPersistentData().getInt("abyss_aegis_ticks") > 0) {
                int t = p.getPersistentData().getInt("abyss_aegis_ticks") - 1;
                p.getPersistentData().putInt("abyss_aegis_ticks", t);
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 10, 4, false, false));
                if (p.age % 2 == 0) {
                    double r = 3.5;
                    for (int i = 0; i < 12; i++) {
                        double a = (p.age + i * 30) * 0.1;
                        world.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME,
                                p.getX() + Math.cos(a) * r, p.getY() + 1, p.getZ() + Math.sin(a) * r,
                                1, 0, 0.02, 0, 0.01);
                        world.spawnParticles(ParticleTypes.END_ROD,
                                p.getX() + Math.cos(-a) * r, p.getY() + 0.5, p.getZ() + Math.sin(-a) * r,
                                1, 0, 0.02, 0, 0.01);
                    }
                    // push away nearby mobs
                    Box box = new Box(p.getX() - r, p.getY() - r, p.getZ() - r,
                            p.getX() + r, p.getY() + r, p.getZ() + r);
                    for (var e : world.getEntitiesByClass(net.minecraft.entity.mob.MobEntity.class, box, e -> e.isAlive())) {
                        Vec3d dir = e.getPos().subtract(p.getPos()).normalize().multiply(0.6);
                        e.setVelocity(dir.x, 0.3, dir.z);
                        e.velocityModified = true;
                    }
                }
            }
            if (p.getPersistentData().getInt("abyss_wings_ticks") > 0) {
                int t = p.getPersistentData().getInt("abyss_wings_ticks") - 1;
                p.getPersistentData().putInt("abyss_wings_ticks", t);
                p.getAbilities().allowFlying = true;
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20, 2, false, false));
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20, 2, false, false));
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 40, 0, false, false));
                if (p.age % 2 == 0) {
                    world.spawnParticles(ParticleTypes.PORTAL,
                            p.getX() + (world.random.nextDouble() - 0.5) * 0.8,
                            p.getY() + world.random.nextDouble() * 1.5,
                            p.getZ() + (world.random.nextDouble() - 0.5) * 0.8,
                            1, 0, 0.02, 0, 0.01);
                    world.spawnParticles(ParticleTypes.END_ROD,
                            p.getX() + (world.random.nextDouble() - 0.5) * 0.8,
                            p.getY() + world.random.nextDouble() * 1.5,
                            p.getZ() + (world.random.nextDouble() - 0.5) * 0.8,
                            1, 0, 0.02, 0, 0.01);
                }
                if (t == 0) {
                    if (!p.isCreative() && !p.isSpectator()) {
                        p.getAbilities().allowFlying = false;
                        p.getAbilities().flying = false;
                    }
                }
            }
            if (p.getPersistentData().getInt("abyss_titan_ticks") > 0) {
                int t = p.getPersistentData().getInt("abyss_titan_ticks") - 1;
                p.getPersistentData().putInt("abyss_titan_ticks", t);
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 40, 4, false, false));
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40, 2, false, false));
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 40, 3, false, false));
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 40, 0, false, false));
                if (p.age % 5 == 0) {
                    world.spawnParticles(ParticleTypes.FLAME,
                            p.getX() + (world.random.nextDouble() - 0.5) * 1.5,
                            p.getY() + world.random.nextDouble() * 1.5,
                            p.getZ() + (world.random.nextDouble() - 0.5) * 1.5,
                            2, 0, 0.02, 0, 0.02);
                }
            }
        }
    }
}
