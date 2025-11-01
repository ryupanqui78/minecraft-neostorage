package com.ryu.minecraft.mod.neoforge.neostorage.inventory.slots;

import com.ryu.minecraft.mod.neoforge.neostorage.item.UpgradeLevelItem;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class UpgradeLevelSlot extends Slot {
    
    public UpgradeLevelSlot(Container pContainer, int pSlot, int pX, int pY, int minSlot) {
        super(pContainer, pSlot, pX, pY);
    }
    
    @Override
    public int getMaxStackSize() {
        return 1;
    }
    
    @Override
    public boolean mayPlace(ItemStack pStack) {
        return pStack.getItem() instanceof UpgradeLevelItem;
    }
    
    @Override
    public void onTake(Player pPlayer, ItemStack pStack) {
        super.onTake(pPlayer, pStack);
        // for (int i = this.nSecondStartResultLevel; i < this.container.getContainerSize(); i++) {
        // final ItemStack result = this.container.getItem(i);
        // pPlayer.addItem(result);
        // }
    }
    
}
