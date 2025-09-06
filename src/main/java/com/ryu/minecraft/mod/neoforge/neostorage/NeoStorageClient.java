package com.ryu.minecraft.mod.neoforge.neostorage;

import com.ryu.minecraft.mod.neoforge.neostorage.client.gui.screens.inventory.StorageScreen;
import com.ryu.minecraft.mod.neoforge.neostorage.client.renderer.blockentity.StorageBlockEntityRenderer;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupMenus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = NeoStorage.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = NeoStorage.MODID, value = Dist.CLIENT)
public class NeoStorageClient {
    
    @SubscribeEvent
    public static void registerMenuSreen(RegisterMenuScreensEvent event) {
        event.register(SetupMenus.STORAGE.get(), StorageScreen::new);
    }
    
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        System.out.println("BlockEntityType: " + SetupBlockEntity.ARMOR_STORAGE.get());
        
        event.registerBlockEntityRenderer(SetupBlockEntity.ARMOR_STORAGE.get(), StorageBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(SetupBlockEntity.OTHER_STORAGE.get(), StorageBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(SetupBlockEntity.TOOL_STORAGE.get(), StorageBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(SetupBlockEntity.WEAPON_STORAGE.get(), StorageBlockEntityRenderer::new);
    }
    
    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Pre event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        Font font = Minecraft.getInstance().font;
        
        // Draw text at screen position (x=10, y=10)
        guiGraphics.drawString(font, "Overlay Test", 10, 10, 0xFF0000);
        
    }
    
    public NeoStorageClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
