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

public class ChaosSealItem extends Item {
    public ChaosSealItem(Settings s) { super(s); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity sp) {
            ServerWorld sw = (ServerWorld) world;
            Vec3d center = sp.getEyePos().add(sp.getRotationVec(1.0f).multiply(10));
            com.example.abyss.ChaosSealTask.start(sw, center);
            sp.getItemCooldownManager().set(this, 300);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}

