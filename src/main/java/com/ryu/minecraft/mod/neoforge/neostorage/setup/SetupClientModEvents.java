package com.ryu.minecraft.mod.neoforge.neostorage.setup;

import com.ryu.minecraft.mod.neoforge.neostorage.NeoStorage;
import com.ryu.minecraft.mod.neoforge.neostorage.client.gui.screens.inventory.StorageScreen;
import com.ryu.minecraft.mod.neoforge.neostorage.client.renderer.blockentity.StorageBlockEntityRenderer;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = NeoStorage.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class SetupClientModEvents {
    
    @SubscribeEvent
    public static void registerMenuSreen(RegisterMenuScreensEvent event) {
        event.register(SetupMenus.STORAGE_CONTAINER.get(), StorageScreen::new);
    }
    
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(SetupBlockEntity.TOOL_STORAGE.get(), StorageBlockEntityRenderer::new);
    }
    
    private SetupClientModEvents() {
    }
}
