package com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.ryu.minecraft.mod.neoforge.neostorage.blocks.ToolStorageBlock;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.StorageMenu;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.data.ItemStored;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.data.StorageMenuData;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;

public class ToolStorageBlockEntity extends BaseContainerBlockEntity implements RandomizableContainer {
    
    private static final List<TagKey<Item>> ALLOWED_TAGS = List.of(ItemTags.PICKAXES, ItemTags.AXES, ItemTags.SHOVELS,
            ItemTags.HOES, SetupTags.SHEARS, SetupTags.BRUSHES, ItemTags.FISHING_ENCHANTABLE);
    private static final String TAG_LEVEL_SLOTS = "LevelSlots";
    
    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int pIndex) {
            if (pIndex == 0) {
                return ToolStorageBlockEntity.this.levelSlots;
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
                ToolStorageBlockEntity.this.levelSlots = pValue;
            }
        }
        
    };
    
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        
        @Override
        protected boolean isOwnContainer(Player pPlayer) {
            if (pPlayer.containerMenu instanceof final StorageMenu storageMenu) {
                final Container container = storageMenu.getContainer();
                return container == ToolStorageBlockEntity.this;
            } else {
                return false;
            }
        }
        
        @Override
        protected void onClose(Level pLevel, BlockPos pPos, BlockState pState) {
            ToolStorageBlockEntity.this.updateBlockState(pState, false);
        }
        
        @Override
        protected void onOpen(Level pLevel, BlockPos pPos, BlockState pState) {
            ToolStorageBlockEntity.this.updateBlockState(pState, true);
        }
        
        @Override
        protected void openerCountChanged(Level pLevel, BlockPos pPos, BlockState pState, int pCount, int pOpenCount) {
            // Nothing
        }
    };
    
    private final TagKey<Item> filterTag;
    protected NonNullList<ItemStack> items = NonNullList.withSize(StorageMenu.NUMBER_SLOTS_CONTAINER, ItemStack.EMPTY);
    @Nullable
    protected ResourceKey<LootTable> lootTable;
    protected long lootTableSeed;
    
    private int levelSlots;
    
    public ToolStorageBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SetupBlockEntity.TOOL_STORAGE.get(), pPos, pBlockState);
        this.filterTag = SetupTags.STORE_TOOLS;
    }
    
    @Override
    public boolean canOpen(Player pPlayer) {
        return super.canOpen(pPlayer) && ((this.lootTable == null) || !pPlayer.isSpectator());
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
    
    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        if (this.canOpen(pPlayer)) {
            this.unpackLootTable(pInventory.player);
            return this.createMenu(pContainerId, pInventory);
        } else {
            return null;
        }
    }
    
    public List<TagKey<Item>> getAvailablesTagKey() {
        return ToolStorageBlockEntity.ALLOWED_TAGS;
    }
    
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
    protected Component getDefaultName() {
        return Component.translatable("screen.container.toolstorage");
    }
    
    @Override
    public ItemStack getItem(int pIndex) {
        this.unpackLootTable(null);
        return this.getItems().get(pIndex);
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
    
    @Override
    public ResourceKey<LootTable> getLootTable() {
        return this.lootTable;
    }
    
    public int getLevelSlots() {
        return this.levelSlots;
    }
    
    @Override
    public long getLootTableSeed() {
        return this.lootTableSeed;
    }
    
    public long getNumberFilledSlots() {
        return this.getContainerSize() - this.items.stream().filter(ItemStack::isEmpty).count();
    }
    
    public int getNumberTotalSlots() {
        return StorageMenu.NUMBER_SLOTS_CONTAINER;
    }
    
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    
    @Override
    public CompoundTag getUpdateTag(Provider pRegistries) {
        final CompoundTag compoundTag = new CompoundTag();
        this.saveAdditional(compoundTag, pRegistries);
        return compoundTag;
    }
    
    @Override
    public boolean isEmpty() {
        this.unpackLootTable(null);
        return this.getItems().stream().allMatch(ItemStack::isEmpty);
    }
    
    @Override
    protected void loadAdditional(CompoundTag pTag, Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(pTag)) {
            ContainerHelper.loadAllItems(pTag, this.items, pRegistries);
            this.levelSlots = pTag.getByte(ToolStorageBlockEntity.TAG_LEVEL_SLOTS);
        }
    }
    
    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        this.unpackLootTable(null);
        final ItemStack itemstack = ContainerHelper.removeItem(this.getItems(), pIndex, pCount);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }
        
        return itemstack;
    }
    
    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        this.unpackLootTable(null);
        return ContainerHelper.takeItem(this.getItems(), pIndex);
    }
    
    @Override
    protected void saveAdditional(CompoundTag pTag, Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        if (!this.trySaveLootTable(pTag)) {
            ContainerHelper.saveAllItems(pTag, this.items, pRegistries);
            pTag.putByte(ToolStorageBlockEntity.TAG_LEVEL_SLOTS, (byte) this.levelSlots);
        }
    }
    
    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        this.unpackLootTable(null);
        this.getItems().set(pIndex, pStack);
        if (pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }
        this.setChanged();
    }
    
    @Override
    protected void setItems(NonNullList<ItemStack> pItemStacks) {
        this.items = pItemStacks;
    }
    
    @Override
    public void setLootTable(ResourceKey<LootTable> pLootTable) {
        this.lootTable = pLootTable;
    }
    
    @Override
    public void setLootTableSeed(long pSeed) {
        this.lootTableSeed = pSeed;
    }
    
    @Override
    public void startOpen(Player pPlayer) {
        if (!this.remove && !pPlayer.isSpectator()) {
            this.openersCounter.incrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
            this.levelSlots = this.getBlockState().getValue(ToolStorageBlock.LEVEL);
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
    }
    
    protected void updateBlockState(BlockState pState, boolean pOpen) {
        this.level.setBlock(this.getBlockPos(), pState.setValue(ToolStorageBlock.OPEN, Boolean.valueOf(pOpen)), 3);
    }
}
