package net.davoleo.crystalglass.init;

import net.davoleo.crystalglass.CrystalGlass;
import net.davoleo.crystalglass.block.CrystalBlock;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModItems {

    private static final Item.Properties DEFAULT_ITEM_PROPERTIES = new Item.Properties().group(CrystalGlass.CREATIVE_TAB);

    private static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, CrystalGlass.MODID);

    public static void init()
    {
        REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private static <T extends Block> RegistryObject<Item> registerFromBlock(RegistryObject<T> block)
    {
        return REGISTER.register(block.getId().getPath(), () -> new BlockItem(block.get(), DEFAULT_ITEM_PROPERTIES));
    }

    public static final List<RegistryObject<Item>> CRYSTAL_CLUSTERS = new ArrayList<>(CrystalClusterBlock.AGE.getAllowedValues().size());

    static
    {
        CrystalClusterBlock.AGE.getAllowedValues().forEach(age ->
                CRYSTAL_CLUSTERS.add(REGISTER.register(
                        "crystal_cluster_age_" + age,
                        () -> new BlockItem(ModBlocks.CRYSTAL_CLUSTER_BLOCK.get(), DEFAULT_ITEM_PROPERTIES))
                )
        );
    }

    public static final List<RegistryObject<Item>> CRYSTALS = new ArrayList<>(CrystalBlock.Size.values().length);


    public static final Map<DyeColor, RegistryObject<Item>> CRYSTALS_BLOCK_ITEMS = new HashMap<>();

    static
    {
        for (CrystalBlock.Size size : CrystalBlock.Size.values())
            CRYSTALS.add(registerFromBlock(ModBlocks.CRYSTAL_BLOCKS.get(size.ordinal())));
        for (DyeColor color : ModBlocks.FULL_CRYSTAL_BLOCKS.keySet())
        {
            RegistryObject<Item> CRYSTAL_ITEMBLOCK = registerFromBlock(ModBlocks.FULL_CRYSTAL_BLOCKS.get(color));
            CRYSTALS_BLOCK_ITEMS.put(color, CRYSTAL_ITEMBLOCK);
        }

    }

    public static final RegistryObject<Item> CRYSTAL_BUCKET = REGISTER.register("molten_crystal_bucket",
            () -> new BucketItem(ModFluids.MOLTEN_CRYSTAL, new Item.Properties().maxStackSize(1))
    );

}