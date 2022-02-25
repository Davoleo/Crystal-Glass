package net.davoleo.crystalglass.datagen;

import net.davoleo.crystalglass.CrystalGlass;
import net.davoleo.crystalglass.block.CrystalBlock;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.davoleo.crystalglass.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStateGenerator extends BlockStateProvider {

    public BlockStateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper)
    {
        super(gen, CrystalGlass.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        CrystalClusterBlock crystalCluster = ModBlocks.CRYSTAL_CLUSTER_BLOCK.get();

        //Register a directional block with an angle offset of 90 degrees and a different existing named model for each stage
        getVariantBuilder(crystalCluster).forAllStatesExcept(state -> ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(
                                new ResourceLocation(CrystalGlass.MODID, "block/crystal_cluster_age_" + state.getValue(CrystalClusterBlock.AGE))
                        ))
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                        .rotationX(state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE).ordinal() * 90)
                        .build()
                , BlockStateProperties.WATERLOGGED);

        //Generate Crystal Blocks blockstates
        for (CrystalBlock.Size size : CrystalBlock.Size.values())
        {
            getVariantBuilder(ModBlocks.CRYSTAL_BLOCKS.get(size.ordinal()).get()).forAllStatesExcept(state -> ConfiguredModel.builder()
                    .modelFile(
                            models().getExistingFile(
                                    new ResourceLocation(CrystalGlass.MODID, "block/" + size.name().toLowerCase() + "_crystal")
                            )
                    )
                    .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                    .rotationX(state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE).ordinal() * 90)
                    .build(), BlockStateProperties.WATERLOGGED);
        }

        //for (DyeColor color : ModBlocks.FULL_CRYSTAL_BLOCKS.keySet())
        //{
        //    //Generate Full Crystal Block blockstate
        //    simpleBlock(ModBlocks.FULL_CRYSTAL_BLOCKS.get(color).get());
        //}
    }
}
