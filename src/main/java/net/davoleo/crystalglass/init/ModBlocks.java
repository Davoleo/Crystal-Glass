package net.davoleo.crystalglass.init;

import com.google.common.collect.Lists;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.davoleo.crystalglass.block.CrystalBlock;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.Supplier;

import static net.davoleo.crystalglass.CrystalGlass.REGISTRATE;

/**
 * Uses Deferred registry
 * which is a safe way to posticipate Mod Objects initialization as much as possible until they're needed in the game
 * <p>
 * It also initializes registry names automatically without needing to set them anywhere else in the mod
 */
public class ModBlocks {

    //Registers the Crystal Cluster Block and its related BlockItem
    public static final RegistryEntry<CrystalClusterBlock> CRYSTAL_CLUSTER_BLOCK =
            REGISTRATE.object("crystal_cluster")
                    .block(Material.AMETHYST, properties -> new CrystalClusterBlock())
                    .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .register();

    public static final List<RegistryEntry<CrystalBlock>> CRYSTAL_BLOCKS = Lists.newArrayListWithCapacity(CrystalBlock.Size.values().length);
    static
    {
        for (CrystalBlock.Size size : CrystalBlock.Size.values())
        {
            Pair<String, Supplier<CrystalBlock>> blockPair = CrystalBlock.create(size);
            CRYSTAL_BLOCKS.add(
                    REGISTRATE.object(blockPair.getLeft())
                            .block(Block.class, props -> blockPair.getRight().get())
                            .simpleItem()
                            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                            .register()
            );
        }
    }

    //public static Map<DyeColor, RegistryEntry<FullCrystalBlock>> FULL_CRYSTAL_BLOCKS = new HashMap<>();
    //static
    //{
    //    for (DyeColor color : DyeColor.values())
    //    {
    //        String name = "crystal_block_" + color.getSerializedName();
    //        RegistryEntry<FullCrystalBlock> CRYSTALBLOCK_DYE = REGISTRATE.object(name).simple(Block.class, FullCrystalBlock::new);
    //        FULL_CRYSTAL_BLOCKS.put(color, CRYSTALBLOCK_DYE);
    //    }
    //
    //    RegistryEntry<FullCrystalBlock> CRYSTALBLOCK = REGISTRATE.object("crystal_block").simple(Block.class, FullCrystalBlock::new);
    //    FULL_CRYSTAL_BLOCKS.put(null, CRYSTALBLOCK);
    //}

}
