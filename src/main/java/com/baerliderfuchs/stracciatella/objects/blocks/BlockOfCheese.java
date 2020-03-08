package com.baerliderfuchs.stracciatella.objects.blocks;


import com.baerliderfuchs.stracciatella.Stracciatella;
import com.baerliderfuchs.stracciatella.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.GameData;
import sun.security.provider.SHA;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class BlockOfCheese extends Block {

    public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 3);

    private static final VoxelShape[] SHAPES = {
            Block.makeCuboidShape(1.9965093539013399, 0.000006092332930585087, 2.0034845643895878, 13.99650935390134, 6.000006092332931, 14.003484564389588),
            VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 0, 8, 14, 6, 14), Block.makeCuboidShape(2, 0, 2, 8, 6, 8), IBooleanFunction.OR),
            Block.makeCuboidShape(2, 0, 8, 14, 6, 14),
            Block.makeCuboidShape(8, 0, 8, 14, 6, 14)
    };

    public BlockOfCheese(Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(BITES, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[(Integer)state.get(BITES)];
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (worldIn.isRemote) {
            ItemStack stack = playerIn.getHeldItem(hand);
            if (this.eat(worldIn, pos, state, playerIn) == ActionResultType.SUCCESS) {
                return ActionResultType.SUCCESS;
            }

            if (stack.isEmpty()) {
                return ActionResultType.CONSUME;
            }
        }

        return this.eat(worldIn, pos, state, playerIn);
    }

    private ActionResultType eat(IWorld worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn) {
        if (!playerIn.canEat(false)) {
            return ActionResultType.PASS;
        } else {
            playerIn.addStat(Stats.EAT_CAKE_SLICE);
            playerIn.getFoodStats().addStats(4, 0.1F);
            if (playerIn.getActivePotionEffects() != null) {
                Collection<EffectInstance> playerEffects = playerIn.getActivePotionEffects();
                for (EffectInstance playerEffect : playerEffects) {
                    //TODO: Remove bad potion effects
                }
            }
            int lvt_5_1_ = (Integer)state.get(BITES);
            if (lvt_5_1_ < 3) {
                worldIn.setBlockState(pos, (BlockState)state.with(BITES, lvt_5_1_ + 1), 3);
            } else {
                worldIn.removeBlock(pos, false);
            }

            return ActionResultType.SUCCESS;
        }
    }

    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState state2, IWorld worldIn, BlockPos pos, BlockPos pos2) {
        return direction == Direction.DOWN && !state.isValidPosition(worldIn, pos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(state, direction, state2, worldIn, pos, pos2);
    }

    public boolean isValidPosition(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
        return p_196260_2_.getBlockState(p_196260_3_.down()).getMaterial().isSolid();
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(new IProperty[]{BITES});
    }

    public int getComparatorInputOverride(BlockState state, World worldIn, BlockPos pos) {
        return (4 - (Integer)state.get(BITES)) * 2;
    }

    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    public boolean allowsMovement(BlockState state, IBlockReader blockReader, BlockPos pos, PathType pathType) {
        return false;
    }
}
