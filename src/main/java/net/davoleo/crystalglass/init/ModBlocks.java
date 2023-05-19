package net.davoleo.crystalglass.init;

import com.google.common.collect.Lists;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.davoleo.crystalglass.CrystalGlassMod;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.davoleo.crystalglass.block.CrystalShardBlock;
import net.davoleo.crystalglass.util.DefaultProperties;
import net.minecraft.Util;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Uses Deferred registry
 * which is a safe way to postpone Mod Objects initialization as much as possible until they're needed in the game
 * <p>
 * It also initializes registry names automatically without needing to set them anywhere else in the mod
 */
public final class ModBlocks extends ModRegistry {

    public static void init()
    {
    }

    //Registers the Crystal Cluster Block
    public static final RegistryObject<CrystalClusterBlock> CRYSTAL_CLUSTER = BLOCKS.register("crystal_cluster", CrystalClusterBlock::new);

    //Crystal Shards
    public static final List<RegistryObject<CrystalShardBlock>> CRYSTAL_SHARDS = Util.make(
            Lists.newArrayListWithCapacity(CrystalShardBlock.Size.values().length),
            shards -> {
                for (CrystalShardBlock.Size size : CrystalShardBlock.Size.values())
                {
                    Pair<String, Supplier<CrystalShardBlock>> blockPair = CrystalShardBlock.create(size);
                    shards.add(BLOCKS.register(
                            blockPair.getLeft(), blockPair.getRight()
                    ));
                }
            });

    //LiquidBlock: Molten Crystal
    public static final RegistryObject<LiquidBlock> MOLTEN_CRYSTAL_BLOCK =
            BLOCKS.register(
                    "molten_crystal",
                    () -> new LiquidBlock(ModFluids.MOLTEN_CRYSTAL, BlockBehaviour.Properties.copy(Blocks.LAVA))
            );

    //Decorative FullBlock: Crystal Block
//    public static final RegistryEntry<Block> BASE_CRYSTAL_BLOCK = CrystalGlassMod.REGISTRATE
//            .object("crystal_full_block")
//            .block(properties -> new Block(DefaultProperties.CRYSTAL_BLOCK))
//            .blockstate((gencontext, registrateProvider) -> registrateProvider.simpleBlock(gencontext.getEntry()))
//            .simpleItem()
//            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
//            .register();

    //public static final Map<DyeColor, RegistryEntry<Block>> COLORED_CRYSTAL_BLOCKS = registerColoredBlocks("full", properties -> properties);


    private static Map<DyeColor, RegistryEntry<Block>> registerColoredBlocks(String blockType, UnaryOperator<BlockBehaviour.Properties> transformer) {
        EnumMap<DyeColor, RegistryEntry<Block>> coloredBlocks = new EnumMap<>(DyeColor.class);

        for (DyeColor color : DyeColor.values()) {
            RegistryEntry<Block> crystalBlock = CrystalGlassMod.REGISTRATE
                    .object(color.getSerializedName() + "_crystal_" + blockType + "_block")
                    .block((properties) -> new Block(transformer.apply(DefaultProperties.CRYSTAL_BLOCK).color(color.getMaterialColor())))
                    .blockstate((gencontext, registrateProvider) -> registrateProvider.simpleBlock(gencontext.getEntry()))
                    .simpleItem()
                    .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .register();

            coloredBlocks.put(color, crystalBlock);
        }
        return coloredBlocks;
    }
}
