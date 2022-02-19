package net.davoleo.crystalglass.init;

import com.google.common.collect.Lists;
import net.davoleo.crystalglass.CrystalGlass;
import net.davoleo.crystalglass.block.CrystalBlock;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.davoleo.crystalglass.block.FullCrystalBlock;
import net.minecraft.block.Block;
import net.minecraft.item.DyeColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Uses Deferred registry
 * which is a safe way to posticipate Mod Objects initialization as much as possible until they're needed in the game
 * <p>
 * It also initializes registry names automatically without needing to set them anywhere else in the mod
 */
public class ModBlocks {

    private static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, CrystalGlass.MODID);

    //Initialize the deferred registries
    public static void init()
    {
        REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Registers the Crystal Cluster Block and its related BlockItem; TODO: this can be later rearranged to be way more polished less verbose
    public static final RegistryObject<CrystalClusterBlock> CRYSTAL_CLUSTER_BLOCK = REGISTER.register("crystal_cluster", CrystalClusterBlock::new);

    public static final List<RegistryObject<CrystalBlock>> CRYSTAL_BLOCKS = Lists.newArrayListWithCapacity(CrystalBlock.Size.values().length);

    static
    {
        for (CrystalBlock.Size size : CrystalBlock.Size.values())
        {
            Pair<String, Supplier<CrystalBlock>> blockPair = CrystalBlock.create(size);
            CRYSTAL_BLOCKS.add(REGISTER.register(blockPair.getLeft(), blockPair.getRight()));
        }
    }


    //public static final RegistryObject<FullCrystalBlock> FULL_CRYSTAL_BLOCKS = BLOCKS.register("crystal_block", FullCrystalBlock::new);


    public static Map<DyeColor, RegistryObject<FullCrystalBlock>> FULL_CRYSTAL_BLOCKS = new HashMap<>();

    static
    {

        for (DyeColor color : DyeColor.values())
        {

            String name = "crystal_block_" + color.getTranslationKey();

            RegistryObject<FullCrystalBlock> CRYSTALBLOCK_DYE = REGISTER.register(name, FullCrystalBlock::new);

            FULL_CRYSTAL_BLOCKS.put(color, CRYSTALBLOCK_DYE);
        }

        String name = "crystal_block";

        RegistryObject<FullCrystalBlock> CRYSTALBLOCK = REGISTER.register(name, FullCrystalBlock::new);

        FULL_CRYSTAL_BLOCKS.put(null, CRYSTALBLOCK);

    }

    //public static final RegistryObject<FlowingFluidBlock> CRYSTAL_BLOCK = REGISTER.register("molten_crystal",
    //        () -> new ForgeFluidBlock(() -> ModFluids.MOLTEN_CRYSTAL.get(), AbstractBlock.Properties.create(Material.LAVA)
    //        .doesNotBlockMovement().hardnessAndResistance(100f).noDrop()));
}
