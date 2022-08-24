package net.davoleo.crystalglass.mixin;

import net.davoleo.crystalglass.init.ModBlocks;
import net.davoleo.crystalglass.init.ModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class FurnaceBurnMixin {

    @Inject(
            at = @At(value = "INVOKE", target = "net.minecraft.world.item.ItemStack.shrink(I)V", ordinal = 0),
            method = "burn(Lnet/minecraft/world/item/crafting/Recipe;Lnet/minecraft/core/NonNullList;I)Z"
    )
    private void onBurn(@Nullable Recipe<?> pRecipe, NonNullList<ItemStack> pStacks, int pStackSize, CallbackInfoReturnable<Boolean> callback)
    {

        if (pStacks.get(0).is(ModBlocks.BASE_CRYSTAL_BLOCK.get().asItem()) && pStacks.get(1).is(Items.BUCKET))
        {
            pStacks.set(1, new ItemStack(ModItems.MOLTEN_CRYSTAL_BUCKET.get()));
        }
    }


}
