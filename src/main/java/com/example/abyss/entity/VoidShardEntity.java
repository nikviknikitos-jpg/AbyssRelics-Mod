package com.example.abyss.entity;

import com.example.abyss.AbyssUtil;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class VoidShardEntity extends Entity {
    private int life = 0;
    private ServerPlayerEntity owner;
    private LivingEntity target;

    public VoidShardEntity(EntityType<?> type, World world) {
        super(type, world);
        this.setNoGravity(true);
        this.setInvulnerable(true);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0);
    }

    public void init(ServerPlayerEntity owner, int index) {
        this.owner = owner;
        Vec3d base = owner.getPos().add(0, 8, 0);
        double a = Math.PI * 2 * index / 10.0;
        this.refreshPositionAndAngles(base.x + Math.cos(a) * 1.5, base.y, base.z + Math.sin(a) * 1.5, 0, 0);
    }

    @Override
    public void tick() {
        super.tick();
        life++;
        if (getWorld().isClient) {
            getWorld().addParticle(ParticleTypes.PORTAL,
                    getX() + (random.nextDouble() - 0.5) * 0.4,
                    getY() + (random.nextDouble() - 0.5) * 0.4,
                    getZ() + (random.nextDouble() - 0.5) * 0.4,
                    0, 0, 0);
            return;
        }
        if (!(getWorld() instanceof ServerWorld sw)) return;
        if (life < 40) {
            // descend from sky
            setPosition(getX(), getY() - 0.15, getZ());
            return;
        }
        if (target == null || !target.isAlive()) {
            var nearest = sw.getEntitiesByClass(LivingEntity.class,
                    new Box(getX() - 32, getY() - 32, getZ() - 32, getX() + 32, getY() + 32, getZ() + 32),
                    e -> e.isAlive() && !(e instanceof net.minecraft.entity.player.PlayerEntity));
            if (!nearest.isEmpty()) target = nearest.get(0);
        }
        if (target != null) {
            Vec3d dir = new Vec3d(target.getX() - getX(), target.getY() + 0.5 - getY(), target.getZ() - getZ()).normalize().multiply(0.6);
            setPosition(getX() + dir.x, getY() + dir.y, getZ() + dir.z);
            if (this.distanceTo(target) < 2.0) {
                AbyssUtil.explode(sw, getPos(), 2.5f);
                AbyssUtil.areaDamage(sw, getPos(), 4.0, 25.0f, 1.2);
                sw.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, getX(), getY(), getZ(), 2, 1, 1, 1, 0);
                this.discard();
            }
        }
        if (life > 200) this.discard();
    }

    @Override public boolean canHit() { return false; }
    @Override protected void initDataTracker() {}
    @Override protected void readCustomDataFromNbt(NbtCompound nbt) {}
    @Override protected void writeCustomDataToNbt(NbtCompound nbt) {}
}
