package net.davoleo.crystalglass.util;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class Utils {

    public static final AbstractBlock.Properties DEFAULT_ROCK_PROPERTIES =
            AbstractBlock.Properties.create(Material.ROCK)
                    .harvestTool(ToolType.PICKAXE)
                    .hardnessAndResistance(0.8F, 9);

}
