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

public class TitanBloodItem extends Item {
    public TitanBloodItem(Settings s) { super(s); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity sp) {
            ServerWorld sw = (ServerWorld) world;
            sp.getPersistentData().putInt("abyss_titan_ticks", 500);
            var lightning = net.minecraft.entity.EntityType.LIGHTNING_BOLT.create(sw);
            if (lightning != null) {
                lightning.refreshPositionAfterTeleport(sp.getX(), sp.getY(), sp.getZ());
                sw.spawnEntity(lightning);
            }
            sw.playSound(null, sp.getBlockPos(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER,
                    SoundCategory.PLAYERS, 3.0f, 1.0f);
            AbyssUtil.areaDamage(sw, sp.getPos(), 6.0, 15.0f, 1.5);
            sp.getItemCooldownManager().set(this, 400);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}

