package net.harmonmitchel.firststart.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab Starting_Tab = new CreativeModeTab("firststarttab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.Raw_Starting.get());
        }
    };
}
