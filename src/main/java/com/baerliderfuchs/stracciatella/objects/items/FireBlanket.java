package com.baerliderfuchs.stracciatella.objects.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FireBlanket extends Item {
    public FireBlanket(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn.isBurning()) {
            playerIn.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 60, 255));
            playerIn.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 40, 255));
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
