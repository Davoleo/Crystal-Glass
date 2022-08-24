package net.davoleo.crystalglass.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class BaseColoredBlock extends Block {

    public BaseColoredBlock(Material material, DyeColor color)
    {
        super(Properties.of(material, color).color(color.getMaterialColor()));
    }


}
