package net.davoleo.crystalglass.datagen;

import net.davoleo.crystalglass.CrystalGlass;
import net.davoleo.crystalglass.block.BlockCrystalCluster;
import net.davoleo.crystalglass.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
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
        BlockCrystalCluster crystalCluster = ModRegistry.CRYSTAL_CLUSTER_BLOCK.get();

        //Register a directional block with an angle offset of 90 degrees and a different existing named model for each stage
        getVariantBuilder(crystalCluster).forAllStatesExcept(state -> {
            Direction dir = state.get(BlockStateProperties.FACING);
            return ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(
                            new ResourceLocation(CrystalGlass.MODID, "block/crystal_cluster_age_" + state.get(BlockStateProperties.AGE_0_7))
                    ))
                    .rotationX(dir == Direction.DOWN ? 180 : dir.getAxis().isHorizontal() ? 90 : 0)
                    .rotationY(dir.getAxis().isVertical() ? 0 : (int) dir.getHorizontalAngle() % 360)
                    .build();
        }, BlockStateProperties.WATERLOGGED);
    }
}
