package com.example.abyss.registry;

import com.example.abyss.item.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {
    public static Item LEVIATHAN_SKULL, VOID_HEART, SWARM_CROWN, GUARDIAN_EYE, RIFT_CLAW,
            CHAOS_SEAL, APOCALYPSE_SCROLL, ABYSS_AEGIS, SHADOW_WINGS, TITAN_BLOOD;
    public static ItemGroup ABYSS_TAB;

    public static void register() {
        LEVIATHAN_SKULL = reg("leviathan_skull", new LeviathanSkullItem(new Item.Settings().maxCount(1)));
        VOID_HEART = reg("void_heart", new VoidHeartItem(new Item.Settings().maxCount(1)));
        SWARM_CROWN = reg("swarm_crown", new SwarmCrownItem(new Item.Settings().maxCount(1)));
        GUARDIAN_EYE = reg("guardian_eye", new GuardianEyeItem(new Item.Settings().maxCount(1)));
        RIFT_CLAW = reg("rift_claw", new RiftClawItem(new Item.Settings().maxCount(1)));
        CHAOS_SEAL = reg("chaos_seal", new ChaosSealItem(new Item.Settings().maxCount(1)));
        APOCALYPSE_SCROLL = reg("apocalypse_scroll", new ApocalypseScrollItem(new Item.Settings().maxCount(1)));
        ABYSS_AEGIS = reg("abyss_aegis", new AbyssAegisItem(new Item.Settings().maxCount(1)));
        SHADOW_WINGS = reg("shadow_wings", new ShadowWingsItem(new Item.Settings().maxCount(1)));
        TITAN_BLOOD = reg("titan_blood", new TitanBloodItem(new Item.Settings().maxCount(1)));

        ABYSS_TAB = Registry.register(Registries.ITEM_GROUP,
                new Identifier("abyss", "abyss_tab"),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemGroup.abyss.abyss_tab"))
                        .icon(() -> new ItemStack(LEVIATHAN_SKULL))
                        .entries((ctx, entries) -> {
                            entries.add(LEVIATHAN_SKULL);
                            entries.add(VOID_HEART);
                            entries.add(SWARM_CROWN);
                            entries.add(GUARDIAN_EYE);
                            entries.add(RIFT_CLAW);
                            entries.add(CHAOS_SEAL);
                            entries.add(APOCALYPSE_SCROLL);
                            entries.add(ABYSS_AEGIS);
                            entries.add(SHADOW_WINGS);
                            entries.add(TITAN_BLOOD);
                        })
                        .build());
    }

    private static Item reg(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier("abyss", name), item);
    }

    public static Item[] all() {
        return new Item[]{LEVIATHAN_SKULL, VOID_HEART, SWARM_CROWN, GUARDIAN_EYE, RIFT_CLAW,
                CHAOS_SEAL, APOCALYPSE_SCROLL, ABYSS_AEGIS, SHADOW_WINGS, TITAN_BLOOD};
    }
}
