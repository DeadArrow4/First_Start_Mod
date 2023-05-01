package net.harmonmitchel.schithiumworks.block.entity;

import net.harmonmitchel.schithiumworks.SCHITHIUMWORKS;
import net.harmonmitchel.schithiumworks.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SCHITHIUMWORKS.MOD_ID);
    public static final RegistryObject<BlockEntityType<SchithiumGeneratorBlockEntity>> SCHITHIUM_GENERATOR =
            BLOCK_ENTITIES.register("schithium_generator", ()-> BlockEntityType.Builder.of(SchithiumGeneratorBlockEntity::new,
                    ModBlocks.SCHITHIUM_GENERATOR.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
