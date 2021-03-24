package net.davoleo.crystalglass.block;

import net.davoleo.crystalglass.CrystalGlass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class CrystalClusterBlock extends Block {

    private static final DirectionProperty FACING = BlockStateProperties.FACING;
    private static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CrystalClusterBlock()
    {
        super(Properties.of(Material.STONE));
        this.setRegistryName(CrystalGlass.MODID, "crystal_cluster");
        Properties props = Properties.of(Material.STONE);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder)
    {
        stateBuilder.add(FACING, AGE, WATERLOGGED);
    }

    @Override
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerWorld world, @Nonnull BlockPos pos, @Nonnull Random random)
    {
        int age = state.getValue(AGE);
        boolean waterlogged = state.getValue(WATERLOGGED);
        if (age < 7)
        {
            if (random.nextInt(waterlogged ? 30 : 15) == 0)
                world.setBlock(pos, state.setValue(AGE, age + 1), 2);
        }
    }

    @Override
    public boolean isRandomlyTicking(@Nonnull BlockState state)
    {
        return state.getValue(AGE) < 7;
    }
}
