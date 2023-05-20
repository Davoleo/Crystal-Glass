package net.davoleo.crystalglass.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

public class BaseColoredBlock extends Block {

    public BaseColoredBlock(Properties properties, DyeColor color) {
        super(properties.color(color.getMaterialColor()));
    }
}
