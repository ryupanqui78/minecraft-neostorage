package com.ryu.minecraft.mod.neoforge.neostorage.setup;

import com.ryu.minecraft.mod.neoforge.neostorage.NeoStorage;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupCreativeModTab {
    
    private static final String TAB_NAME_NEOSTORAGE = "neostorage_tab";
    
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, NeoStorage.MODID);
    
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NEOVILLAGERS_TAB = SetupCreativeModTab.CREATIVE_MODE_TABS
            .register(SetupCreativeModTab.TAB_NAME_NEOSTORAGE,
                    () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.neostorage.neostorage"))
                            .icon(SetupItems.NEOTOOL::toStack).displayItems((parameters, output) -> {
                                output.accept(SetupItems.NEOTOOL.get());
                                SetupBlocks.ARMOR_STORAGE.forEach(output::accept);
                                SetupBlocks.OTHER_STORAGE.forEach(output::accept);
                                SetupBlocks.TOOL_STORAGE.forEach(output::accept);
                                SetupBlocks.WEAPON_STORAGE.forEach(output::accept);
                            }).build());
    
    private SetupCreativeModTab() {
    }
    
}
