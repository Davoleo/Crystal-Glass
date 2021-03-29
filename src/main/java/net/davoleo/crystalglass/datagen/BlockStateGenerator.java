package net.davoleo.crystalglass.datagen;

import net.davoleo.crystalglass.CrystalGlass;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.davoleo.crystalglass.init.ModRegistry;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
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
        CrystalClusterBlock crystalCluster = ModRegistry.CRYSTAL_CLUSTER_BLOCK.get();

        //Register a directional block with an angle offset of 90 degrees and a different existing named model for each stage
        getVariantBuilder(crystalCluster).forAllStatesExcept(state -> ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(
                                new ResourceLocation(CrystalGlass.MODID, "block/crystal_cluster_age_" + state.get(BlockStateProperties.AGE_0_7))
                        ))
                        .rotationY(((int) state.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalAngle() + 180) % 360)
                        .rotationX(state.get(HorizontalFaceBlock.FACE).ordinal() * 90)
                        .build()
                , BlockStateProperties.WATERLOGGED);
    }
}
