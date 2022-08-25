package net.davoleo.crystalglass.util;

import net.davoleo.crystalglass.CrystalGlassMod;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class DefaultProperties {

    public static final Block.Properties CRYSTAL_BLOCK =
            Block.Properties.of(Material.AMETHYST)
                    .requiresCorrectToolForDrops()
                    .strength(0.8F, 9)
                    .noOcclusion()
                    .isViewBlocking((pState, pLevel, pPos) -> false);

    public static final Block.Properties GLASS_BLOCK =
            Block.Properties.of(Material.GLASS)
                    .strength(0.5F, 1F)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .isValidSpawn((p1, p2, p3, p4) -> false)
                    .isRedstoneConductor((p1, p2, p3) -> false)
                    .isSuffocating((p1, p2, p3) -> false)
                    .isViewBlocking((p1, p2, p3) -> false);

    public static BlockBehaviour.Properties coloredBlockProps(Material material, DyeColor color)
    {
        return BlockBehaviour.Properties.of(material, color);
    }

    public static final Item.Properties ITEM =
            new Item.Properties()
                    .tab(CrystalGlassMod.CREATIVE_TAB);
}
