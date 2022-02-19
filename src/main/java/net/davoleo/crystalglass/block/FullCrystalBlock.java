package net.davoleo.crystalglass.block;

import net.davoleo.crystalglass.init.ModSounds;
import net.davoleo.crystalglass.util.Utils;
import net.minecraft.block.Block;

public class FullCrystalBlock extends Block {

    static final Properties PROPERTIES = Utils.DEFAULT_ROCK_PROPERTIES
            .setOpaque((p1, p2, p3) -> false)
            .notSolid()
            .sound(ModSounds.CRYSTAL_SOUND_TYPE);

    public FullCrystalBlock()
    {
        super(PROPERTIES);
    }
}