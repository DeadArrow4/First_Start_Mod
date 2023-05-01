package net.harmonmitchel.schithiumworks.block.entity;

import net.harmonmitchel.schithiumworks.item.ModItems;
import net.harmonmitchel.schithiumworks.screen.SchithiumGeneratorMenu;
import net.harmonmitchel.schithiumworks.util.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SchithiumGeneratorBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot){
                case 0 -> stack.getItem() == ModItems.Schithium_Ingot.get();
                case 2 -> false;
                default -> super.isItemValid(slot,stack);
            };
        }
    };
    private static final int ENERGY_ALLOWED = 1000000;

    private LazyOptional<IItemHandler> LazyItemHandler = LazyOptional.empty();

   private  LazyOptional<IEnergyStorage> LazyEnergyHandler = LazyOptional.empty();
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(1000000, 5000) {
        @Override
        public void onEnergyChange() {
            setChanged();
        }
    };


    public SchithiumGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SCHITHIUM_GENERATOR.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch(index){
                  case 0 -> SchithiumGeneratorBlockEntity.this.progress;
                  case 1 -> SchithiumGeneratorBlockEntity.this.maxProgress;
                    default -> 0;

                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> SchithiumGeneratorBlockEntity.this.progress = value;
                    case 1 -> SchithiumGeneratorBlockEntity.this.maxProgress = value;
                }

            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }


    @Override
    public Component getDisplayName() {
        return Component.literal("Schithium Generator");
    }



    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SchithiumGeneratorMenu(id,inventory,this,this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if(cap == CapabilityEnergy.ENERGY){
            return LazyEnergyHandler.cast();

        }

        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return LazyItemHandler.cast();
        }


        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        LazyItemHandler = LazyOptional.of(() -> itemHandler);
        LazyEnergyHandler = LazyOptional.of(()->ENERGY_STORAGE);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        LazyItemHandler.invalidate();
        LazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory",itemHandler.serializeNBT());
        nbt.putInt("schithium_generator.energy", ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(serializeNBT().getCompound("inventory"));
        ENERGY_STORAGE.setEnergy(nbt.getInt("schithium_generator.energy"));
    }
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SchithiumGeneratorBlockEntity pEnitiy) {
        /*if(level.isClientSide()){
            return;
        }*/
        if(hasSchithiumInFirstSlot(pEnitiy)){
            pEnitiy.ENERGY_STORAGE.receiveEnergy(64,false);
        }
        if(hasSchithium(pEnitiy) && energyNotFull(pEnitiy)){
            pEnitiy.progress++;
            receiveEnergy(pEnitiy);
            setChanged(level, pos, state);

            /*if (pEnitiy.progress >= pEnitiy.maxProgress){
                craftItem(pEnitiy);
            }*/
        }
        else {
            pEnitiy.resetProgress();
            setChanged(level,pos,state);
        }

    }


    private static void receiveEnergy(SchithiumGeneratorBlockEntity pEnitiy) {
        pEnitiy.ENERGY_STORAGE.receiveEnergy(ENERGY_ALLOWED,false);
    }

    private static boolean hasSchithiumInFirstSlot(SchithiumGeneratorBlockEntity pEnitiy) {
        return pEnitiy.itemHandler.getStackInSlot(0).getItem() == ModItems.Schithium_Ingot.get();
    }

    private void resetProgress() {
        this.progress = 0;
    }

    /*private static void craftItem(SchithiumGeneratorBlockEntity pEnitiy) {

    }*/

    private static boolean hasSchithium(SchithiumGeneratorBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots();i++) {
            inventory.setItem(0, entity.itemHandler.getStackInSlot(i));
        }
        boolean hasSchithiumInFirstSlot = entity.itemHandler.getStackInSlot(0).getItem() == ModItems.Schithium_Ingot.get();

        return hasSchithiumInFirstSlot/* && canInsertSchithium(inventory)*/;


    }

    /*private static boolean canInsertSchithium(SimpleContainer inventory) {
        return inventory.getItem(1).getMaxStackSize() > inventory.getItem(1).getCount();
    }*/

    private static boolean energyNotFull(SchithiumGeneratorBlockEntity pEntity) {
        return pEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_ALLOWED;
    }

}
