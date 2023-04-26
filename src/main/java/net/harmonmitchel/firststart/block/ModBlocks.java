package net.harmonmitchel.firststart.block;

import net.harmonmitchel.firststart.FirstStart;
import net.harmonmitchel.firststart.item.ModCreativeModeTab;
import net.harmonmitchel.firststart.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FirstStart.MOD_ID);

    public static final RegistryObject<Block> Starting_Block = registerBlock("starting_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
            .strength(3f).requiresCorrectToolForDrops()), ModCreativeModeTab.Starting_Tab);
    public static final RegistryObject<Block> Starting_Ore = registerBlock("starting_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
            .strength(3f).requiresCorrectToolForDrops(), UniformInt.of(3,7)), ModCreativeModeTab.Starting_Tab);
    public static final RegistryObject<Block> DeepSlate_Starting_Ore = registerBlock("deepslate_starting_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
            .strength(3f).requiresCorrectToolForDrops(), UniformInt.of(3,7)), ModCreativeModeTab.Starting_Tab);

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name, toReturn,tab);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.Items.register(name, () -> new BlockItem(block.get(),new Item.Properties().tab(tab)));
    }

    public static void  register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
