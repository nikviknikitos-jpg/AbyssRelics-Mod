package com.example.abyss;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class AbyssUtil {
    public static void areaDamage(ServerWorld world, Vec3d center, double radius, float damage, double knockback) {
        Box box = new Box(center.x - radius, center.y - radius, center.z - radius,
                center.x + radius, center.y + radius, center.z + radius);
        List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, box,
                e -> e.isAlive() && !(e instanceof net.minecraft.entity.player.PlayerEntity));
        for (LivingEntity e : entities) {
            e.damage(world.getDamageSources().magic(), damage);
            Vec3d dir = e.getPos().subtract(center);
            if (dir.lengthSquared() < 1e-4) dir = new Vec3d(0, 1, 0);
            dir = dir.normalize().multiply(knockback);
            e.setVelocity(dir.x, dir.y + 0.5, dir.z);
            e.velocityModified = true;
        }
    }

    public static void explode(World world, Vec3d pos, float power) {
        world.createExplosion(null, pos.x, pos.y, pos.z, power, World.ExplosionSourceType.TNT);
    }

    public static void breakSphere(ServerWorld world, Vec3d center, double radius) {
        int r = (int) Math.ceil(radius);
        var pos = net.minecraft.util.math.BlockPos.ofFloored(center.x, center.y, center.z);
        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -r; dy <= r; dy++) {
                for (int dz = -r; dz <= r; dz++) {
                    double d = Math.sqrt(dx*dx + dy*dy + dz*dz);
                    if (d > radius) continue;
                    var bp = pos.add(dx, dy, dz);
                    if (!world.getBlockState(bp).isAir() && world.getBlockState(bp).getHardness(world, bp) >= 0) {
                        world.breakBlock(bp, false);
                    }
                }
            }
        }
    }
}
