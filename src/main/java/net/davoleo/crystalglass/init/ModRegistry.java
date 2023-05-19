package net.davoleo.crystalglass.init;

import net.davoleo.crystalglass.CrystalGlassMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public sealed class ModRegistry permits ModBlocks, ModFluids, ModItems {

    protected static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CrystalGlassMod.MODID);
    protected static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrystalGlassMod.MODID);
    protected static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, CrystalGlassMod.MODID);
    protected static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, CrystalGlassMod.MODID);

    public static void init()
    {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        FLUIDS.register(eventBus);
        FLUID_TYPES.register(eventBus);

        ModBlocks.init();
        ModItems.init();
        ModFluids.init();
    }

    public static ResourceLocation resourceLoc(String name)
    {
        return new ResourceLocation(CrystalGlassMod.MODID, name);
    }

}
