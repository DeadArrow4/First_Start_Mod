package net.harmonmitchel.schithiumworks.item;

import net.harmonmitchel.schithiumworks.SCHITHIUMWORKS;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final  DeferredRegister<Item> Items =
            DeferredRegister.create(ForgeRegistries.ITEMS, SCHITHIUMWORKS.MOD_ID);

    public static final RegistryObject<Item> Schithium_Ingot = Items.register("schithium_ingot", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.Schithium_Tab)));

    public static final RegistryObject<Item> Raw_Schithium = Items.register("raw_schithium", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.Schithium_Tab)));

    public static void register(IEventBus eventBus) {
        Items.register(eventBus);




    }




}
