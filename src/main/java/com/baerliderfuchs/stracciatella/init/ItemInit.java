package com.baerliderfuchs.stracciatella.init;

import com.baerliderfuchs.stracciatella.Stracciatella;
import com.baerliderfuchs.stracciatella.objects.items.FireBlanket;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Stracciatella.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Stracciatella.MOD_ID)
public class ItemInit {
    public static final Item fire_blanket = null;
    public static final Item cheese = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new FireBlanket(new Item.Properties().group(ItemGroup.TOOLS)).setRegistryName("fire_blanket"));
        event.getRegistry().register(new Item(new Item.Properties().group(ItemGroup.FOOD).food(new Food.Builder().hunger(5).saturation(1.0f).build())).setRegistryName("cheese"));
    }
}
