package com.ryu.minecraft.mod.neoforge.neostorage.inventory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;

public record SpecialItemStackWithSlot(int slot, ItemStack stack) {
    
    public static final Codec<SpecialItemStackWithSlot> CODEC = RecordCodecBuilder.create(p_421518_ -> p_421518_
            .group(ExtraCodecs.NON_NEGATIVE_INT.fieldOf("Slot").orElse(0).forGetter(SpecialItemStackWithSlot::slot),
                    ItemStack.MAP_CODEC.forGetter(SpecialItemStackWithSlot::stack))
            .apply(p_421518_, SpecialItemStackWithSlot::new));
    
    public boolean isValidInContainer(int numSlots) {
        return (this.slot >= 0) && (this.slot < numSlots);
    }
}
