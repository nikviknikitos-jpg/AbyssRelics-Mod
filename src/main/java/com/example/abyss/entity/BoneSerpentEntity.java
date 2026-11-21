package com.example.abyss.entity;

import com.example.abyss.AbyssUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BoneSerpentEntity extends Entity {
    private int life = 0;
    private Vec3d direction = new Vec3d(0, 0, 1);
    private Vec3d start = Vec3d.ZERO;

    public BoneSerpentEntity(EntityType<?> type, World world) {
        super(type, world);
        this.setNoGravity(true);
        this.setInvulnerable(true);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0);
    }

    public void init(Vec3d start, Vec3d direction) {
        this.start = start;
        this.direction = direction.normalize();
        this.refreshPositionAndAngles(start.x, start.y, start.z, 0, 0);
    }

    @Override
    public void tick() {
        super.tick();
        life++;
        if (getWorld().isClient) return;
        if (!(getWorld() instanceof ServerWorld sw)) return;
        double dist = life * 0.4;
        double arc = Math.sin(life * 0.08) * 2.5;
        double rise = Math.sin(life * 0.1) * 1.5;
        Vec3d pos = start.add(direction.multiply(dist))
                .add(0, 2 + rise, 0);
        setPosition(pos.x, pos.y, pos.z);
        AbyssUtil.breakSphere(sw, pos, 1.2);
        AbyssUtil.areaDamage(sw, pos, 3.0, 30.0f, 1.0);
        sw.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y, pos.z, 3, 0.3, 0.3, 0.3, 0.02);
        if (life > 50) this.discard();
    }

    @Override public boolean canHit() { return false; }
    @Override protected void initDataTracker() {}
    @Override protected void readCustomDataFromNbt(NbtCompound nbt) {}
    @Override protected void writeCustomDataToNbt(NbtCompound nbt) {}
}
