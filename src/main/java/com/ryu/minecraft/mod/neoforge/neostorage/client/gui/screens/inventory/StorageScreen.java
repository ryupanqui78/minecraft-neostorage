package com.ryu.minecraft.mod.neoforge.neostorage.client.gui.screens.inventory;

import com.ryu.minecraft.mod.neoforge.neostorage.NeoStorage;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.StorageMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StorageScreen extends AbstractContainerScreen<StorageMenu> {
    
    protected static final int SLOT_SIZE = 18;
    protected static final int SLOT_CONTENT_SIZE = 16;
    
    private static final int IMAGE_SIZE = 256;
    private static final int LATERAL_SIZE = 23;
    private static final int OFFSET_TAB_BUTTON_X = 165;
    private static final ResourceLocation TEXTURE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID,
            "textures/gui/container/upgradecontainer.png");
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID,
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
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        final int edgeX = (this.width - this.imageWidth) / 2;
        final int edgeY = (this.height - this.imageHeight) / 2;
        final double posX = 182 - (mouseX - (edgeX + StorageScreen.LATERAL_SIZE + 5));
        final double pMouseY = mouseY - (edgeY + 16);
        final boolean isInsideButtonX = (posX > 0) && (posX < 16);
        final int posY = (int) (pMouseY - 3) % 18;
        
        if (isInsideButtonX && ((posY >= 0) && (posY < 18))) {
            final int index = (int) (pMouseY - 4) / 18;
            if (index <= (this.menu.getLevelSlot() - 1)) {
                this.menu.setCurrentTab(index);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
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
        
        pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.currentMainResource, statContentX, edgeY, 0, 0,
                this.imageWidth - StorageScreen.LATERAL_SIZE, this.imageHeight, StorageScreen.IMAGE_SIZE,
                StorageScreen.IMAGE_SIZE);
        pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.currentMainResource, edgeX, edgeY, 233, 0, 23, 143,
                StorageScreen.IMAGE_SIZE, StorageScreen.IMAGE_SIZE);
        this.renderContentBg(pGuiGraphics, statContentX + 5, edgeY + 16);
    }
    
    private void renderContentBg(GuiGraphics pGuiGraphics, int pStartX, int pStartY) {
        pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, StorageScreen.TEXTURE, pStartX, pStartY, 0, 0, 166, 112,
                StorageScreen.IMAGE_SIZE, StorageScreen.IMAGE_SIZE);
        
        // Disabled buttons
        if (this.menu.getLevelSlot() > 1) {
            for (int i = 0; i < this.menu.getLevelSlot(); i++) {
                this.renderDisabledTabNumber(pGuiGraphics, pStartX, pStartY, i);
            }
        }
        
        // Enabled buttons
        if (this.menu.getLevelSlot() > 1) {
            for (int i = 0; i < this.menu.getLevelSlot(); i++) {
                this.renderEnabledTabNumber(pGuiGraphics, pStartX, pStartY, i, this.menu.getCurrentTab() == i);
            }
        }
    }
    
    private void renderDisabledTabNumber(GuiGraphics pGuiGraphics, int pStartX, int pStartY, int pIndex) {
        final int offsetTabY = pStartY + 2 + (18 * pIndex);
        final int offsetNumberY = pStartY + 6 + (18 * pIndex);
        
        pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, StorageScreen.TEXTURE,
                pStartX + StorageScreen.OFFSET_TAB_BUTTON_X, offsetTabY, 0, 113, 17, 20, StorageScreen.IMAGE_SIZE,
                StorageScreen.IMAGE_SIZE);
        pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, StorageScreen.TEXTURE, pStartX + 169, offsetNumberY,
                10.0f * pIndex, 134, 9, 11, StorageScreen.IMAGE_SIZE, StorageScreen.IMAGE_SIZE);
    }
    
    private void renderEnabledTabNumber(GuiGraphics pGuiGraphics, int pStartX, int pStartY, int pIndex, boolean pIsSelected) {
        if (pIsSelected) {
            final int offsetTabY = pStartY + 2 + (18 * pIndex);
            final int offsetNumberY = pStartY + 6 + (18 * pIndex);
            
            pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, StorageScreen.TEXTURE,
                    pStartX + StorageScreen.OFFSET_TAB_BUTTON_X, offsetTabY, 18, 113, 17, 20, StorageScreen.IMAGE_SIZE,
                    StorageScreen.IMAGE_SIZE);
            pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, StorageScreen.TEXTURE, pStartX + 169, offsetNumberY,
                    10.0f * pIndex, 146, 9, 11, StorageScreen.IMAGE_SIZE, StorageScreen.IMAGE_SIZE);
        }
    }
    
}
