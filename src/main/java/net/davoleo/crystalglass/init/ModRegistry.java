package net.davoleo.crystalglass.init;

import net.davoleo.crystalglass.CrystalGlass;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRegistry {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CrystalGlass.MODID);

    public static void init()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<CrystalClusterBlock> CRYSTAL_CLUSTER_BLOCK = BLOCKS.register("crystal_cluster", CrystalClusterBlock::new);

}
