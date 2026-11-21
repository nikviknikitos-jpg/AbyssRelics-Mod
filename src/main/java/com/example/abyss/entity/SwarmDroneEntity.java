package com.example.abyss.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SwarmDroneEntity extends Entity {
    private int life = 0;
    private int attackCd = 0;
    private ServerPlayerEntity owner;
    private int index = 0;

    public SwarmDroneEntity(EntityType<?> type, World world) {
        super(type, world);
        this.setNoGravity(true);
        this.setInvulnerable(true);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0);
    }

    public void init(ServerPlayerEntity owner, int index) {
        this.owner = owner;
        this.index = index;
    }

    @Override
    public void tick() {
        super.tick();
        life++;
        if (getWorld().isClient) {
            getWorld().addParticle(ParticleTypes.END_ROD,
                    getX() + (random.nextDouble() - 0.5) * 0.3,
                    getY() + (random.nextDouble() - 0.5) * 0.3,
                    getZ() + (random.nextDouble() - 0.5) * 0.3,
                    0, 0, 0);
            return;
        }
        if (!(getWorld() instanceof ServerWorld sw)) return;
        if (owner == null || !owner.isAlive() || life > 300) {
            this.discard();
            return;
        }
        double angle = (life * 0.1) + index * (Math.PI * 2 / 6);
        Vec3d target = new Vec3d(owner.getX() + Math.cos(angle) * 2.5,
                owner.getY() + 2.0 + Math.sin(life * 0.2) * 0.3,
                owner.getZ() + Math.sin(angle) * 2.5);
        Vec3d move = target.subtract(getPos()).multiply(0.2);
        setPosition(getX() + move.x, getY() + move.y, getZ() + move.z);

        if (attackCd > 0) attackCd--;
        else {
            var nearest = sw.getEntitiesByClass(LivingEntity.class,
                    new Box(getX() - 16, getY() - 16, getZ() - 16, getX() + 16, getY() + 16, getZ() + 16),
                    e -> e.isAlive() && !(e instanceof net.minecraft.entity.player.PlayerEntity));
            if (!nearest.isEmpty()) {
                LivingEntity victim = nearest.get(0);
                victim.damage(sw.getDamageSources().magic(), 15.0f);
                for (int i = 0; i < 20; i++) {
                    double t = i / 20.0;
                    sw.spawnParticles(ParticleTypes.END_ROD,
                            getX() + (victim.getX() - getX()) * t,
                            getY() + (victim.getY() + 1 - getY()) * t,
                            getZ() + (victim.getZ() - getZ()) * t,
                            1, 0, 0, 0, 0);
                    sw.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME,
                            getX() + (victim.getX() - getX()) * t,
                            getY() + (victim.getY() + 1 - getY()) * t,
                            getZ() + (victim.getZ() - getZ()) * t,
                            1, 0, 0, 0, 0);
                }
                attackCd = 15;
            }
        }
    }

    @Override public boolean canHit() { return false; }
    @Override protected void initDataTracker() {}
    @Override protected void readCustomDataFromNbt(NbtCompound nbt) {}
    @Override protected void writeCustomDataToNbt(NbtCompound nbt) {}
}
