package net.davoleo.crystalglass.block;

import net.davoleo.crystalglass.init.ModRegistry;
import net.davoleo.crystalglass.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class CrystalBlock extends HorizontalFaceBlock implements IWaterLoggable {

    protected static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public enum Size {
        SMALL,
        MEDIUM,
        LARGE;

        /**
         * @return 4 | 8 | 12
         */
        private int getLightLevel(BlockState state)
        {
            return 4 + (4 * this.ordinal());
        }
    }

    @Nullable
    private final Size size;

    public static Pair<String, Supplier<CrystalBlock>> create(Size size)
    {
        String blockName = size.name().toLowerCase() + "_crystal";
        return Pair.of(blockName, () -> new CrystalBlock(size));
    }

    private CrystalBlock(Size size)
    {
        super(Utils.DEFAULT_ROCK_PROPERTIES
                .setEmmisiveRendering((p1, p2, p3) -> true)
                .setLightLevel(size::getLightLevel)
                .setOpaque((p1, p2, p3) -> false)
                .notSolid()
        );
        this.size = size;
    }

    /**
     * this only exists to be called by subclasses
     */
    protected CrystalBlock(ToIntFunction<BlockState> stateLightFunction)
    {
        super(Utils.DEFAULT_ROCK_PROPERTIES
                .setEmmisiveRendering((p1, p2, p3) -> true)
                .setLightLevel(stateLightFunction)
                .setOpaque((p1, p2, p3) -> false)
                .notSolid()
        );
        this.size = null;
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
        if (size == null)
            return super.getPickBlock(state, target, world, pos, player);

        return new ItemStack(ModRegistry.Items.CRYSTALS.get(size.ordinal()).get());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, HorizontalFaceBlock.FACE, WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context)
    {
        BlockState state = super.getStateForPlacement(context);

        if (state == null)
            return null;

        //Set the waterlogged state
        boolean waterlogged = context.getWorld().hasWater(context.getPos());
        state = state.with(WATERLOGGED, waterlogged);

        return state;
    }

    /**
     * @return the state of the fluid inside of the block if it's waterlogged
     */
    @Nonnull
    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}
