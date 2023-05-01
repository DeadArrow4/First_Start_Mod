package net.harmonmitchel.schithiumworks.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab Schithium_Tab = new CreativeModeTab("schithiumworkstab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.Raw_Schithium.get());
        }
    };
}
