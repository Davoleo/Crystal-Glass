package net.davoleo.crystalglass.init;

import com.google.common.collect.Lists;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.davoleo.crystalglass.block.CrystalShardBlock;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.Supplier;

/**
 * Uses Deferred registry
 * which is a safe way to postpone Mod Objects initialization as much as possible until they're needed in the game
 * <p>
 * It also initializes registry names automatically without needing to set them anywhere else in the mod
 */
public final class ModBlocks extends ModRegistry {

    //Registers the Crystal Cluster Block and its related BlockItem
    public static final RegistryObject<CrystalClusterBlock> CRYSTAL_CLUSTER = BLOCKS.register("crystal_cluster", CrystalClusterBlock::new);

    //REGISTRATE.object("crystal_cluster")
    //        .block(Material.AMETHYST, properties -> new CrystalClusterBlock())
    //        .loot(NonNullBiConsumer.noop())
    //        .tag(BlockTags.MINEABLE_WITH_PICKAXE)
    //                .blockstate((genContext, provider) -> provider.horizontalFaceBlock(
    //        genContext.getEntry(),
    //        provider.models().getExistingFile(new ResourceLocation(CrystalGlassMod.MODID, "crystal_cluster_"))
    //        ))
    //        .register()

    public static final List<RegistryObject<CrystalShardBlock>> CRYSTAL_SHARDS = Lists.newArrayListWithCapacity(CrystalShardBlock.Size.values().length);

    static
    {
        for (CrystalShardBlock.Size size : CrystalShardBlock.Size.values())
        {
            Pair<String, Supplier<CrystalShardBlock>> blockPair = CrystalShardBlock.create(size);
            CRYSTAL_SHARDS.add(BLOCKS.register(
                    blockPair.getLeft(), blockPair.getRight()
            ));
        }
    }

    /*
    public static Map<DyeColor, RegistryEntry<Block>> CRYSTAL_GLASS_BLOCKS = new HashMap<>();
    static
    {
        for (DyeColor color : DyeColor.values())
        {
            String name = "crystal_glass_" + color.getSerializedName();
            RegistryEntry<Block> CRYSTAL_GLASS_DYE = REGISTRATE
                    .object(name)
                    .block((properties) -> new Block(DefaultProperties.GLASS_BLOCK))
                    .blockstate((gencontext, registrateProvider) -> registrateProvider.simpleBlock(gencontext.getEntry()))
                    .simpleItem()
                    .tag(BlockTags.MINEABLE_WITH_PICKAXE, Tags.Blocks.GLASS)
                    .register();

            CRYSTAL_GLASS_BLOCKS.put(color, CRYSTAL_GLASS_DYE);
        }

        RegistryEntry<Block> CRYSTAL_GLASS = REGISTRATE
                .object("crystal_glass")
                .block((properties) -> new Block(DefaultProperties.GLASS_BLOCK))
                .blockstate((gencontext, registrateProvider) -> registrateProvider.simpleBlock(gencontext.getEntry()))
                .simpleItem()
                .tag(BlockTags.MINEABLE_WITH_PICKAXE, Tags.Blocks.GLASS)
                .register();
        CRYSTAL_GLASS_BLOCKS.put(null, CRYSTAL_GLASS);
    }
     */
}
