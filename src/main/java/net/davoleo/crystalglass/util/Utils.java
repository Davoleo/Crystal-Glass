package net.davoleo.crystalglass.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class Utils {

    public static final Block.Properties DEFAULT_ROCK_PROPERTIES =
            Block.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(0.8F, 9);


}
