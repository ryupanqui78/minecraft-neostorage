package com.ryu.minecraft.mod.neoforge.neostorage.client.gui.screens.inventory;

import com.ryu.minecraft.mod.neoforge.neostorage.NeoStorage;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.StorageMenu;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class StorageScreen extends AbstractContainerScreen<StorageMenu> {
    
    public static final int POS_UPGRADE_SLOT_LEFT = 5;
    public static final int POS_UPGRADE_SLOT_TOP = 33;
    
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
    
    private void changeCurrentTab(int pNewTab) {
        final int currentAvailableTabs = this.menu.getLevelSlot() + this.menu.getUpgradeLevel();
        if (pNewTab < currentAvailableTabs) {
            this.menu.setCurrentTab(pNewTab);
        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        final int edgeX = (this.width - this.imageWidth) / 2;
        final int edgeY = (this.height - this.imageHeight) / 2;
        final double pMouseY = mouseY - (edgeY + 16) - 3;
        final double posX = 182 - (mouseX - (edgeX + StorageScreen.LATERAL_SIZE + 5));
        final boolean isInsideButtonX = (posX > 0) && (posX < 16);
        final int posY = (int) (pMouseY - 4) % 14;
        final int index = (int) (pMouseY - 4) / 14;
        final int currentTab = this.menu.getCurrentTab();
        if (isInsideButtonX) {
            if ((0 <= pMouseY) && (pMouseY < 4)) {
                if (currentTab != index) {
                    this.changeCurrentTab(index);
                }
            } else {
                final int currentAvailableTabs = this.menu.getLevelSlot() + this.menu.getUpgradeLevel();
                if (((posY < 10) || (index == (currentAvailableTabs - 1))) && (posY >= 0) && (posY < 14)) {
                    this.changeCurrentTab(index);
                }
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
        final int slotLeftPos = edgeX + StorageScreen.POS_UPGRADE_SLOT_LEFT;
        final int slotTopUpgradePos = edgeY + StorageScreen.POS_UPGRADE_SLOT_TOP;
        
        pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.currentMainResource, statContentX, edgeY, 0, 0,
                this.imageWidth - StorageScreen.LATERAL_SIZE, this.imageHeight, StorageScreen.IMAGE_SIZE,
                StorageScreen.IMAGE_SIZE);
        pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.currentMainResource, edgeX, edgeY, 233, 0, 23, 143,
                StorageScreen.IMAGE_SIZE, StorageScreen.IMAGE_SIZE);
        pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.currentMainResource, slotLeftPos, slotTopUpgradePos, 199,
                0, StorageScreen.SLOT_SIZE, StorageScreen.SLOT_SIZE, StorageScreen.IMAGE_SIZE,
                StorageScreen.IMAGE_SIZE);
        if (this.menu.getItems().get(StorageMenu.SLOT_UPGRADE).isEmpty()) {
            pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.currentMainResource, slotLeftPos + 1,
                    slotTopUpgradePos + 1, 199, StorageScreen.SLOT_SIZE, StorageScreen.SLOT_CONTENT_SIZE,
                    StorageScreen.SLOT_CONTENT_SIZE, StorageScreen.IMAGE_SIZE, StorageScreen.IMAGE_SIZE);
        }
        this.renderContentBg(pGuiGraphics, statContentX + 5, edgeY + 16);
    }
    
    private void renderContentBg(GuiGraphics pGuiGraphics, int pStartX, int pStartY) {
        final int currentAvailableTabs = this.menu.getLevelSlot() + this.menu.getUpgradeLevel();
        pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, StorageScreen.TEXTURE, pStartX, pStartY, 0, 0, 166, 112,
                StorageScreen.IMAGE_SIZE, StorageScreen.IMAGE_SIZE);
        
        // Disabled buttons
        if (currentAvailableTabs > 1) {
            for (int i = currentAvailableTabs; i > 0; i--) {
                this.renderDisabledTabNumber(pGuiGraphics, pStartX, pStartY, i - 1);
            }
        }
        
        // Enabled buttons
        if (currentAvailableTabs > 1) {
            this.renderEnabledTabNumber(pGuiGraphics, pStartX, pStartY, this.menu.getCurrentTab(), true);
        }
    }
    
    private void renderDisabledTabNumber(GuiGraphics pGuiGraphics, int pStartX, int pStartY, int pIndex) {
        final int offsetTabY = pStartY + 2 + (14 * pIndex);
        final int offsetNumberY = pStartY + 7 + (14 * pIndex);
        
        pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, StorageScreen.TEXTURE,
                pStartX + StorageScreen.OFFSET_TAB_BUTTON_X, offsetTabY, 0, 113, 17, 20, StorageScreen.IMAGE_SIZE,
                StorageScreen.IMAGE_SIZE);
        pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, StorageScreen.TEXTURE, pStartX + 169, offsetNumberY,
                10.0f * pIndex, 134, 9, 11, StorageScreen.IMAGE_SIZE, StorageScreen.IMAGE_SIZE);
    }
    
    private void renderEnabledTabNumber(GuiGraphics pGuiGraphics, int pStartX, int pStartY, int pIndex, boolean pIsSelected) {
        if (pIsSelected) {
            final int offsetTabY = pStartY + 2 + (14 * pIndex);
            final int offsetNumberY = pStartY + 7 + (14 * pIndex);
            
            pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, StorageScreen.TEXTURE,
                    pStartX + StorageScreen.OFFSET_TAB_BUTTON_X, offsetTabY, 18, 113, 17, 20, StorageScreen.IMAGE_SIZE,
                    StorageScreen.IMAGE_SIZE);
            pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, StorageScreen.TEXTURE, pStartX + 169, offsetNumberY,
                    10.0f * pIndex, 146, 9, 11, StorageScreen.IMAGE_SIZE, StorageScreen.IMAGE_SIZE);
        }
    }
    
    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int pX, int pY) {
        super.renderTooltip(guiGraphics, pX, pY);
        
        final int edgeX = (this.width - this.imageWidth) / 2;
        final int edgeY = (this.height - this.imageHeight) / 2;
        final int contentSlotLeftPos = edgeX + StorageScreen.POS_UPGRADE_SLOT_LEFT;
        final int contentSlotToptPos = edgeY + StorageScreen.POS_UPGRADE_SLOT_TOP;
        
        final boolean isMouseXOverSlot = (pX >= contentSlotLeftPos)
                && (pX < ((contentSlotLeftPos + StorageScreen.SLOT_SIZE) - 1));
        final boolean isMouseYOverSlot = (pY >= contentSlotToptPos)
                && (pY < ((contentSlotToptPos + StorageScreen.SLOT_SIZE) - 1));
        if (isMouseXOverSlot && isMouseYOverSlot) {
            final Component textUpgrade = Component.translatable("container.upgrade.slot.empty")
                    .withStyle(ChatFormatting.GRAY);
            guiGraphics.setTooltipForNextFrame(this.font, textUpgrade, pX, pY);
        }
    }
}
