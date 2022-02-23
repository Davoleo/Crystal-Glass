package net.davoleo.crystalglass.block;

import net.davoleo.crystalglass.init.ModBlocks;
import net.davoleo.crystalglass.util.ShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class CrystalBlock extends FaceAttachedHorizontalDirectionalBlock implements SimpleWaterloggedBlock {

    protected static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final BooleanProperty UP = BlockStateProperties.UP;
    protected static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    public enum Size {
        SMALL(new Vec3(7, 0, 7), new Vec3(9, 8, 9)),
        MEDIUM(new Vec3(6.5, 0, 6.5), new Vec3(9.5, 12, 9.5)),
        LARGE(new Vec3(6, 0, 6), new Vec3(10, 16, 10));

        private final VoxelShape[] shapes = new VoxelShape[Direction.values().length];

        Size(Vec3 shapeStart, Vec3 shapeEnd)
        {
            VoxelShape shape = Block.box(shapeStart.x, shapeStart.y, shapeStart.z, shapeEnd.x, shapeEnd.y, shapeEnd.z);
            for (int i = 0; i < Direction.values().length; i++)
            {
                shapes[i] = ShapeUtils.alignBox(shape, Direction.UP, Direction.values()[i]);
            }
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
                direction = Direction.DOWN;
            if (attachFace == AttachFace.FLOOR)
                direction = Direction.UP;

            return shapes[direction.get3DDataValue()];
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
                .emissiveRendering((p1, p2, p3) -> true)
                .lightLevel(size::getLightLevel)
        );
        this.size = size;
    }

    /**
     * this only exists to be called by subclasses
     */
    protected CrystalBlock(ToIntFunction<BlockState> stateLightFunction)
    {
        super(FullCrystalBlock.PROPERTIES
                .emissiveRendering((p1, p2, p3) -> true)
                .lightLevel(stateLightFunction)
        );
        this.size = null;
    }

    @ParametersAreNonnullByDefault
    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context)
    {
        if (size != null)
            return size.getCrystalShape(state.getValue(HORIZONTAL_FACING), state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE));
        else
            return super.getShape(state, worldIn, pos, context);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player)
    {
        if (size == null)
            return super.getCloneItemStack(state, target, world, pos, player);

        return new ItemStack(ModBlocks.CRYSTAL_BLOCKS.get(size.ordinal()).get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, FaceAttachedHorizontalDirectionalBlock.FACE, WATERLOGGED);
    }

    @Nullable
    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context)
    {
        BlockState state = super.getStateForPlacement(context);

        if (state == null)
            return null;

        /*
        BlockState up = world.getBlockState(pos.above());
        state = state.setValue(UP, up.getBlock() == state.getBlock());

        BlockState down = world.getBlockState(pos.below());
        state = state.setValue(DOWN, down.getBlock() == state.getBlock());
        */

        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState fluidstate = world.getFluidState(pos);
        return state.setValue(WATERLOGGED, fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8);
    }

    /**
     * @return the state of the fluid inside of the block if it's waterlogged
     */
    @Nonnull
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean isValidSpawn(BlockState state, BlockGetter world, BlockPos pos, SpawnPlacements.Type type, EntityType<?> entityType)
    {
        return true;
    }


    @ParametersAreNonnullByDefault
    @Nonnull
    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos)
    {
        if (stateIn.getValue(WATERLOGGED))
            world.scheduleTick(currentPos, Fluids.WATER.getSource(), Fluids.WATER.getTickDelay(world));

        /*
        BlockState newState = super.updateShape(stateIn, facing, facingState, world, currentPos, facingPos);
        if (facing == Direction.UP)
        {
            newState = newState.setValue(UP, facingState.getBlock() == stateIn.getBlock());
        }
        else if (facing == Direction.DOWN)
        {
            newState = newState.setValue(DOWN, facingState.getBlock() == stateIn.getBlock());
        }
        return newState;
         */
        return super.updateShape(stateIn, facing, facingState, world, currentPos, facingPos);
    }


}
