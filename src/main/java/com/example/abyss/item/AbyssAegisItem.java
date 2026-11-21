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

public class AbyssAegisItem extends Item {
    public AbyssAegisItem(Settings s) { super(s); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity sp) {
            AbyssTickHandler.setAegisTicks(sp, 240);
            sp.getWorld().playSound(null, sp.getBlockPos(), SoundEvents.BLOCK_BEACON_ACTIVATE,
                    SoundCategory.PLAYERS, 2.0f, 1.5f);
            sp.getItemCooldownManager().set(this, 300);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}

