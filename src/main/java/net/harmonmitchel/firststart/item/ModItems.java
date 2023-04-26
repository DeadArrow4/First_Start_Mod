package net.harmonmitchel.firststart.item;

import net.harmonmitchel.firststart.FirstStart;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final  DeferredRegister<Item> Items =
            DeferredRegister.create(ForgeRegistries.ITEMS, FirstStart.MOD_ID);

    public static final RegistryObject<Item> StartIngot = Items.register("startingot", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.Starting_Tab)));

    public static final RegistryObject<Item> Raw_Starting = Items.register("raw_starting", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.Starting_Tab)));

    public static void register(IEventBus eventBus) {
        Items.register(eventBus);




    }




}
