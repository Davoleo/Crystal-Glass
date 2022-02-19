package net.davoleo.crystalglass.block;

import net.davoleo.crystalglass.util.Utils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class FullCrystalBlock extends Block {

    static final Block.Properties PROPERTIES = Utils.DEFAULT_ROCK_PROPERTIES
            .isSuffocating((p1, p2, p3) -> false)
            .noOcclusion()
            .sound(SoundType.AMETHYST);

    public FullCrystalBlock()
    {
        super(PROPERTIES);
    }
}