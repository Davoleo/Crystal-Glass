package net.davoleo.crystalglass.datagen;

import net.davoleo.crystalglass.CrystalGlass;
import net.davoleo.crystalglass.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStateGenerator extends BlockStateProvider {

    public BlockStateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper)
    {
        super(gen, CrystalGlass.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        //Crystal Cluster
        this.directionalBlock(
                ModRegistry.CRYSTAL_CLUSTER_BLOCK.get(),
                state -> models().getExistingFile(
                        new ResourceLocation(
                                CrystalGlass.MODID,
                                "models/block/crystal_cluster_age_" + state.get(BlockStateProperties.AGE_0_7)
                        )
                )
        );
    }
}
