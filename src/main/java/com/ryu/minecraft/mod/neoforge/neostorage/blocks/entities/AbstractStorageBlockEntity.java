package com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.ryu.minecraft.mod.neoforge.neostorage.blocks.AbstractStorageBlock;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.StorageMenu;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.data.ItemStored;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.data.StorageMenuData;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class AbstractStorageBlockEntity extends RandomizableContainerBlockEntity {
    
    private static final String TAG_LEVEL_SLOTS = "LevelSlots";
    
    protected final ContainerData dataAccess = new ContainerData() {
        
        @Override
        public int get(int pIndex) {
            if (pIndex == 0) {
                return AbstractStorageBlockEntity.this.levelSlots;
            }
            return 0;
        }
        
        @Override
        public int getCount() {
            return StorageMenu.NUMBER_DATA_CONTAINER;
        }
        
        @Override
        public void set(int pIndex, int pValue) {
            if (pIndex == 0) {
                AbstractStorageBlockEntity.this.levelSlots = pValue;
            }
        }
        
    };
    
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        
        @Override
        protected boolean isOwnContainer(Player pPlayer) {
            if (pPlayer.containerMenu instanceof final StorageMenu storageMenu) {
                final Container container = storageMenu.getContainer();
                return container == AbstractStorageBlockEntity.this;
            } else {
                return false;
            }
        }
        
        @Override
        protected void onClose(Level pLevel, BlockPos pPos, BlockState pState) {
            AbstractStorageBlockEntity.this.updateBlockState(pState, false);
        }
        
        @Override
        protected void onOpen(Level pLevel, BlockPos pPos, BlockState pState) {
            AbstractStorageBlockEntity.this.updateBlockState(pState, true);
        }
        
        @Override
        protected void openerCountChanged(Level pLevel, BlockPos pPos, BlockState pState, int pCount, int pOpenCount) {
            // Nothing
        }
    };
    
    private final TagKey<Item> filterTag;
    
    protected NonNullList<ItemStack> items = NonNullList.withSize(StorageMenu.NUMBER_SLOTS_CONTAINER, ItemStack.EMPTY);
    
    private int levelSlots;
    
    protected AbstractStorageBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, TagKey<Item> pFilterTag) {
        super(pType, pPos, pBlockState);
        this.filterTag = pFilterTag;
    }
    
    @Override
    public boolean canPlaceItem(int pSlot, ItemStack pStack) {
        boolean isValid = false;
        if (pSlot < StorageMenu.NUMBER_SLOTS_CONTAINER) {
            if (this.isEmpty()) {
                isValid = pStack.is(this.filterTag);
            } else {
                final List<TagKey<Item>> listTags = this.getCurrentTagKeys();
                isValid = listTags.stream().anyMatch(pStack::is);
                if (!isValid && (listTags.size() < this.getLevelSlots())) {
                    isValid = pStack.is(this.filterTag);
                }
            }
        }
        return isValid;
    }
    
    @Override
    public void clearContent() {
        this.getItems().clear();
    }
    
    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new StorageMenu(pContainerId, pInventory, new StorageMenuData(this, this.dataAccess, this.filterTag));
    }
    
    public abstract List<TagKey<Item>> getAvailablesTagKey();
    
    @Override
    public int getContainerSize() {
        return this.items.size();
    }
    
    public List<TagKey<Item>> getCurrentTagKeys() {
        final List<TagKey<Item>> listTags = new ArrayList<>();
        final Map<Item, Long> listItems = this.items.stream().filter(itemStack -> !itemStack.isEmpty())
                .collect(Collectors.groupingBy(ItemStack::getItem, Collectors.counting()));
        
        for (final Entry<Item, Long> entry : listItems.entrySet()) {
            final ItemStack itemStack = new ItemStack(entry.getKey());
            final List<TagKey<Item>> filterTags = itemStack.getTags().filter(this.getAvailablesTagKey()::contains)
                    .toList();
            for (int i = 0; i < filterTags.size(); i++) {
                if (!listTags.contains(filterTags.get(i))) {
                    listTags.add(filterTags.get(i));
                }
            }
        }
        
        return listTags;
    }
    
    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }
    
    public List<ItemStored> getItemsStoredByCount() {
        final List<TagKey<Item>> currentTagKeys = this.getCurrentTagKeys();
        final Map<Item, Long> sortedItems = this.items.stream().filter(itemStack -> !itemStack.isEmpty())
                .collect(Collectors.groupingBy(ItemStack::getItem, Collectors.counting())).entrySet().stream()
                .sorted(Map.Entry.<Item, Long> comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        
        final List<ItemStored> listItemStored = new LinkedList<>();
        for (final Entry<Item, Long> entry : sortedItems.entrySet()) {
            for (final TagKey<Item> currentTag : currentTagKeys) {
                final ItemStack itemStack = new ItemStack(entry.getKey());
                if (itemStack.is(currentTag)) {
                    listItemStored.add(new ItemStored(itemStack, entry.getValue(), currentTag));
                    currentTagKeys.remove(currentTag);
                    break;
                }
            }
        }
        
        sortedItems.entrySet().forEach(entry -> {
            if (listItemStored.stream().noneMatch(item -> item.getItemStack().is(entry.getKey()))) {
                final ItemStack itemStack = new ItemStack(entry.getKey());
                for (int i = 0; i < listItemStored.size(); i++) {
                    final ItemStored itemStored = listItemStored.get(i);
                    if (itemStack.is(itemStored.getTagItem())) {
                        itemStored.addCount(entry.getValue());
                        break;
                    }
                }
            }
        });
        return listItemStored;
    }
    
    public int getLevelSlots() {
        return this.levelSlots;
    }
    
    public long getNumberFilledSlots() {
        return this.getContainerSize() - this.items.stream().filter(ItemStack::isEmpty).count();
    }
    
    public int getNumberTotalSlots() {
        return this.getLevelSlots() * StorageMenu.NUMBER_SLOTS_BY_PAGE;
    }
    
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    
    @Override
    public CompoundTag getUpdateTag(Provider pRegistries) {
        return this.saveWithoutMetadata(pRegistries);
    }
    
    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(input)) {
            ContainerHelper.loadAllItems(input, this.items);
            this.levelSlots = input.getByteOr(AbstractStorageBlockEntity.TAG_LEVEL_SLOTS, (byte) 0);
        }
    }
    
    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (!this.trySaveLootTable(output)) {
            ContainerHelper.saveAllItems(output, this.items);
            output.putByte(AbstractStorageBlockEntity.TAG_LEVEL_SLOTS, (byte) this.levelSlots);
        }
    }
    
    @Override
    protected void setItems(NonNullList<ItemStack> pItemStacks) {
        this.items = pItemStacks;
    }
    
    @Override
    public void startOpen(Player pPlayer) {
        if (!this.remove && !pPlayer.isSpectator()) {
            this.openersCounter.incrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
            this.levelSlots = this.getBlockState().getValue(AbstractStorageBlock.LEVEL);
            this.setChanged();
        }
    }
    
    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }
    
    @Override
    public void stopOpen(Player pPlayer) {
        if (!this.remove && !pPlayer.isSpectator()) {
            this.openersCounter.decrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
        if (!this.level.isClientSide()) {
            this.setChanged();
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }
    
    protected void updateBlockState(BlockState pState, boolean pOpen) {
        this.level.setBlock(this.getBlockPos(), pState.setValue(AbstractStorageBlock.OPEN, Boolean.valueOf(pOpen)), 3);
    }
    
}
