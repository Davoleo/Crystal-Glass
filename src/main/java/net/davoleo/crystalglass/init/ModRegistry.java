package net.davoleo.crystalglass.init;

import com.google.common.collect.Lists;
import net.davoleo.crystalglass.CrystalGlass;
import net.davoleo.crystalglass.block.CrystalBlock;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BlockStateProperties;
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
public class ModRegistry {

    private static final Item.Properties DEFAULT_ITEM_PROPERTIES = new Item.Properties().group(CrystalGlass.CREATIVE_TAB);

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CrystalGlass.MODID);

    //Initialize the deferred registries
    public static void init()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        Items.REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
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

    public static class Items {
        private static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, CrystalGlass.MODID);

        private static <T extends Block> RegistryObject<Item> registerFromBlock(RegistryObject<T> block)
        {
            return REGISTER.register(block.getId().getPath(), () -> new BlockItem(block.get(), DEFAULT_ITEM_PROPERTIES));
        }

        public static final List<RegistryObject<Item>> CRYSTAL_CLUSTERS = Lists.newArrayListWithCapacity(8);

        static
        {
            BlockStateProperties.AGE_0_7.getAllowedValues().forEach(age ->
                    CRYSTAL_CLUSTERS.add(REGISTER.register(
                            "crystal_cluster_age_" + age,
                            () -> new BlockItem(CRYSTAL_CLUSTER_BLOCK.get(), DEFAULT_ITEM_PROPERTIES))
                    )
            );
        }

        public static final List<RegistryObject<Item>> CRYSTALS = Lists.newArrayListWithCapacity(CrystalBlock.Size.values().length);

        static
        {
            for (CrystalBlock.Size size : CrystalBlock.Size.values())
                CRYSTALS.add(registerFromBlock(CRYSTAL_BLOCKS.get(size.ordinal())));
        }

    }

}
