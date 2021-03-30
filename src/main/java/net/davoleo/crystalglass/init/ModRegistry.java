package net.davoleo.crystalglass.init;

import com.google.common.collect.Lists;
import net.davoleo.crystalglass.CrystalGlass;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

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

    public static class Items {
        private static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, CrystalGlass.MODID);

        public static final List<RegistryObject<Item>> CRYSTAL_CLUSTERS = Lists.newArrayList();

        static
        {
            BlockStateProperties.AGE_0_7.getAllowedValues().forEach(age ->
                    CRYSTAL_CLUSTERS.add(REGISTER.register("crystal_cluster_age_" + age, () -> new BlockItem(CRYSTAL_CLUSTER_BLOCK.get(), DEFAULT_ITEM_PROPERTIES)))
            );
        }

    }

}
