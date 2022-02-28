package net.davoleo.crystalglass.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class Utils {

    public static final Block.Properties DEFAULT_ROCK_PROPERTIES =
            Block.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(0.8F, 9);

    public static final Block.Properties DEFAULT_GLASS_PROPERTIES =
            Block.Properties.of(Material.GLASS)
                    .strength(0.5F, 1F)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .isValidSpawn((p1, p2, p3, p4) -> false)
                    .isRedstoneConductor((p1, p2, p3) -> false)
                    .isSuffocating((p1, p2, p3) -> false)
                    .isViewBlocking((p1, p2, p3) -> false);
}
