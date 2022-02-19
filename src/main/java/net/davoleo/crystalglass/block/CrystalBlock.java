package net.davoleo.crystalglass.block;

import net.davoleo.crystalglass.init.ModItems;
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
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class CrystalBlock extends HorizontalFaceBlock implements IWaterLoggable {

    protected static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final BooleanProperty UP = BlockStateProperties.UP;
    protected static final BooleanProperty DOWN = BlockStateProperties.DOWN;


    public enum Size {
        SMALL(new Vector3f(7, 0, 7), new Vector3f(9, 8, 9)),
        MEDIUM(new Vector3f(6.5F, 0, 6.5F), new Vector3f(9.5F, 12, 9.5F)),
        LARGE(new Vector3f(6, 0, 6), new Vector3f(10, 16, 10));

        private final VoxelShape[] shapes;

        Size(Vector3f shapeStart, Vector3f shapeEnd)
        {
            shapes = Utils.generateDirectionalVoxelShapes(shapeStart, shapeEnd);
        }

        /**
         * @return 4 | 8 | 12
         */
        private int getLightLevel(BlockState state)
        {
            return 4 + (4 * this.ordinal());
        }

        public VoxelShape getCrystalShape(Direction direction, AttachFace attachFace)
        {
            if (attachFace == AttachFace.CEILING)
                return shapes[4];

            if (attachFace == AttachFace.FLOOR)
                return shapes[5];

            int horizIndex = direction.getHorizontalIndex();
            if (horizIndex != -1)
            {
                return shapes[horizIndex];
            }

            return shapes[5];
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
        super(FullCrystalBlock.PROPERTIES
                .setEmmisiveRendering((p1, p2, p3) -> true)
                .setLightLevel(size::getLightLevel)
        );
        this.size = size;
    }

    /**
     * this only exists to be called by subclasses
     */
    protected CrystalBlock(ToIntFunction<BlockState> stateLightFunction)
    {
        super(FullCrystalBlock.PROPERTIES
                .setEmmisiveRendering((p1, p2, p3) -> true)
                .setLightLevel(stateLightFunction)
        );
        this.size = null;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context)
    {
        if (size != null)
            return size.getCrystalShape(state.get(BlockStateProperties.HORIZONTAL_FACING), state.get(HorizontalFaceBlock.FACE));
        else
            return super.getShape(state, worldIn, pos, context);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
        if (size == null)
            return super.getPickBlock(state, target, world, pos, player);

        return new ItemStack(ModItems.CRYSTALS.get(size.ordinal()).get());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, HorizontalFaceBlock.FACE, WATERLOGGED, UP, DOWN);
    }


    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        BlockPos pos = context.getPos();
        World world = context.getWorld();
        BlockState up = world.getBlockState(pos.up());
        state = state.with(UP, up.getBlock() == state.getBlock());
        BlockState down = world.getBlockState(pos.down());
        state = state.with(DOWN, down.getBlock() == state.getBlock());
        return state.with(WATERLOGGED, fluidstate.isTagged(FluidTags.WATER) && fluidstate.getLevel() == 8);
    }


    /**
     * @return the state of the fluid inside of the block if it's waterlogged
     */
    @Nonnull
    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return true;
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (stateIn.get(WATERLOGGED))
        {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        BlockState newState = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        if (facing == Direction.UP)
        {
            newState = newState.with(UP, facingState.getBlock() == stateIn.getBlock());
        } else if (facing == Direction.DOWN)
        {
            newState = newState.with(DOWN, facingState.getBlock() == stateIn.getBlock());
        }
        return newState;
    }


}
