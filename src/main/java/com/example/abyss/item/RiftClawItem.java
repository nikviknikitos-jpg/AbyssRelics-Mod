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

public class RiftClawItem extends Item {
    public RiftClawItem(Settings s) { super(s); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity sp) {
            ServerWorld sw = (ServerWorld) world;
            Vec3d from = sp.getPos();
            Vec3d dir = sp.getRotationVec(1.0f).normalize();
            Vec3d to = from.add(dir.multiply(18));
            sp.requestTeleport(to.x, to.y, to.z);
            AbyssUtil.explode(sw, from, 3.0f);
            AbyssUtil.explode(sw, to, 3.0f);
            AbyssUtil.areaDamage(sw, to, 5.0, 20.0f, 1.2);
            sw.spawnParticles(net.minecraft.particle.ParticleTypes.PORTAL,
                    from.x, from.y + 1, from.z, 40, 1, 1, 1, 0.2);
            sw.playSound(null, sp.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                    SoundCategory.PLAYERS, 2.0f, 1.0f);
            sp.getItemCooldownManager().set(this, 120);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}

