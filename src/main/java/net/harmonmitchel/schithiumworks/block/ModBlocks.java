package net.harmonmitchel.schithiumworks.block;

import net.harmonmitchel.schithiumworks.SCHITHIUMWORKS;
import net.harmonmitchel.schithiumworks.block.custom.SchithiumGeneratorBlock;
import net.harmonmitchel.schithiumworks.item.ModCreativeModeTab;
import net.harmonmitchel.schithiumworks.item.ModItems;
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
            DeferredRegister.create(ForgeRegistries.BLOCKS, SCHITHIUMWORKS.MOD_ID);

    public static final RegistryObject<Block> Schithium_Block = registerBlock("schithium_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
            .strength(3f).requiresCorrectToolForDrops()), ModCreativeModeTab.Schithium_Tab);
    public static final RegistryObject<Block> Schithium_Ore = registerBlock("schithium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
            .strength(3f).requiresCorrectToolForDrops(), UniformInt.of(3,7)), ModCreativeModeTab.Schithium_Tab);
    public static final RegistryObject<Block> DeepSlate_Schithium_Ore = registerBlock("deepslate_schithium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
            .strength(3f).requiresCorrectToolForDrops(), UniformInt.of(3,7)), ModCreativeModeTab.Schithium_Tab);
    public static final RegistryObject<Block> SCHITHIUM_GENERATOR = registerBlock("schithium_generator",
            () -> new SchithiumGeneratorBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(3f).requiresCorrectToolForDrops().noOcclusion()),ModCreativeModeTab.Schithium_Tab);

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
