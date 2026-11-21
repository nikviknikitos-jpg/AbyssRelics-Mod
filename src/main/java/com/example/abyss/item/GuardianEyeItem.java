package com.example.abyss.item;

import com.example.abyss.AbyssUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GuardianEyeItem extends Item {
    public GuardianEyeItem(Settings s) { super(s); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity sp) {
            ServerWorld sw = (ServerWorld) world;
            Vec3d start = sp.getEyePos();
            Vec3d dir = sp.getRotationVec(1.0f).normalize();
            for (int d = 1; d <= 35; d++) {
                Vec3d p = start.add(dir.multiply(d));
                AbyssUtil.breakSphere(sw, p, 2.0);
                sw.spawnParticles(net.minecraft.particle.ParticleTypes.SOUL_FIRE_FLAME,
                        p.x, p.y, p.z, 3, 0.1, 0.1, 0.1, 0.01);
            }
            Vec3d end = start.add(dir.multiply(35));
            AbyssUtil.areaDamage(sw, end, 6.0, 30.0f, 1.5);
            AbyssUtil.explode(sw, end, 4.0f);
            sw.playSound(null, sp.getBlockPos(), SoundEvents.ENTITY_GUARDIAN_ATTACK,
                    SoundCategory.PLAYERS, 2.0f, 0.8f);
            sp.getItemCooldownManager().set(this, 200);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}

