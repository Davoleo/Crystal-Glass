package net.davoleo.crystalglass.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;


public class ModTags {

    public static final Tags.IOptionalNamedTag<Item> CRYSTAL_ITEMS_TAGS = ItemTags.createOptional(new ResourceLocation("crystalglass:shards"));
    public static final Tags.IOptionalNamedTag<Block> CRYSTAL_BLOCKS_TAGS = BlockTags.createOptional(new ResourceLocation("crystalglass:shards"));
}
