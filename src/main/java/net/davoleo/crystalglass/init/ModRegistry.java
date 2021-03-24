package net.davoleo.crystalglass.init;

import net.davoleo.crystalglass.CrystalGlass;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Uses Deferred registry
 * which is a safe way to posticipate Mod Objects initialization as much as possible until they're needed in the game
 * <p>
 * It also initializes registry names automatically without needing to set them anywhere else in the mod
 */
public class ModRegistry {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CrystalGlass.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrystalGlass.MODID);

    //Initialize the deferred registries
    public static void init()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Registers the Crystal Cluster Block and its related BlockItem; TODO: this can be later rearranged to be way more polished less verbose
    public static final RegistryObject<CrystalClusterBlock> CRYSTAL_CLUSTER_BLOCK =
            BLOCKS.register("crystal_cluster", CrystalClusterBlock::new);
    public static final RegistryObject<Item> CRYSTAL_CLUSTER_BLOCKITEM =
            ITEMS.register("crysta_cluster", () -> CRYSTAL_CLUSTER_BLOCK.get().asItem());

}
