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

public class VoidHeartItem extends Item {
    public VoidHeartItem(Settings s) { super(s); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity sp) {
            ServerWorld sw = (ServerWorld) world;
            for (int i = 0; i < 10; i++) {
                com.example.abyss.entity.VoidShardEntity shard =
                        com.example.abyss.registry.ModEntities.VOID_SHARD.create(sw);
                if (shard != null) {
                    shard.init(sp, i);
                    sw.spawnEntity(shard);
                }
            }
            sw.playSound(null, sp.getBlockPos(), SoundEvents.ENTITY_WITHER_SHOOT,
                    SoundCategory.PLAYERS, 2.0f, 1.2f);
            sp.getItemCooldownManager().set(this, 300);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}

