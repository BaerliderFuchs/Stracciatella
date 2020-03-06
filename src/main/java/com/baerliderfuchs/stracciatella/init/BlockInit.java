package com.baerliderfuchs.stracciatella.init;

import com.baerliderfuchs.stracciatella.Stracciatella;
import com.baerliderfuchs.stracciatella.objects.blocks.BlockOfCheese;
import com.baerliderfuchs.stracciatella.objects.blocks.ExperienceCrystal;
import com.baerliderfuchs.stracciatella.objects.blocks.FoodBowl;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Stracciatella.MOD_ID)
@Mod.EventBusSubscriber(modid = Stracciatella.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit {
    public static final Block block_of_cheese = null;
    public static final Block food_bowl = null;
    public static final Block experience_crystal = null;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        //event.getRegistry().register(new Block(Block.Properties.create(Material.ORGANIC).hardnessAndResistance(0.3f, 5.0f).sound(SoundType.WET_GRASS).harvestTool(ToolType.AXE)).setRegistryName("block_of_cheese"));
        event.getRegistry().register(new BlockOfCheese(Block.Properties.create(Material.ORGANIC).hardnessAndResistance(0.3f, 5.0f).sound(SoundType.STEM).harvestTool(ToolType.AXE)).setRegistryName("block_of_cheese"));
        event.getRegistry().register(new FoodBowl(Block.Properties.create(Material.CLAY).hardnessAndResistance(5.0f).sound(SoundType.STONE)).setRegistryName("food_bowl"));
        event.getRegistry().register(new ExperienceCrystal(Block.Properties.create(Material.GLASS).hardnessAndResistance(0.1f).sound(SoundType.GLASS).lightValue(5)).setRegistryName("experience_crystal"));
    }

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new BlockItem(block_of_cheese, new Item.Properties().group(ItemGroup.FOOD).maxStackSize(16)).setRegistryName("block_of_cheese"));
        event.getRegistry().register(new BlockItem(food_bowl, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName("food_bowl"));
        event.getRegistry().register(new BlockItem(experience_crystal, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName("experience_crystal"));
    }
}
