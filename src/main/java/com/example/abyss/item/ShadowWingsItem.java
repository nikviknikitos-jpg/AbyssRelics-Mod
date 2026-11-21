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

public class ShadowWingsItem extends Item {
    public ShadowWingsItem(Settings s) { super(s); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity sp) {
            sp.getPersistentData().putInt("abyss_wings_ticks", 600);
            sp.getAbilities().allowFlying = true;
            sp.sendAbilitiesUpdate();
            sp.getWorld().playSound(null, sp.getBlockPos(), SoundEvents.ENTITY_PHANTOM_FLAP,
                    SoundCategory.PLAYERS, 2.0f, 1.0f);
            sp.getItemCooldownManager().set(this, 200);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}

