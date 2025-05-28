package com.ryu.minecraft.mod.neoforge.neostorage.setup;

import com.ryu.minecraft.mod.neoforge.neostorage.NeoStorage;
import com.ryu.minecraft.mod.neoforge.neostorage.client.gui.screens.inventory.StorageScreen;
import com.ryu.minecraft.mod.neoforge.neostorage.client.renderer.blockentity.StorageBlockEntityRenderer;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = NeoStorage.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SetupClientModEvents {
    
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        NeoStorage.LOGGER.info("HELLO FROM CLIENT SETUP");
        NeoStorage.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
    
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
