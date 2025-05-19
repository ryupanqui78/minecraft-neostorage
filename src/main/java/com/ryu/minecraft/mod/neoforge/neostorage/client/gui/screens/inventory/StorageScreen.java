package com.ryu.minecraft.mod.neoforge.neostorage.client.gui.screens.inventory;

import com.ryu.minecraft.mod.neoforge.neostorage.NeoStorage;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.StorageMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StorageScreen extends AbstractContainerScreen<StorageMenu> {
    
    protected static final int SLOT_SIZE = 18;
    protected static final int SLOT_CONTENT_SIZE = 16;
    
    private static final int LATERAL_SIZE = 23;
    private static final ResourceLocation TEXTURE_BACKGROUND = new ResourceLocation(NeoStorage.MODID,
            "textures/gui/container/upgradecontainer.png");
    private static final ResourceLocation TEXTURE = new ResourceLocation(NeoStorage.MODID,
            "textures/gui/container/storage.png");
    
    private final ResourceLocation currentMainResource;
    
    public StorageScreen(StorageMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.titleLabelX += StorageScreen.LATERAL_SIZE;
        this.inventoryLabelX += StorageScreen.LATERAL_SIZE;
        this.imageWidth += StorageScreen.LATERAL_SIZE * 2;
        this.imageHeight = 224;
        
        this.currentMainResource = StorageScreen.TEXTURE_BACKGROUND;
        this.inventoryLabelY = this.imageHeight - 94;
    }
    
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
    
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        final int edgeX = (this.width - this.imageWidth) / 2;
        final int edgeY = (this.height - this.imageHeight) / 2;
        final int statContentX = edgeX + StorageScreen.LATERAL_SIZE;
        
        pGuiGraphics.blit(this.currentMainResource, statContentX, edgeY, 0, 0,
                this.imageWidth - StorageScreen.LATERAL_SIZE, this.imageHeight);
        pGuiGraphics.blit(this.currentMainResource, edgeX, edgeY, 233, 0, 23, 143);
        this.renderContentBg(pGuiGraphics, statContentX + 5, edgeY + 16);
    }
    
    private void renderContentBg(GuiGraphics pGuiGraphics, int pStartX, int pStartY) {
        pGuiGraphics.blit(StorageScreen.TEXTURE, pStartX, pStartY, 0, 0, 166, 112);
    }
    
}
