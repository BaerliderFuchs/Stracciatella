package com.baerliderfuchs.stracciatella.objects.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFrameItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FoodBowl extends HorizontalBlock {

    public static List<Item> CONTENT = new ArrayList<Item>();
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(4, 0, 4, 12, 1, 12),
            Block.makeCuboidShape(11, 1, 6, 12, 3, 10),
            Block.makeCuboidShape(4, 1, 6, 5, 3, 10),
            Block.makeCuboidShape(6, 1, 4, 10, 3, 5),
            Block.makeCuboidShape(6, 1, 11, 10, 3, 12),
            Block.makeCuboidShape(11.242640687119287, 1, 6, 12.242640687119287, 3, 10),
            Block.makeCuboidShape(4.171572875253808, 1, 6, 5.171572875253808, 3, 10)
    ).reduce((v1, v2) -> {
        return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
    }).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(4, 0, 4, 12, 1, 12),
            Block.makeCuboidShape(4, 1, 6, 5, 3, 10),
            Block.makeCuboidShape(11, 1, 6, 12, 3, 10),
            Block.makeCuboidShape(6, 1, 11, 10, 3, 12),
            Block.makeCuboidShape(6, 1, 4, 10, 3, 5),
            Block.makeCuboidShape(3.7573593128807126, 1, 6, 4.757359312880713, 3, 10),
            Block.makeCuboidShape(6, 1, 10.828427124746192, 10, 3, 11.828427124746192)
    ).reduce((v1, v2) -> {
        return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
    }).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(4, 0, 4, 12, 1, 12),
            Block.makeCuboidShape(6, 1, 11, 10, 3, 12),
            Block.makeCuboidShape(6, 1, 4, 10, 3, 5),
            Block.makeCuboidShape(11, 1, 6, 12, 3, 10),
            Block.makeCuboidShape(4, 1, 6, 5, 3, 10),
            Block.makeCuboidShape(6, 1, 11.242640687119287, 10, 3, 12.242640687119287),
            Block.makeCuboidShape(10.828427124746192, 1, 6, 11.828427124746192, 3, 10)
    ).reduce((v1, v2) -> {
        return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
    }).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(4, 0, 4, 12, 1, 12),
            Block.makeCuboidShape(6, 1, 4, 10, 3, 5),
            Block.makeCuboidShape(6, 1, 11, 10, 3, 12),
            Block.makeCuboidShape(4, 1, 6, 5, 3, 10),
            Block.makeCuboidShape(11, 1, 6, 12, 3, 10),
            Block.makeCuboidShape(6, 1, 3.7573593128807126, 10, 3, 4.757359312880713),
            Block.makeCuboidShape(4.171572875253808, 1, 6, 5.171572875253808, 3, 10)
    ).reduce((v1, v2) -> {
        return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
    }).get();

    public FoodBowl(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case NORTH:
                return SHAPE_N;
            case SOUTH:
                return SHAPE_S;
            case EAST:
                return SHAPE_E;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }


    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (!worldIn.isRemote()) {
            if (playerIn.isCrouching() && playerIn.inventory.getCurrentItem().isEmpty()) {
                playerIn.inventory.addItemStackToInventory(new ItemStack(CONTENT.get(CONTENT.size()-1)));
                CONTENT.remove(CONTENT.get(CONTENT.size()-1));
            } else if (playerIn.inventory.getCurrentItem().isFood()) {
                if (playerIn.inventory.getCurrentItem().getItem().getFood().isMeat()) {
                    CONTENT.add(playerIn.inventory.getCurrentItem().getItem());
                    playerIn.sendMessage(new StringTextComponent(CONTENT.toString()));
                    playerIn.inventory.getCurrentItem().shrink(1);
                }
            }
        }
        return ActionResultType.SUCCESS;
    }
}
