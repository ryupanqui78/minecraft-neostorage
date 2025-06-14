package com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities;

import java.util.List;

import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupTags;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

public class WeaponStorageBlockEntity extends AbstractStorageBlockEntity {
    
    private static final List<TagKey<Item>> ALLOWED_TAGS = List.of(ItemTags.BOW_ENCHANTABLE,
            ItemTags.CROSSBOW_ENCHANTABLE, ItemTags.TRIDENT_ENCHANTABLE, ItemTags.SWORDS, SetupTags.SHIELDS);
    
    public WeaponStorageBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SetupBlockEntity.WEAPON_STORAGE.get(), pPos, pBlockState, SetupTags.STORE_WEAPONS);
    }
    
    @Override
    public List<TagKey<Item>> getAvailablesTagKey() {
        return WeaponStorageBlockEntity.ALLOWED_TAGS;
    }
    
    @Override
    protected Component getDefaultName() {
        return Component.translatable("screen.container.weaponstorage");
    }
    
}
