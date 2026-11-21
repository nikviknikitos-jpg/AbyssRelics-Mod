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

public class SwarmCrownItem extends Item {
    public SwarmCrownItem(Settings s) { super(s); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity sp) {
            ServerWorld sw = (ServerWorld) world;
            for (int i = 0; i < 6; i++) {
                com.example.abyss.entity.SwarmDroneEntity drone =
                        com.example.abyss.registry.ModEntities.SWARM_DRONE.create(sw);
                if (drone != null) {
                    drone.init(sp, i);
                    sw.spawnEntity(drone);
                }
            }
            sw.playSound(null, sp.getBlockPos(), SoundEvents.BLOCK_BEACON_ACTIVATE,
                    SoundCategory.PLAYERS, 2.0f, 1.4f);
            sp.getItemCooldownManager().set(this, 400);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}

