package net.davoleo.crystalglass.init;

import com.google.common.collect.Lists;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.davoleo.crystalglass.CrystalGlass;
import net.davoleo.crystalglass.block.CrystalBlock;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.davoleo.crystalglass.util.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                    .loot(NonNullBiConsumer.noop())
                    .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .blockstate((genContext, provider) -> provider.horizontalFaceBlock(
                            genContext.getEntry(),
                            provider.models().getExistingFile(new ResourceLocation(CrystalGlass.MODID, "crystal_cluster_"))
                    ))
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
                            .loot(NonNullBiConsumer.noop())
                            .simpleItem()
                            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                            .register()
            );
        }
    }

    public static Map<DyeColor, RegistryEntry<Block>> CRYSTAL_GLASS_BLOCKS = new HashMap<>();

    static
    {
        for (DyeColor color : DyeColor.values())
        {
            String name = "crystal_glass_" + color.getSerializedName();
            RegistryEntry<Block> CRYSTAL_GLASS_DYE = REGISTRATE
                    .object(name)
                    .block((properties) -> new Block(Utils.DEFAULT_GLASS_PROPERTIES))
                    .blockstate((gencontext, registrateProvider) -> registrateProvider.simpleBlock(gencontext.getEntry()))
                    .simpleItem()
                    .tag(BlockTags.MINEABLE_WITH_PICKAXE, Tags.Blocks.GLASS)
                    .register();

            CRYSTAL_GLASS_BLOCKS.put(color, CRYSTAL_GLASS_DYE);
        }

        RegistryEntry<Block> CRYSTAL_GLASS = REGISTRATE
                .object("crystal_glass")
                .block((properties) -> new Block(Utils.DEFAULT_GLASS_PROPERTIES))
                .blockstate((gencontext, registrateProvider) -> registrateProvider.simpleBlock(gencontext.getEntry()))
                .simpleItem()
                .tag(BlockTags.MINEABLE_WITH_PICKAXE, Tags.Blocks.GLASS)
                .register();
        CRYSTAL_GLASS_BLOCKS.put(null, CRYSTAL_GLASS);
    }

}
