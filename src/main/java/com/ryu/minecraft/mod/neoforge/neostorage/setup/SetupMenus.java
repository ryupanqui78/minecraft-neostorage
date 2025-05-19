package com.ryu.minecraft.mod.neoforge.neostorage.setup;

import com.ryu.minecraft.mod.neoforge.neostorage.NeoStorage;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.StorageMenu;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupMenus {
    
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU,
            NeoStorage.MODID);
    
    public static final DeferredHolder<MenuType<?>, MenuType<StorageMenu>> STORAGE_CONTAINER = SetupMenus.MENUS
            .register("storage", () -> new MenuType<StorageMenu>(StorageMenu::new, FeatureFlags.DEFAULT_FLAGS));
    
    private SetupMenus() {
    }
}
