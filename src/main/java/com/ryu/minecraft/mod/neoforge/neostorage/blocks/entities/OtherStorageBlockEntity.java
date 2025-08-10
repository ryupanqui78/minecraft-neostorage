package com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities;

import java.util.List;

import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupTags;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

public class OtherStorageBlockEntity extends AbstractStorageBlockEntity {
    
    private static final List<TagKey<Item>> ALLOWED_TAGS = List.of(SetupTags.POTIONS, SetupTags.SOUPS,
            SetupTags.FILLED_MAPS, SetupTags.FLIT_AND_STEELS, SetupTags.GOAT_HORNS, SetupTags.TOTEMS,
            SetupTags.SPYGLASSES, SetupTags.WRITABLE_BOOKS);
    
    public OtherStorageBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SetupBlockEntity.OTHER_STORAGE.get(), pPos, pBlockState, SetupTags.STORE_OTHERS);
    }
    
    @Override
    public List<TagKey<Item>> getAvailablesTagKey() {
        return OtherStorageBlockEntity.ALLOWED_TAGS;
    }
    
    @Override
    protected Component getDefaultName() {
        return Component.translatable("screen.container.otherstorage");
    }
    
}
