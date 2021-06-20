package net.davoleo.crystalglass.init;

import com.google.common.collect.Lists;
import net.davoleo.crystalglass.CrystalGlass;
import net.davoleo.crystalglass.block.CrystalBlock;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ModItems {

    private static final Item.Properties DEFAULT_ITEM_PROPERTIES = new Item.Properties().group(CrystalGlass.CREATIVE_TAB);

    public static void init()
    {
        REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, CrystalGlass.MODID);

    private static <T extends Block> RegistryObject<Item> registerFromBlock(RegistryObject<T> block)
    {
        return REGISTER.register(block.getId().getPath(), () -> new BlockItem(block.get(), DEFAULT_ITEM_PROPERTIES));
    }

    public static final List<RegistryObject<Item>> CRYSTAL_CLUSTERS = Lists.newArrayListWithCapacity(8);

    static
    {
        CrystalClusterBlock.AGE.getAllowedValues().forEach(age ->
                CRYSTAL_CLUSTERS.add(REGISTER.register(
                        "crystal_cluster_age_" + age,
                        () -> new BlockItem(ModBlocks.CRYSTAL_CLUSTER_BLOCK.get(), DEFAULT_ITEM_PROPERTIES))
                )
        );
    }

    public static final List<RegistryObject<Item>> CRYSTALS = Lists.newArrayListWithCapacity(CrystalBlock.Size.values().length);

    static
    {
        for (CrystalBlock.Size size : CrystalBlock.Size.values())
            CRYSTALS.add(registerFromBlock(ModBlocks.CRYSTAL_BLOCKS.get(size.ordinal())));
    }

}
