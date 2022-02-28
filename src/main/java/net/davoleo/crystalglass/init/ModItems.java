package net.davoleo.crystalglass.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.minecraft.world.item.BlockItem;

import java.util.ArrayList;
import java.util.List;

import static net.davoleo.crystalglass.CrystalGlass.REGISTRATE;

public class ModItems {

    public static final List<RegistryEntry<BlockItem>> CRYSTAL_CLUSTERS = new ArrayList<>(CrystalClusterBlock.AGE.getPossibleValues().size());
    static
    {
        for (int age : CrystalClusterBlock.AGE.getPossibleValues())
        {
            CRYSTAL_CLUSTERS.add(REGISTRATE
                    .item("crystal_cluster_age_" + age, (props) -> new BlockItem(ModBlocks.CRYSTAL_CLUSTER_BLOCK.get(), props))
                    .defaultModel()
                    .register()
            );
        }
    }

}