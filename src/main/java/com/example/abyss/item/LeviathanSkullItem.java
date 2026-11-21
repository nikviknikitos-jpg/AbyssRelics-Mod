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

public class LeviathanSkullItem extends Item {
    public LeviathanSkullItem(Settings s) { super(s); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity sp) {
            ServerWorld sw = (ServerWorld) world;
            Vec3d dir = sp.getRotationVec(1.0f).normalize();
            com.example.abyss.entity.BoneSerpentEntity serpent =
                    com.example.abyss.registry.ModEntities.BONE_SERPENT.create(sw);
            if (serpent != null) {
                serpent.init(sp.getPos(), dir);
                sw.spawnEntity(serpent);
            }
            sw.playSound(null, sp.getBlockPos(), SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT,
                    SoundCategory.PLAYERS, 2.0f, 0.5f);
            sp.getItemCooldownManager().set(this, 200);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}

