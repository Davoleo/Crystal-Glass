package net.davoleo.crystalglass.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;


public class ModTags {

    public static final TagKey<Item> CRYSTAL_ITEMS_TAGS = ItemTags.create(new ResourceLocation("crystalglass:shards"));
    public static final TagKey<Block> CRYSTAL_BLOCKS_TAGS = BlockTags.create(new ResourceLocation("crystalglass:shards"));

}
