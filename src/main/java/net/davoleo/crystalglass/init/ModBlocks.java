package net.davoleo.crystalglass.init;

import com.google.common.collect.Lists;
import net.davoleo.crystalglass.CrystalGlass;
import net.davoleo.crystalglass.block.CrystalBlock;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.Supplier;

/**
 * Uses Deferred registry
 * which is a safe way to posticipate Mod Objects initialization as much as possible until they're needed in the game
 * <p>
 * It also initializes registry names automatically without needing to set them anywhere else in the mod
 */
public class ModBlocks {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CrystalGlass.MODID);

    //Initialize the deferred registries
    public static void init()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Registers the Crystal Cluster Block and its related BlockItem; TODO: this can be later rearranged to be way more polished less verbose
    public static final RegistryObject<CrystalClusterBlock> CRYSTAL_CLUSTER_BLOCK = BLOCKS.register("crystal_cluster", CrystalClusterBlock::new);

    public static final List<RegistryObject<CrystalBlock>> CRYSTAL_BLOCKS = Lists.newArrayListWithCapacity(CrystalBlock.Size.values().length);

    static
    {
        for (CrystalBlock.Size size : CrystalBlock.Size.values())
        {
            Pair<String, Supplier<CrystalBlock>> blockPair = CrystalBlock.create(size);
            CRYSTAL_BLOCKS.add(BLOCKS.register(blockPair.getLeft(), blockPair.getRight()));
        }
    }
}
