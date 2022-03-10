package net.davoleo.crystalglass.init;

import net.davoleo.crystalglass.CrystalGlassMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public sealed class ModRegistry permits ModBlocks, ModFluids, ModItems {

    protected static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CrystalGlassMod.MODID);
    protected static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrystalGlassMod.MODID);
    protected static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, CrystalGlassMod.MODID);

    public static void init()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());

        forceInitRegistry(ModBlocks.class);
        forceInitRegistry(ModItems.class);
        forceInitRegistry(ModFluids.class);
    }

    public static ResourceLocation resourceLoc(String name)
    {
        return new ResourceLocation(CrystalGlassMod.MODID, name);
    }

    private static void forceInitRegistry(Class<? extends ModRegistry> registryClass)
    {
        try
        {
            Class.forName(registryClass.getName(), true, registryClass.getClassLoader());
        }
        catch (ClassNotFoundException e)
        {
            throw new AssertionError(e);
        }
    }

}
