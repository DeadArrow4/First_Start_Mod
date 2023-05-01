package net.harmonmitchel.schithiumworks;

import com.mojang.logging.LogUtils;
import net.harmonmitchel.schithiumworks.block.ModBlocks;
import net.harmonmitchel.schithiumworks.block.entity.ModBlockEntities;
import net.harmonmitchel.schithiumworks.item.ModItems;
import net.harmonmitchel.schithiumworks.screen.ModMenuTypes;
import net.harmonmitchel.schithiumworks.screen.SchithiumGeneratorScreen;
import net.harmonmitchel.schithiumworks.world.feature.ModConfiguredFeatures;
import net.harmonmitchel.schithiumworks.world.feature.ModPlacedFeatures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SCHITHIUMWORKS.MOD_ID)
public class SCHITHIUMWORKS
{

    public static final String MOD_ID = "schithiumworks";

    private static final Logger LOGGER = LogUtils.getLogger();

    public SCHITHIUMWORKS()

    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.SCHITHIUM_GENERATOR_MENU.get(), SchithiumGeneratorScreen::new);
        }
    }
}
