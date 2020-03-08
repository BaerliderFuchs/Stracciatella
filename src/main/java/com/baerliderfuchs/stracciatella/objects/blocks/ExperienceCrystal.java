package com.baerliderfuchs.stracciatella.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class ExperienceCrystal extends Block {
    public ExperienceCrystal(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        super.onPlayerDestroy(worldIn, pos, state);
        Random random = new Random();
        this.dropXpOnBlockBreak((World) worldIn, pos, 100 + random.nextInt(10));
    }
}
