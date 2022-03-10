package net.davoleo.crystalglass.init;

import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.davoleo.crystalglass.util.DefaultProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public final class ModItems extends ModRegistry {

    public static final List<RegistryObject<BlockItem>> CRYSTAL_CLUSTERS = new ArrayList<>(CrystalClusterBlock.AGE.getPossibleValues().size());

    static
    {
        for (int age : CrystalClusterBlock.AGE.getPossibleValues())
            ITEMS.register("crystal_cluster_age_" + age, () -> new BlockItem(ModBlocks.CRYSTAL_CLUSTER.get(), DefaultProperties.ITEM));
    }

}