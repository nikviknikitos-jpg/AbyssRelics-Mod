package com.example.abyss.command;

import com.example.abyss.registry.ModItems;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class GiveAbyssCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("giveabyss").executes(ctx -> {
            ServerPlayerEntity p = ctx.getSource().getPlayer();
            if (p != null) {
                for (Item item : ModItems.all()) {
                    p.getInventory().insertStack(new ItemStack(item));
                }
                ctx.getSource().sendFeedback(() -> Text.literal("10 Abyss relics given"), false);
                return 1;
            }
            return 0;
        }));
    }
}
