package com.baerliderfuchs.stracciatella.objects.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Sap extends Item {
    public Sap(Properties properties) {
        super(properties);
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 400;
    }
}
