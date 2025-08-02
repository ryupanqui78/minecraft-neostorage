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

public class ArmorStorageBlockEntity extends AbstractStorageBlockEntity {
    
    private static final List<TagKey<Item>> ALLOWED_TAGS = List.of(ItemTags.FOOT_ARMOR, ItemTags.CHEST_ARMOR,
            ItemTags.HEAD_ARMOR, ItemTags.LEG_ARMOR);
    
    public ArmorStorageBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SetupBlockEntity.ARMOR_STORAGE.get(), pPos, pBlockState, SetupTags.STORE_ARMORS);
    }
    
    @Override
    public List<TagKey<Item>> getAvailablesTagKey() {
        return ArmorStorageBlockEntity.ALLOWED_TAGS;
    }
    
    @Override
    protected Component getDefaultName() {
        return Component.translatable("screen.container.armorstorage");
    }
    
}
