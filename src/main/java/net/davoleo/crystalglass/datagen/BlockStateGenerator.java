package net.davoleo.crystalglass.datagen;

import net.davoleo.crystalglass.CrystalGlassMod;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.davoleo.crystalglass.block.CrystalShardBlock;
import net.davoleo.crystalglass.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStateGenerator extends BlockStateProvider {

    public BlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CrystalGlassMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        CrystalClusterBlock crystalCluster = ModBlocks.CRYSTAL_CLUSTER.get();

        //Register a directional block with an angle offset of 90 degrees and a different existing named model for each stage
        getVariantBuilder(crystalCluster).forAllStatesExcept(state -> ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(
                                new ResourceLocation(CrystalGlassMod.MODID, "block/crystal_cluster_age_" + state.getValue(CrystalClusterBlock.AGE))
                        ))
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                        .rotationX(state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE).ordinal() * 90)
                        .build()
                , BlockStateProperties.WATERLOGGED);

        //Generate Crystal Blocks blockstates
        for (CrystalShardBlock.Size size : CrystalShardBlock.Size.values()) {
            getVariantBuilder(ModBlocks.CRYSTAL_SHARDS.get(size.ordinal()).get()).forAllStatesExcept(state -> ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/" + size.name().toLowerCase() + "_crystal_shard")))
                    .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                    .rotationX(state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE).ordinal() * 90)
                    .build(), BlockStateProperties.WATERLOGGED);
        }

        simpleBlock(ModBlocks.MOLTEN_CRYSTAL_BLOCK.get(), models().getExistingFile(modLoc("block/molten_crystal")));

        //for (DyeColor color : ModBlocks.FULL_CRYSTAL_BLOCKS.keySet())
        //{
        //    //Generate Full Crystal Block blockstate
        //    simpleBlock(ModBlocks.FULL_CRYSTAL_BLOCKS.get(color).get());
        //}
    }
}
