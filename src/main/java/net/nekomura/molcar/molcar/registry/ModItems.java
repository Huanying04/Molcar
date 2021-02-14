package net.nekomura.molcar.molcar.registry;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.nekomura.molcar.molcar.Molcar;

public class ModItems {

    public static final Item LETTUCE_LEAF = new Item(new Item.Settings().group(ItemGroup.FOOD).maxCount(64).food(new FoodComponent.Builder().hunger(2).snack().build()));
    public static final Item MOLCAR_SPAN_EGG = new SpawnEggItem(ModEntities.MOLCAR, 16641740, 16762963, (new Item.Settings()).group(ItemGroup.MISC));

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(Molcar.MOD_ID, "lettuce_leaf"), LETTUCE_LEAF);
        Registry.register(Registry.ITEM, new Identifier(Molcar.MOD_ID, "molcar_spawn_egg"), MOLCAR_SPAN_EGG);
    }

}
