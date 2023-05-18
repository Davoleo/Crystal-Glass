package net.davoleo.crystalglass.init;

import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.minecraft.Util;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public final class ModItems extends ModRegistry {

    public static void init()
    {
        ModBlocks.CRYSTAL_SHARDS.forEach(ModItems::registerBlockItem);
    }

    public static final List<RegistryObject<BlockItem>> CRYSTAL_CLUSTERS = Util.make(
            new ArrayList<>(CrystalClusterBlock.AGE.getPossibleValues().size()), clusters ->
                    CrystalClusterBlock.AGE.getPossibleValues().forEach(age ->
                            clusters.add(ITEMS.register("crystal_cluster_age_" + age,
                                    () -> new BlockItem(ModBlocks.CRYSTAL_CLUSTER.get(), new Item.Properties())
                            )))
    );

    public static final RegistryObject<BucketItem> MOLTEN_CRYSTAL_BUCKET =
            ITEMS.register(
                    "molten_crystal_bucket",
                    () -> new BucketItem(ModFluids.MOLTEN_CRYSTAL, new Item.Properties()
                            .craftRemainder(Items.BUCKET)
                            .stacksTo(1)
                    )
            );

    public static void registerBlockItem(RegistryObject<? extends Block> block)
    {
        ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }
}